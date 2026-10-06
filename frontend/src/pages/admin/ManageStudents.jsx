import { useState, useEffect } from 'react';
import { studentService, departmentService, classService } from '../../services/api';
import { Modal, ConfirmDialog, LoadingSpinner, EmptyState, SearchBar } from '../../components/Common';
import { Plus, Edit, Trash2, Users } from 'lucide-react';
import toast from 'react-hot-toast';

export default function ManageStudents() {
  const [students, setStudents] = useState([]);
  const [departments, setDepartments] = useState([]);
  const [classes, setClasses] = useState([]);
  const [loading, setLoading] = useState(true);
  const [search, setSearch] = useState('');
  const [filterDept, setFilterDept] = useState('');
  const [filterClass, setFilterClass] = useState('');
  const [showModal, setShowModal] = useState(false);
  const [showDelete, setShowDelete] = useState(false);
  const [editing, setEditing] = useState(null);
  const [deleteId, setDeleteId] = useState(null);
  const [form, setForm] = useState({
    registerNumber: '', fullName: '', email: '', phone: '',
    dateOfBirth: '', gender: '', address: '',
    departmentId: '', classId: '', year: 3, semester: 5, password: 'Student@123'
  });

  const fetchStudents = () => {
    const params = {};
    if (search) params.search = search;
    if (filterDept) params.departmentId = filterDept;
    if (filterClass) params.classId = filterClass;
    studentService.getAll(params)
      .then(res => setStudents(res.data.data || []))
      .catch(console.error)
      .finally(() => setLoading(false));
  };

  useEffect(() => {
    departmentService.getAll().then(res => setDepartments(res.data.data || []));
    classService.getAll().then(res => setClasses(res.data.data || []));
    fetchStudents();
  }, []);

  useEffect(() => { fetchStudents(); }, [search, filterDept, filterClass]);

  const resetForm = () => setForm({
    registerNumber: '', fullName: '', email: '', phone: '',
    dateOfBirth: '', gender: '', address: '',
    departmentId: '', classId: '', year: 3, semester: 5, password: 'Student@123'
  });

  const openCreate = () => { resetForm(); setEditing(null); setShowModal(true); };

  const openEdit = (s) => {
    setEditing(s);
    setForm({
      registerNumber: s.registerNumber, fullName: s.fullName, email: s.email,
      phone: s.phone || '', dateOfBirth: s.dateOfBirth || '', gender: s.gender || '',
      address: s.address || '', departmentId: s.department?.id || '',
      classId: s.classEntity?.id || '', year: s.year, semester: s.semester, password: ''
    });
    setShowModal(true);
  };

  const handleSubmit = async (e) => {
    e.preventDefault();
    if (!form.fullName || !form.registerNumber || !form.email || !form.departmentId || !form.classId) {
      toast.error('Please fill all required fields.'); return;
    }
    try {
      if (editing) {
        await studentService.update(editing.id, form);
        toast.success('Student updated successfully');
      } else {
        await studentService.create(form);
        toast.success('Student created successfully');
      }
      setShowModal(false);
      fetchStudents();
    } catch (err) {
      toast.error(err.response?.data?.message || 'Operation failed');
    }
  };

  const handleDelete = async () => {
    try {
      await studentService.delete(deleteId);
      toast.success('Student deactivated');
      setShowDelete(false);
      fetchStudents();
    } catch (err) {
      toast.error('Delete failed');
    }
  };

  if (loading) return <LoadingSpinner />;

  return (
    <div>
      <div className="page-header">
        <h2>Manage Students</h2>
        <button className="btn btn-primary" onClick={openCreate}><Plus size={18} /> Add Student</button>
      </div>

      <div className="filter-bar">
        <SearchBar value={search} onChange={setSearch} placeholder="Search by name or register number..." />
        <select value={filterDept} onChange={e => setFilterDept(e.target.value)}>
          <option value="">All Departments</option>
          {departments.map(d => <option key={d.id} value={d.id}>{d.name}</option>)}
        </select>
        <select value={filterClass} onChange={e => setFilterClass(e.target.value)}>
          <option value="">All Classes</option>
          {classes.map(c => <option key={c.id} value={c.id}>{c.name}</option>)}
        </select>
      </div>

      <div className="table-container">
        <div className="table-wrapper">
          {students.length === 0 ? (
            <EmptyState icon={<Users size={64} />} title="No students found" />
          ) : (
            <table>
              <thead>
                <tr>
                  <th>Reg. No</th><th>Name</th><th>Email</th><th>Phone</th>
                  <th>Department</th><th>Class</th><th>Year</th><th>Status</th><th>Actions</th>
                </tr>
              </thead>
              <tbody>
                {students.map(s => (
                  <tr key={s.id}>
                    <td><strong>{s.registerNumber}</strong></td>
                    <td>{s.fullName}</td>
                    <td>{s.email}</td>
                    <td>{s.phone || '-'}</td>
                    <td>{s.department?.name || '-'}</td>
                    <td>{s.classEntity?.name || '-'}</td>
                    <td>{s.year}</td>
                    <td><span className={`badge ${s.enabled ? 'badge-good' : 'badge-critical'}`}>{s.enabled ? 'Active' : 'Inactive'}</span></td>
                    <td>
                      <div className="btn-group">
                        <button className="btn btn-sm btn-secondary" onClick={() => openEdit(s)}><Edit size={14} /></button>
                        <button className="btn btn-sm btn-danger" onClick={() => { setDeleteId(s.id); setShowDelete(true); }}><Trash2 size={14} /></button>
                      </div>
                    </td>
                  </tr>
                ))}
              </tbody>
            </table>
          )}
        </div>
      </div>

      <Modal isOpen={showModal} onClose={() => setShowModal(false)} title={editing ? 'Edit Student' : 'Add Student'}
        footer={
          <>
            <button className="btn btn-secondary" onClick={() => setShowModal(false)}>Cancel</button>
            <button className="btn btn-primary" onClick={handleSubmit}>{editing ? 'Update' : 'Create'}</button>
          </>
        }>
        <form onSubmit={handleSubmit}>
          <div className="form-row">
            <div className="form-group">
              <label>Register Number *</label>
              <input className="form-control" value={form.registerNumber} onChange={e => setForm({...form, registerNumber: e.target.value})} required />
            </div>
            <div className="form-group">
              <label>Full Name *</label>
              <input className="form-control" value={form.fullName} onChange={e => setForm({...form, fullName: e.target.value})} required />
            </div>
          </div>
          <div className="form-row">
            <div className="form-group">
              <label>Email *</label>
              <input className="form-control" type="email" value={form.email} onChange={e => setForm({...form, email: e.target.value})} required />
            </div>
            <div className="form-group">
              <label>Phone</label>
              <input className="form-control" value={form.phone} onChange={e => setForm({...form, phone: e.target.value})} />
            </div>
          </div>
          <div className="form-row">
            <div className="form-group">
              <label>Department *</label>
              <select className="form-control" value={form.departmentId} onChange={e => setForm({...form, departmentId: e.target.value})} required>
                <option value="">Select Department</option>
                {departments.map(d => <option key={d.id} value={d.id}>{d.name}</option>)}
              </select>
            </div>
            <div className="form-group">
              <label>Class *</label>
              <select className="form-control" value={form.classId} onChange={e => setForm({...form, classId: e.target.value})} required>
                <option value="">Select Class</option>
                {classes.map(c => <option key={c.id} value={c.id}>{c.name}</option>)}
              </select>
            </div>
          </div>
          <div className="form-row">
            <div className="form-group">
              <label>Date of Birth</label>
              <input className="form-control" type="date" value={form.dateOfBirth} onChange={e => setForm({...form, dateOfBirth: e.target.value})} />
            </div>
            <div className="form-group">
              <label>Gender</label>
              <select className="form-control" value={form.gender} onChange={e => setForm({...form, gender: e.target.value})}>
                <option value="">Select</option>
                <option value="MALE">Male</option>
                <option value="FEMALE">Female</option>
                <option value="OTHER">Other</option>
              </select>
            </div>
          </div>
          <div className="form-row">
            <div className="form-group">
              <label>Year</label>
              <input className="form-control" type="number" value={form.year} onChange={e => setForm({...form, year: parseInt(e.target.value)})} />
            </div>
            <div className="form-group">
              <label>Semester</label>
              <input className="form-control" type="number" value={form.semester} onChange={e => setForm({...form, semester: parseInt(e.target.value)})} />
            </div>
          </div>
          {!editing && (
            <div className="form-group">
              <label>Password</label>
              <input className="form-control" type="password" value={form.password} onChange={e => setForm({...form, password: e.target.value})} placeholder="Default: Student@123" />
            </div>
          )}
        </form>
      </Modal>

      <ConfirmDialog isOpen={showDelete} onClose={() => setShowDelete(false)} onConfirm={handleDelete}
        title="Deactivate Student?" message="This student will be deactivated and unable to login." />
    </div>
  );
}
