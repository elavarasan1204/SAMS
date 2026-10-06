import { useState, useEffect } from 'react';
import { facultySubjectService, facultyService, studentService, attendanceService, authService } from '../../services/api';
import { LoadingSpinner, EmptyState } from '../../components/Common';
import { Save, CheckCircle, XCircle, RotateCcw } from 'lucide-react';
import toast from 'react-hot-toast';

export default function MarkAttendance() {
  const [assignments, setAssignments] = useState([]);
  const [selectedSubject, setSelectedSubject] = useState('');
  const [selectedClass, setSelectedClass] = useState('');
  const [date, setDate] = useState(new Date().toISOString().split('T')[0]);
  const [students, setStudents] = useState([]);
  const [attendance, setAttendance] = useState({});
  const [loading, setLoading] = useState(true);
  const [saving, setSaving] = useState(false);
  const [facultyId, setFacultyId] = useState(null);
  const user = authService.getUser();

  useEffect(() => {
    const fetchData = async () => {
      try {
        const facRes = await facultyService.getByUserId(user.userId);
        setFacultyId(facRes.data.data.id);
        const assRes = await facultySubjectService.getAll({ facultyId: facRes.data.data.id });
        setAssignments(assRes.data.data || []);
      } catch (err) { console.error(err); }
      finally { setLoading(false); }
    };
    fetchData();
  }, []);

  // Get unique subject-class pairs
  const subjectClassPairs = assignments.map(a => ({
    subjectId: a.subject?.id,
    subjectName: `${a.subject?.name} (${a.subject?.subjectCode})`,
    classId: a.classEntity?.id,
    className: a.classEntity?.name
  }));

  const uniqueSubjects = [...new Map(subjectClassPairs.map(p => [p.subjectId, p])).values()];

  const availableClasses = subjectClassPairs.filter(p => p.subjectId == selectedSubject);

  const loadStudents = async () => {
    if (!selectedClass) return;
    try {
      const res = await studentService.getAll({ classId: selectedClass });
      const studs = res.data.data || [];
      setStudents(studs);

      // Check existing attendance
      try {
        const existingRes = await attendanceService.getAll({
          subjectId: selectedSubject,
          classId: selectedClass,
          date: date
        });
        const existing = existingRes.data.data || [];
        const att = {};
        studs.forEach(s => {
          const rec = existing.find(e => e.student?.id === s.id);
          att[s.id] = rec ? rec.status : 'PRESENT';
        });
        setAttendance(att);
      } catch {
        const att = {};
        studs.forEach(s => { att[s.id] = 'PRESENT'; });
        setAttendance(att);
      }
    } catch (err) { console.error(err); }
  };

  useEffect(() => { if (selectedClass && selectedSubject) loadStudents(); }, [selectedClass, date]);

  const markAll = (status) => {
    const att = {};
    students.forEach(s => { att[s.id] = status; });
    setAttendance(att);
  };

  const handleSave = async () => {
    if (students.length === 0) { toast.error('No students loaded.'); return; }
    setSaving(true);
    try {
      const request = {
        subjectId: parseInt(selectedSubject),
        classId: parseInt(selectedClass),
        date: date,
        attendanceList: students.map(s => ({
          studentId: s.id,
          status: attendance[s.id] || 'PRESENT'
        }))
      };
      await attendanceService.mark(request);
      toast.success('Attendance saved successfully!');
    } catch (err) {
      toast.error(err.response?.data?.message || 'Failed to save attendance.');
    }
    finally { setSaving(false); }
  };

  if (loading) return <LoadingSpinner />;

  return (
    <div>
      <div className="page-header"><h2>Mark Attendance</h2></div>

      <div className="table-container" style={{ marginBottom: 24 }}>
        <div style={{ padding: 24 }}>
          <div className="filter-bar">
            <select className="form-control" style={{ maxWidth: 280 }} value={selectedSubject} onChange={e => { setSelectedSubject(e.target.value); setSelectedClass(''); setStudents([]); }}>
              <option value="">Select Subject</option>
              {uniqueSubjects.map(s => <option key={s.subjectId} value={s.subjectId}>{s.subjectName}</option>)}
            </select>
            <select className="form-control" style={{ maxWidth: 200 }} value={selectedClass} onChange={e => setSelectedClass(e.target.value)}>
              <option value="">Select Class</option>
              {availableClasses.map(c => <option key={c.classId} value={c.classId}>{c.className}</option>)}
            </select>
            <input className="form-control" type="date" style={{ maxWidth: 180 }} value={date} onChange={e => setDate(e.target.value)} />
          </div>
        </div>
      </div>

      {students.length > 0 && (
        <>
          <div style={{ display: 'flex', justifyContent: 'space-between', alignItems: 'center', marginBottom: 16, flexWrap: 'wrap', gap: 8 }}>
            <span style={{ color: 'var(--text-secondary)', fontSize: 14 }}>{students.length} students loaded</span>
            <div className="btn-group">
              <button className="btn btn-sm btn-success" onClick={() => markAll('PRESENT')}><CheckCircle size={16} /> Mark All Present</button>
              <button className="btn btn-sm btn-danger" onClick={() => markAll('ABSENT')}><XCircle size={16} /> Mark All Absent</button>
              <button className="btn btn-sm btn-secondary" onClick={loadStudents}><RotateCcw size={16} /> Reset</button>
            </div>
          </div>

          <div className="attendance-grid">
            {students.map(s => (
              <div key={s.id} className="attendance-row">
                <div className="student-info">
                  <div className="student-name">{s.fullName}</div>
                  <div className="student-reg">{s.registerNumber}</div>
                </div>
                <div className="attendance-toggle">
                  <button className={`attendance-btn present ${attendance[s.id] === 'PRESENT' ? 'active' : ''}`}
                    onClick={() => setAttendance({...attendance, [s.id]: 'PRESENT'})}>Present</button>
                  <button className={`attendance-btn absent ${attendance[s.id] === 'ABSENT' ? 'active' : ''}`}
                    onClick={() => setAttendance({...attendance, [s.id]: 'ABSENT'})}>Absent</button>
                </div>
              </div>
            ))}
          </div>

          <div style={{ marginTop: 24, textAlign: 'right' }}>
            <button className="btn btn-primary btn-lg" onClick={handleSave} disabled={saving}>
              <Save size={20} /> {saving ? 'Saving...' : 'Save Attendance'}
            </button>
          </div>
        </>
      )}

      {students.length === 0 && selectedClass && (
        <EmptyState title="No students in this class" message="Select a different class or check student assignments." />
      )}
    </div>
  );
}
