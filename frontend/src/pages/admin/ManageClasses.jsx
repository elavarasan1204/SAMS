import { useState, useEffect } from 'react';
import { classService, departmentService } from '../../services/api';
import { Modal, ConfirmDialog, LoadingSpinner, EmptyState } from '../../components/Common';
import { Plus, Edit, Trash2, School } from 'lucide-react';
import toast from 'react-hot-toast';

export default function ManageClasses() {
  const [classes, setClasses] = useState([]);
  const [departments, setDepartments] = useState([]);
  const [loading, setLoading] = useState(true);
  const [showModal, setShowModal] = useState(false);
  const [showDelete, setShowDelete] = useState(false);
  const [editing, setEditing] = useState(null);
  const [deleteId, setDeleteId] = useState(null);
  const [form, setForm] = useState({ name: '', year: 3, section: 'A', semester: 5, departmentId: '' });

  const fetch = () => { classService.getAll().then(res => setClasses(res.data.data || [])).catch(console.error).finally(() => setLoading(false)); };
  useEffect(() => { departmentService.getAll().then(res => setDepartments(res.data.data || [])); fetch(); }, []);

  const handleSubmit = async (e) => {
    e.preventDefault();
    if (!form.name || !form.departmentId) { toast.error('Fill required fields.'); return; }
    try {
      if (editing) { await classService.update(editing.id, form); toast.success('Updated'); }
      else { await classService.create(form); toast.success('Created'); }
      setShowModal(false); fetch();
    } catch (err) { toast.error(err.response?.data?.message || 'Failed'); }
  };

  const handleDelete = async () => { try { await classService.delete(deleteId); toast.success('Deleted'); setShowDelete(false); fetch(); } catch { toast.error('Failed'); } };

  if (loading) return <LoadingSpinner />;

  return (
    <div>
      <div className="page-header"><h2>Manage Classes</h2><button className="btn btn-primary" onClick={() => { setForm({ name: '', year: 3, section: 'A', semester: 5, departmentId: '' }); setEditing(null); setShowModal(true); }}><Plus size={18} /> Add Class</button></div>
      <div className="table-container"><div className="table-wrapper">
        {classes.length === 0 ? <EmptyState icon={<School size={64} />} title="No classes" /> : (
          <table><thead><tr><th>Name</th><th>Year</th><th>Section</th><th>Semester</th><th>Department</th><th>Actions</th></tr></thead>
            <tbody>{classes.map(c => (
              <tr key={c.id}><td><strong>{c.name}</strong></td><td>{c.year}</td><td>{c.section}</td><td>{c.semester}</td><td>{c.department?.name || '-'}</td>
                <td><div className="btn-group"><button className="btn btn-sm btn-secondary" onClick={() => { setEditing(c); setForm({ name: c.name, year: c.year, section: c.section, semester: c.semester, departmentId: c.department?.id || '' }); setShowModal(true); }}><Edit size={14} /></button><button className="btn btn-sm btn-danger" onClick={() => { setDeleteId(c.id); setShowDelete(true); }}><Trash2 size={14} /></button></div></td>
              </tr>))}</tbody></table>)}
      </div></div>
      <Modal isOpen={showModal} onClose={() => setShowModal(false)} title={editing ? 'Edit Class' : 'Add Class'}
        footer={<><button className="btn btn-secondary" onClick={() => setShowModal(false)}>Cancel</button><button className="btn btn-primary" onClick={handleSubmit}>{editing ? 'Update' : 'Create'}</button></>}>
        <form onSubmit={handleSubmit}>
          <div className="form-row"><div className="form-group"><label>Name *</label><input className="form-control" value={form.name} onChange={e => setForm({...form, name: e.target.value})} required /></div>
            <div className="form-group"><label>Section</label><input className="form-control" value={form.section} onChange={e => setForm({...form, section: e.target.value})} /></div></div>
          <div className="form-row"><div className="form-group"><label>Year</label><input className="form-control" type="number" value={form.year} onChange={e => setForm({...form, year: parseInt(e.target.value)})} /></div>
            <div className="form-group"><label>Semester</label><input className="form-control" type="number" value={form.semester} onChange={e => setForm({...form, semester: parseInt(e.target.value)})} /></div></div>
          <div className="form-group"><label>Department *</label><select className="form-control" value={form.departmentId} onChange={e => setForm({...form, departmentId: e.target.value})} required><option value="">Select</option>{departments.map(d => <option key={d.id} value={d.id}>{d.name}</option>)}</select></div>
        </form>
      </Modal>
      <ConfirmDialog isOpen={showDelete} onClose={() => setShowDelete(false)} onConfirm={handleDelete} title="Delete Class?" />
    </div>
  );
}
