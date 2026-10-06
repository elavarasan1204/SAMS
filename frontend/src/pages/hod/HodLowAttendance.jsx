import { useState, useEffect } from 'react';
import { hodService, reportService, authService } from '../../services/api';
import { LoadingSpinner, EmptyState, AttendanceBadge } from '../../components/Common';
import { AlertTriangle } from 'lucide-react';

export default function HodLowAttendance() {
  const [data, setData] = useState([]);
  const [loading, setLoading] = useState(true);
  const [threshold, setThreshold] = useState(75);
  const user = authService.getUser();

  useEffect(() => {
    const fetchData = async () => {
      try {
        const hodRes = await hodService.getByUserId(user.userId);
        const deptId = hodRes.data.data.department?.id;
        const res = await reportService.getLowAttendance({ departmentId: deptId, threshold });
        setData(res.data.data || []);
      } catch (err) { console.error(err); }
      finally { setLoading(false); }
    };
    fetchData();
  }, [threshold]);

  if (loading) return <LoadingSpinner />;

  return (
    <div>
      <div className="page-header">
        <h2>Low Attendance Students</h2>
        <div className="filter-bar" style={{ marginBottom: 0 }}>
          <label style={{ fontSize: 14, color: 'var(--text-secondary)' }}>Threshold:</label>
          <input className="form-control" type="number" style={{ maxWidth: 80 }} value={threshold} onChange={e => setThreshold(parseInt(e.target.value))} />
          <span style={{ color: 'var(--text-muted)' }}>%</span>
        </div>
      </div>

      {data.length === 0 ? (
        <EmptyState icon={<AlertTriangle size={64} />} title="No low attendance students" message={`All students have attendance above ${threshold}%.`} />
      ) : (
        <div className="table-container">
          <div className="table-header"><span className="table-title">{data.length} students below {threshold}%</span></div>
          <div className="table-wrapper">
            <table><thead><tr><th>Reg. No</th><th>Student</th><th>Class</th><th>Total</th><th>Present</th><th>Absent</th><th>Percentage</th><th>Status</th></tr></thead>
              <tbody>{data.map((r, i) => (
                <tr key={i}><td>{r.registerNumber}</td><td>{r.studentName}</td><td>{r.className}</td><td>{r.totalClasses}</td><td>{r.presentClasses}</td><td>{r.absentClasses}</td>
                  <td><strong className="text-danger">{r.percentage}%</strong></td><td><AttendanceBadge status={r.status} /></td>
                </tr>))}</tbody></table>
          </div>
        </div>
      )}
    </div>
  );
}
