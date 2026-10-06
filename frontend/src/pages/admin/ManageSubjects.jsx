import { useState, useEffect } from 'react';
import { subjectService, departmentService } from '../../services/api';
import { Modal, ConfirmDialog, LoadingSpinner, EmptyState } from '../../components/Common';
import { Plus, Edit, Trash2, BookOpen } from 'lucide-react';
import toast from 'react-hot-toast';

export default function ManageSubjects() {
  const [subjects, setSubjects] = useState([]);
  const [departments, setDepartments] = useState([]);
  const [loading, setLoading] = useState(true);
  const [filterDept, setFilterDept] = useState('');
  const [showModal, setShowModal] = useState(false);
  const [showDelete, setShowDelete] = useState(false);
  const [editing, setEditing] = useState(null);
  const [deleteId, setDeleteId] = useState(null);
  const [form, setForm] = useState({ subjectCode: '', name: '', credits: 3, semester: 5, departmentId: '' });

  const fetch = () => {
    const params = filterDept ? { departmentId: filterDept } : {};
    subjectService.getAll(params).then(res => setSubjects(res.data.data || [])).catch(console.error).finally(() => setLoading(false));
  };

  useEffect(() => { departmentService.getAll().then(res => setDepartments(res.data.data || [])); fetch(); }, []);
  useEffect(() => { fetch(); }, [filterDept]);

  const handleSubmit = async (e) => {
    e.preventDefault();
    if (!form.subjectCode || !form.name || !form.departmentId) { toast.error('Fill required fields.'); return; }
    try {
      if (editing) { await subjectService.update(editing.id, form); toast.success('Updated'); }
      else { await subjectService.create(form); toast.success('Created'); }
      setShowModal(false); fetch();
    } catch (err) { toast.error(err.response?.data?.message || 'Failed'); }
  };

  const handleDelete = async () => { try { await subjectService.delete(deleteId); toast.success('Deleted'); setShowDelete(false); fetch(); } catch { toast.error('Failed'); } };

  if (loading) return <LoadingSpinner />;

  return (
    <div>
      <div className="page-header"><h2>Manage Subjects</h2><button className="btn btn-primary" onClick={() => { setForm({ subjectCode: '', name: '', credits: 3, semester: 5, departmentId: '' }); setEditing(null); setShowModal(true); }}><Plus size={18} /> Add Subject</button></div>
      <div className="filter-bar">
        <select value={filterDept} onChange={e => setFilterDept(e.target.value)}><option value="">All Departments</option>{departments.map(d => <option key={d.id} value={d.id}>{d.name}</option>)}</select>
      </div>
      <div className="table-container"><div className="table-wrapper">
        {subjects.length === 0 ? <EmptyState icon={<BookOpen size={64} />} title="No subjects" /> : (
          <table><thead><tr><th>Code</th><th>Name</th><th>Credits</th><th>Semester</th><th>Department</th><th>Actions</th></tr></thead>
            <tbody>{subjects.map(s => (
              <tr key={s.id}><td><strong>{s.subjectCode}</strong></td><td>{s.name}</td><td>{s.credits}</td><td>{s.semester}</td><td>{s.department?.name || '-'}</td>
                <td><div className="btn-group"><button className="btn btn-sm btn-secondary" onClick={() => { setEditing(s); setForm({ subjectCode: s.subjectCode, name: s.name, credits: s.credits, semester: s.semester, departmentId: s.department?.id || '' }); setShowModal(true); }}><Edit size={14} /></button><button className="btn btn-sm btn-danger" onClick={() => { setDeleteId(s.id); setShowDelete(true); }}><Trash2 size={14} /></button></div></td>
              </tr>))}</tbody></table>)}
      </div></div>
      <Modal isOpen={showModal} onClose={() => setShowModal(false)} title={editing ? 'Edit Subject' : 'Add Subject'}
        footer={<><button className="btn btn-secondary" onClick={() => setShowModal(false)}>Cancel</button><button className="btn btn-primary" onClick={handleSubmit}>{editing ? 'Update' : 'Create'}</button></>}>
        <form onSubmit={handleSubmit}>
          <div className="form-row"><div className="form-group"><label>Code *</label><input className="form-control" value={form.subjectCode} onChange={e => setForm({...form, subjectCode: e.target.value})} required /></div>
            <div className="form-group"><label>Name *</label><input className="form-control" value={form.name} onChange={e => setForm({...form, name: e.target.value})} required /></div></div>
          <div className="form-row"><div className="form-group"><label>Credits</label><input className="form-control" type="number" value={form.credits} onChange={e => setForm({...form, credits: parseInt(e.target.value)})} /></div>
            <div className="form-group"><label>Semester</label><input className="form-control" type="number" value={form.semester} onChange={e => setForm({...form, semester: parseInt(e.target.value)})} /></div></div>
          <div className="form-group"><label>Department *</label><select className="form-control" value={form.departmentId} onChange={e => setForm({...form, departmentId: e.target.value})} required><option value="">Select</option>{departments.map(d => <option key={d.id} value={d.id}>{d.name}</option>)}</select></div>
        </form>
      </Modal>
      <ConfirmDialog isOpen={showDelete} onClose={() => setShowDelete(false)} onConfirm={handleDelete} title="Delete Subject?" />
    </div>
  );
}
