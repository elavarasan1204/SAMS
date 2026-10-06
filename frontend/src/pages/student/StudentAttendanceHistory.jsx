import { useState, useEffect } from 'react';
import { attendanceService, studentService, authService } from '../../services/api';
import { LoadingSpinner, EmptyState } from '../../components/Common';
import { Calendar } from 'lucide-react';

export default function StudentAttendanceHistory() {
  const [records, setRecords] = useState([]);
  const [loading, setLoading] = useState(true);
  const user = authService.getUser();

  useEffect(() => {
    const fetchData = async () => {
      try {
        const stuRes = await studentService.getByUserId(user.userId);
        const res = await attendanceService.getAll({ studentId: stuRes.data.data.id });
        setRecords(res.data.data || []);
      } catch (err) { console.error(err); }
      finally { setLoading(false); }
    };
    fetchData();
  }, []);

  if (loading) return <LoadingSpinner />;

  return (
    <div>
      <div className="page-header"><h2>Attendance History</h2></div>
      {records.length === 0 ? <EmptyState icon={<Calendar size={64} />} title="No attendance records" /> : (
        <div className="table-container">
          <div className="table-header"><span className="table-title">{records.length} records</span></div>
          <div className="table-wrapper">
            <table><thead><tr><th>Date</th><th>Subject</th><th>Class</th><th>Status</th></tr></thead>
              <tbody>{records.sort((a, b) => new Date(b.date) - new Date(a.date)).map(r => (
                <tr key={r.id}><td>{r.date}</td><td>{r.subject?.name} ({r.subject?.subjectCode})</td><td>{r.classEntity?.name}</td>
                  <td><span className={`badge ${r.status === 'PRESENT' ? 'badge-good' : 'badge-critical'}`}>{r.status}</span></td>
                </tr>))}</tbody></table>
          </div>
        </div>
      )}
    </div>
  );
}
