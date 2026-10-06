import { useState, useEffect } from 'react';
import { hodService, departmentService } from '../../services/api';
import { Modal, ConfirmDialog, LoadingSpinner, EmptyState } from '../../components/Common';
import { Plus, Edit, Trash2, UserCheck } from 'lucide-react';
import toast from 'react-hot-toast';

export default function ManageHod() {
  const [hods, setHods] = useState([]);
  const [departments, setDepartments] = useState([]);
  const [loading, setLoading] = useState(true);
  const [showModal, setShowModal] = useState(false);
  const [showDelete, setShowDelete] = useState(false);
  const [editing, setEditing] = useState(null);
  const [deleteId, setDeleteId] = useState(null);
  const [form, setForm] = useState({ employeeId: '', fullName: '', email: '', phone: '', qualification: '', departmentId: '', password: 'Hod@123' });

  const fetchHods = () => { hodService.getAll().then(res => setHods(res.data.data || [])).catch(console.error).finally(() => setLoading(false)); };
  useEffect(() => { departmentService.getAll().then(res => setDepartments(res.data.data || [])); fetchHods(); }, []);

  const resetForm = () => setForm({ employeeId: '', fullName: '', email: '', phone: '', qualification: '', departmentId: '', password: 'Hod@123' });
  const openCreate = () => { resetForm(); setEditing(null); setShowModal(true); };
  const openEdit = (h) => { setEditing(h); setForm({ employeeId: h.employeeId, fullName: h.fullName, email: h.email, phone: h.phone || '', qualification: h.qualification || '', departmentId: h.department?.id || '', password: '' }); setShowModal(true); };

  const handleSubmit = async (e) => {
    e.preventDefault();
    if (!form.fullName || !form.employeeId || !form.email || !form.departmentId) { toast.error('Please fill required fields.'); return; }
    try {
      if (editing) { await hodService.update(editing.id, form); toast.success('HOD updated'); }
      else { await hodService.create(form); toast.success('HOD created'); }
      setShowModal(false); fetchHods();
    } catch (err) { toast.error(err.response?.data?.message || 'Operation failed'); }
  };

  const handleDelete = async () => { try { await hodService.delete(deleteId); toast.success('HOD deactivated'); setShowDelete(false); fetchHods(); } catch { toast.error('Failed'); } };

  if (loading) return <LoadingSpinner />;

  return (
    <div>
      <div className="page-header"><h2>Manage HODs</h2><button className="btn btn-primary" onClick={openCreate}><Plus size={18} /> Add HOD</button></div>
      <div className="table-container"><div className="table-wrapper">
        {hods.length === 0 ? <EmptyState icon={<UserCheck size={64} />} title="No HODs found" /> : (
          <table><thead><tr><th>Employee ID</th><th>Name</th><th>Email</th><th>Department</th><th>Qualification</th><th>Status</th><th>Actions</th></tr></thead>
            <tbody>{hods.map(h => (
              <tr key={h.id}><td><strong>{h.employeeId}</strong></td><td>{h.fullName}</td><td>{h.email}</td><td>{h.department?.name || '-'}</td><td>{h.qualification || '-'}</td>
                <td><span className={`badge ${h.enabled ? 'badge-good' : 'badge-critical'}`}>{h.enabled ? 'Active' : 'Inactive'}</span></td>
                <td><div className="btn-group"><button className="btn btn-sm btn-secondary" onClick={() => openEdit(h)}><Edit size={14} /></button><button className="btn btn-sm btn-danger" onClick={() => { setDeleteId(h.id); setShowDelete(true); }}><Trash2 size={14} /></button></div></td>
              </tr>))}</tbody></table>)}
      </div></div>

      <Modal isOpen={showModal} onClose={() => setShowModal(false)} title={editing ? 'Edit HOD' : 'Add HOD'}
        footer={<><button className="btn btn-secondary" onClick={() => setShowModal(false)}>Cancel</button><button className="btn btn-primary" onClick={handleSubmit}>{editing ? 'Update' : 'Create'}</button></>}>
        <form onSubmit={handleSubmit}>
          <div className="form-row"><div className="form-group"><label>Employee ID *</label><input className="form-control" value={form.employeeId} onChange={e => setForm({...form, employeeId: e.target.value})} required /></div>
            <div className="form-group"><label>Full Name *</label><input className="form-control" value={form.fullName} onChange={e => setForm({...form, fullName: e.target.value})} required /></div></div>
          <div className="form-row"><div className="form-group"><label>Email *</label><input className="form-control" type="email" value={form.email} onChange={e => setForm({...form, email: e.target.value})} required /></div>
            <div className="form-group"><label>Phone</label><input className="form-control" value={form.phone} onChange={e => setForm({...form, phone: e.target.value})} /></div></div>
          <div className="form-row"><div className="form-group"><label>Qualification</label><input className="form-control" value={form.qualification} onChange={e => setForm({...form, qualification: e.target.value})} /></div>
            <div className="form-group"><label>Department *</label><select className="form-control" value={form.departmentId} onChange={e => setForm({...form, departmentId: e.target.value})} required>
              <option value="">Select</option>{departments.map(d => <option key={d.id} value={d.id}>{d.name}</option>)}</select></div></div>
          {!editing && <div className="form-group"><label>Password</label><input className="form-control" type="password" value={form.password} onChange={e => setForm({...form, password: e.target.value})} placeholder="Default: Hod@123" /></div>}
        </form>
      </Modal>
      <ConfirmDialog isOpen={showDelete} onClose={() => setShowDelete(false)} onConfirm={handleDelete} title="Deactivate HOD?" />
    </div>
  );
}
