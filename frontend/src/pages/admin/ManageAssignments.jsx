import { useState, useEffect } from 'react';
import { facultySubjectService, facultyService, subjectService, classService } from '../../services/api';
import { ConfirmDialog, LoadingSpinner, EmptyState } from '../../components/Common';
import { Plus, Trash2, Link } from 'lucide-react';
import toast from 'react-hot-toast';

export default function ManageAssignments() {
  const [assignments, setAssignments] = useState([]);
  const [faculty, setFaculty] = useState([]);
  const [subjects, setSubjects] = useState([]);
  const [classes, setClasses] = useState([]);
  const [loading, setLoading] = useState(true);
  const [showDelete, setShowDelete] = useState(false);
  const [deleteId, setDeleteId] = useState(null);
  const [form, setForm] = useState({ facultyId: '', subjectId: '', classId: '', academicYear: '2024-2025' });

  const fetch = () => { facultySubjectService.getAll().then(res => setAssignments(res.data.data || [])).catch(console.error).finally(() => setLoading(false)); };

  useEffect(() => {
    facultyService.getAll().then(res => setFaculty(res.data.data || []));
    subjectService.getAll().then(res => setSubjects(res.data.data || []));
    classService.getAll().then(res => setClasses(res.data.data || []));
    fetch();
  }, []);

  const handleCreate = async () => {
    if (!form.facultyId || !form.subjectId || !form.classId) { toast.error('Select all fields.'); return; }
    try {
      await facultySubjectService.create(form);
      toast.success('Assignment created');
      setForm({ facultyId: '', subjectId: '', classId: '', academicYear: '2024-2025' });
      fetch();
    } catch (err) { toast.error(err.response?.data?.message || 'Failed'); }
  };

  const handleDelete = async () => { try { await facultySubjectService.delete(deleteId); toast.success('Deleted'); setShowDelete(false); fetch(); } catch { toast.error('Failed'); } };

  if (loading) return <LoadingSpinner />;

  return (
    <div>
      <div className="page-header"><h2>Faculty Assignments</h2></div>

      <div className="table-container" style={{ marginBottom: 24 }}>
        <div style={{ padding: 24 }}>
          <h3 style={{ marginBottom: 16, fontSize: 16, fontWeight: 600 }}>Create New Assignment</h3>
          <div className="filter-bar">
            <select className="form-control" style={{ maxWidth: 200 }} value={form.facultyId} onChange={e => setForm({...form, facultyId: e.target.value})}>
              <option value="">Select Faculty</option>{faculty.map(f => <option key={f.id} value={f.id}>{f.fullName} ({f.employeeId})</option>)}
            </select>
            <select className="form-control" style={{ maxWidth: 200 }} value={form.subjectId} onChange={e => setForm({...form, subjectId: e.target.value})}>
              <option value="">Select Subject</option>{subjects.map(s => <option key={s.id} value={s.id}>{s.name} ({s.subjectCode})</option>)}
            </select>
            <select className="form-control" style={{ maxWidth: 200 }} value={form.classId} onChange={e => setForm({...form, classId: e.target.value})}>
              <option value="">Select Class</option>{classes.map(c => <option key={c.id} value={c.id}>{c.name}</option>)}
            </select>
            <input className="form-control" style={{ maxWidth: 120 }} value={form.academicYear} onChange={e => setForm({...form, academicYear: e.target.value})} placeholder="2024-2025" />
            <button className="btn btn-primary" onClick={handleCreate}><Plus size={18} /> Assign</button>
          </div>
        </div>
      </div>

      <div className="table-container"><div className="table-wrapper">
        {assignments.length === 0 ? <EmptyState icon={<Link size={64} />} title="No assignments" /> : (
          <table><thead><tr><th>Faculty</th><th>Subject</th><th>Class</th><th>Academic Year</th><th>Actions</th></tr></thead>
            <tbody>{assignments.map(a => (
              <tr key={a.id}><td>{a.faculty?.fullName} ({a.faculty?.employeeId})</td><td>{a.subject?.name} ({a.subject?.subjectCode})</td><td>{a.classEntity?.name}</td><td>{a.academicYear}</td>
                <td><button className="btn btn-sm btn-danger" onClick={() => { setDeleteId(a.id); setShowDelete(true); }}><Trash2 size={14} /></button></td>
              </tr>))}</tbody></table>)}
      </div></div>
      <ConfirmDialog isOpen={showDelete} onClose={() => setShowDelete(false)} onConfirm={handleDelete} title="Delete Assignment?" />
    </div>
  );
}
