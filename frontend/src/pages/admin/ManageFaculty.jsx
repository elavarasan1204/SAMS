import { useState, useEffect } from 'react';
import { facultyService, departmentService } from '../../services/api';
import { Modal, ConfirmDialog, LoadingSpinner, EmptyState, SearchBar } from '../../components/Common';
import { Plus, Edit, Trash2, Users } from 'lucide-react';
import toast from 'react-hot-toast';

export default function ManageFaculty() {
  const [faculty, setFaculty] = useState([]);
  const [departments, setDepartments] = useState([]);
  const [loading, setLoading] = useState(true);
  const [search, setSearch] = useState('');
  const [filterDept, setFilterDept] = useState('');
  const [showModal, setShowModal] = useState(false);
  const [showDelete, setShowDelete] = useState(false);
  const [editing, setEditing] = useState(null);
  const [deleteId, setDeleteId] = useState(null);
  const [form, setForm] = useState({
    employeeId: '', fullName: '', email: '', phone: '',
    qualification: '', designation: '', departmentId: '', password: 'Faculty@123'
  });

  const fetchFaculty = () => {
    const params = {};
    if (search) params.search = search;
    if (filterDept) params.departmentId = filterDept;
    facultyService.getAll(params)
      .then(res => setFaculty(res.data.data || []))
      .catch(console.error)
      .finally(() => setLoading(false));
  };

  useEffect(() => {
    departmentService.getAll().then(res => setDepartments(res.data.data || []));
    fetchFaculty();
  }, []);

  useEffect(() => { fetchFaculty(); }, [search, filterDept]);

  const resetForm = () => setForm({
    employeeId: '', fullName: '', email: '', phone: '',
    qualification: '', designation: '', departmentId: '', password: 'Faculty@123'
  });

  const openCreate = () => { resetForm(); setEditing(null); setShowModal(true); };
  const openEdit = (f) => {
    setEditing(f);
    setForm({
      employeeId: f.employeeId, fullName: f.fullName, email: f.email,
      phone: f.phone || '', qualification: f.qualification || '',
      designation: f.designation || '', departmentId: f.department?.id || '', password: ''
    });
    setShowModal(true);
  };

  const handleSubmit = async (e) => {
    e.preventDefault();
    if (!form.fullName || !form.employeeId || !form.email || !form.departmentId) {
      toast.error('Please fill all required fields.'); return;
    }
    try {
      if (editing) {
        await facultyService.update(editing.id, form);
        toast.success('Faculty updated successfully');
      } else {
        await facultyService.create(form);
        toast.success('Faculty created successfully');
      }
      setShowModal(false); fetchFaculty();
    } catch (err) {
      toast.error(err.response?.data?.message || 'Operation failed');
    }
  };

  const handleDelete = async () => {
    try { await facultyService.delete(deleteId); toast.success('Faculty deactivated'); setShowDelete(false); fetchFaculty(); }
    catch { toast.error('Delete failed'); }
  };

  if (loading) return <LoadingSpinner />;

  return (
    <div>
      <div className="page-header">
        <h2>Manage Faculty</h2>
        <button className="btn btn-primary" onClick={openCreate}><Plus size={18} /> Add Faculty</button>
      </div>
      <div className="filter-bar">
        <SearchBar value={search} onChange={setSearch} placeholder="Search faculty..." />
        <select value={filterDept} onChange={e => setFilterDept(e.target.value)}>
          <option value="">All Departments</option>
          {departments.map(d => <option key={d.id} value={d.id}>{d.name}</option>)}
        </select>
      </div>
      <div className="table-container">
        <div className="table-wrapper">
          {faculty.length === 0 ? <EmptyState icon={<Users size={64} />} title="No faculty found" /> : (
            <table>
              <thead><tr><th>Employee ID</th><th>Name</th><th>Email</th><th>Phone</th><th>Department</th><th>Designation</th><th>Status</th><th>Actions</th></tr></thead>
              <tbody>
                {faculty.map(f => (
                  <tr key={f.id}>
                    <td><strong>{f.employeeId}</strong></td><td>{f.fullName}</td><td>{f.email}</td><td>{f.phone || '-'}</td>
                    <td>{f.department?.name || '-'}</td><td>{f.designation || '-'}</td>
                    <td><span className={`badge ${f.enabled ? 'badge-good' : 'badge-critical'}`}>{f.enabled ? 'Active' : 'Inactive'}</span></td>
                    <td>
                      <div className="btn-group">
                        <button className="btn btn-sm btn-secondary" onClick={() => openEdit(f)}><Edit size={14} /></button>
                        <button className="btn btn-sm btn-danger" onClick={() => { setDeleteId(f.id); setShowDelete(true); }}><Trash2 size={14} /></button>
                      </div>
                    </td>
                  </tr>
                ))}
              </tbody>
            </table>
          )}
        </div>
      </div>

      <Modal isOpen={showModal} onClose={() => setShowModal(false)} title={editing ? 'Edit Faculty' : 'Add Faculty'}
        footer={<><button className="btn btn-secondary" onClick={() => setShowModal(false)}>Cancel</button><button className="btn btn-primary" onClick={handleSubmit}>{editing ? 'Update' : 'Create'}</button></>}>
        <form onSubmit={handleSubmit}>
          <div className="form-row">
            <div className="form-group"><label>Employee ID *</label><input className="form-control" value={form.employeeId} onChange={e => setForm({...form, employeeId: e.target.value})} required /></div>
            <div className="form-group"><label>Full Name *</label><input className="form-control" value={form.fullName} onChange={e => setForm({...form, fullName: e.target.value})} required /></div>
          </div>
          <div className="form-row">
            <div className="form-group"><label>Email *</label><input className="form-control" type="email" value={form.email} onChange={e => setForm({...form, email: e.target.value})} required /></div>
            <div className="form-group"><label>Phone</label><input className="form-control" value={form.phone} onChange={e => setForm({...form, phone: e.target.value})} /></div>
          </div>
          <div className="form-row">
            <div className="form-group"><label>Qualification</label><input className="form-control" value={form.qualification} onChange={e => setForm({...form, qualification: e.target.value})} /></div>
            <div className="form-group"><label>Designation</label><input className="form-control" value={form.designation} onChange={e => setForm({...form, designation: e.target.value})} /></div>
          </div>
          <div className="form-group"><label>Department *</label>
            <select className="form-control" value={form.departmentId} onChange={e => setForm({...form, departmentId: e.target.value})} required>
              <option value="">Select Department</option>
              {departments.map(d => <option key={d.id} value={d.id}>{d.name}</option>)}
            </select>
          </div>
          {!editing && <div className="form-group"><label>Password</label><input className="form-control" type="password" value={form.password} onChange={e => setForm({...form, password: e.target.value})} placeholder="Default: Faculty@123" /></div>}
        </form>
      </Modal>
      <ConfirmDialog isOpen={showDelete} onClose={() => setShowDelete(false)} onConfirm={handleDelete} title="Deactivate Faculty?" message="This faculty member will be deactivated." />
    </div>
  );
}
