import { useState, useEffect } from 'react';
import { facultySubjectService, facultyService, attendanceService, authService } from '../../services/api';
import { LoadingSpinner, EmptyState, AttendanceBadge } from '../../components/Common';
import { History } from 'lucide-react';

export default function AttendanceHistory() {
  const [assignments, setAssignments] = useState([]);
  const [selectedSubject, setSelectedSubject] = useState('');
  const [selectedClass, setSelectedClass] = useState('');
  const [dates, setDates] = useState([]);
  const [selectedDate, setSelectedDate] = useState('');
  const [records, setRecords] = useState([]);
  const [loading, setLoading] = useState(true);
  const user = authService.getUser();

  useEffect(() => {
    const fetchData = async () => {
      try {
        const facRes = await facultyService.getByUserId(user.userId);
        const assRes = await facultySubjectService.getAll({ facultyId: facRes.data.data.id });
        setAssignments(assRes.data.data || []);
      } catch (err) { console.error(err); }
      finally { setLoading(false); }
    };
    fetchData();
  }, []);

  useEffect(() => {
    if (selectedSubject && selectedClass) {
      attendanceService.getDates(selectedSubject, selectedClass)
        .then(res => setDates(res.data.data || []))
        .catch(() => setDates([]));
    }
  }, [selectedSubject, selectedClass]);

  useEffect(() => {
    if (selectedDate && selectedSubject && selectedClass) {
      attendanceService.getAll({ subjectId: selectedSubject, classId: selectedClass, date: selectedDate })
        .then(res => setRecords(res.data.data || []))
        .catch(() => setRecords([]));
    }
  }, [selectedDate]);

  const subjectClassPairs = assignments.map(a => ({
    subjectId: a.subject?.id, subjectName: `${a.subject?.name} (${a.subject?.subjectCode})`,
    classId: a.classEntity?.id, className: a.classEntity?.name
  }));
  const uniqueSubjects = [...new Map(subjectClassPairs.map(p => [p.subjectId, p])).values()];
  const availableClasses = subjectClassPairs.filter(p => p.subjectId == selectedSubject);

  if (loading) return <LoadingSpinner />;

  return (
    <div>
      <div className="page-header"><h2>Attendance History</h2></div>
      <div className="filter-bar">
        <select className="form-control" style={{ maxWidth: 280 }} value={selectedSubject} onChange={e => { setSelectedSubject(e.target.value); setSelectedClass(''); setRecords([]); }}>
          <option value="">Select Subject</option>{uniqueSubjects.map(s => <option key={s.subjectId} value={s.subjectId}>{s.subjectName}</option>)}
        </select>
        <select className="form-control" style={{ maxWidth: 200 }} value={selectedClass} onChange={e => { setSelectedClass(e.target.value); setRecords([]); }}>
          <option value="">Select Class</option>{availableClasses.map(c => <option key={c.classId} value={c.classId}>{c.className}</option>)}
        </select>
        <select className="form-control" style={{ maxWidth: 180 }} value={selectedDate} onChange={e => setSelectedDate(e.target.value)}>
          <option value="">Select Date</option>{dates.map(d => <option key={d} value={d}>{d}</option>)}
        </select>
      </div>

      {records.length > 0 ? (
        <div className="table-container">
          <div className="table-header"><span className="table-title">Attendance for {selectedDate}</span></div>
          <div className="table-wrapper">
            <table><thead><tr><th>Reg. No</th><th>Student</th><th>Status</th></tr></thead>
              <tbody>{records.map(r => (
                <tr key={r.id}><td>{r.student?.registerNumber}</td><td>{r.student?.fullName}</td>
                  <td><span className={`badge ${r.status === 'PRESENT' ? 'badge-good' : 'badge-critical'}`}>{r.status}</span></td>
                </tr>))}</tbody></table>
          </div>
        </div>
      ) : (
        <EmptyState icon={<History size={64} />} title="No records" message="Select subject, class, and date to view attendance." />
      )}
    </div>
  );
}
