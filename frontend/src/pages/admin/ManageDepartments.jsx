import { useState, useEffect } from 'react';
import { departmentService } from '../../services/api';
import { Modal, ConfirmDialog, LoadingSpinner, EmptyState } from '../../components/Common';
import { Plus, Edit, Trash2, Building2 } from 'lucide-react';
import toast from 'react-hot-toast';

export default function ManageDepartments() {
  const [departments, setDepartments] = useState([]);
  const [loading, setLoading] = useState(true);
  const [showModal, setShowModal] = useState(false);
  const [showDelete, setShowDelete] = useState(false);
  const [editing, setEditing] = useState(null);
  const [deleteId, setDeleteId] = useState(null);
  const [form, setForm] = useState({ name: '', code: '', description: '' });

  const fetch = () => { departmentService.getAll().then(res => setDepartments(res.data.data || [])).catch(console.error).finally(() => setLoading(false)); };
  useEffect(() => { fetch(); }, []);

  const handleSubmit = async (e) => {
    e.preventDefault();
    if (!form.name || !form.code) { toast.error('Name and code are required.'); return; }
    try {
      if (editing) { await departmentService.update(editing.id, form); toast.success('Updated'); }
      else { await departmentService.create(form); toast.success('Created'); }
      setShowModal(false); fetch();
    } catch (err) { toast.error(err.response?.data?.message || 'Failed'); }
  };

  const handleDelete = async () => { try { await departmentService.delete(deleteId); toast.success('Deleted'); setShowDelete(false); fetch(); } catch { toast.error('Failed'); } };

  if (loading) return <LoadingSpinner />;

  return (
    <div>
      <div className="page-header"><h2>Manage Departments</h2><button className="btn btn-primary" onClick={() => { setForm({ name: '', code: '', description: '' }); setEditing(null); setShowModal(true); }}><Plus size={18} /> Add Department</button></div>
      <div className="table-container"><div className="table-wrapper">
        {departments.length === 0 ? <EmptyState icon={<Building2 size={64} />} title="No departments" /> : (
          <table><thead><tr><th>Code</th><th>Name</th><th>Description</th><th>Actions</th></tr></thead>
            <tbody>{departments.map(d => (
              <tr key={d.id}><td><strong>{d.code}</strong></td><td>{d.name}</td><td>{d.description || '-'}</td>
                <td><div className="btn-group"><button className="btn btn-sm btn-secondary" onClick={() => { setEditing(d); setForm({ name: d.name, code: d.code, description: d.description || '' }); setShowModal(true); }}><Edit size={14} /></button><button className="btn btn-sm btn-danger" onClick={() => { setDeleteId(d.id); setShowDelete(true); }}><Trash2 size={14} /></button></div></td>
              </tr>))}</tbody></table>)}
      </div></div>
      <Modal isOpen={showModal} onClose={() => setShowModal(false)} title={editing ? 'Edit Department' : 'Add Department'}
        footer={<><button className="btn btn-secondary" onClick={() => setShowModal(false)}>Cancel</button><button className="btn btn-primary" onClick={handleSubmit}>{editing ? 'Update' : 'Create'}</button></>}>
        <form onSubmit={handleSubmit}>
          <div className="form-group"><label>Name *</label><input className="form-control" value={form.name} onChange={e => setForm({...form, name: e.target.value})} required /></div>
          <div className="form-group"><label>Code *</label><input className="form-control" value={form.code} onChange={e => setForm({...form, code: e.target.value})} required /></div>
          <div className="form-group"><label>Description</label><textarea className="form-control" value={form.description} onChange={e => setForm({...form, description: e.target.value})} rows={3} /></div>
        </form>
      </Modal>
      <ConfirmDialog isOpen={showDelete} onClose={() => setShowDelete(false)} onConfirm={handleDelete} title="Delete Department?" message="All associated data may be affected." />
    </div>
  );
}
