import { useState, useEffect } from 'react';
import { reportService, studentService, authService } from '../../services/api';
import DashboardCard from '../../components/DashboardCard';
import { LoadingSpinner, AttendanceBadge } from '../../components/Common';
import { BarChart3, CheckCircle, XCircle, TrendingUp } from 'lucide-react';

export default function StudentDashboard() {
  const [student, setStudent] = useState(null);
  const [overall, setOverall] = useState(null);
  const [subjectReports, setSubjectReports] = useState([]);
  const [loading, setLoading] = useState(true);
  const user = authService.getUser();

  useEffect(() => {
    const fetchData = async () => {
      try {
        const stuRes = await studentService.getByUserId(user.userId);
        const stu = stuRes.data.data;
        setStudent(stu);
        const overallRes = await reportService.getStudentOverall(stu.id);
        setOverall(overallRes.data.data);
        const subRes = await reportService.getStudentReport(stu.id);
        setSubjectReports(subRes.data.data || []);
      } catch (err) { console.error(err); }
      finally { setLoading(false); }
    };
    fetchData();
  }, []);

  if (loading) return <LoadingSpinner />;

  return (
    <div>
      <div className="page-header"><h2>Student Dashboard</h2></div>

      {overall && (
        <div className="dashboard-grid">
          <DashboardCard icon={<BarChart3 size={24} />} value={`${overall.percentage}%`} label="Overall Attendance" color={overall.status === 'GOOD' ? 'green' : overall.status === 'WARNING' ? 'orange' : 'red'} />
          <DashboardCard icon={<CheckCircle size={24} />} value={overall.presentClasses} label="Classes Attended" color="green" />
          <DashboardCard icon={<XCircle size={24} />} value={overall.absentClasses} label="Classes Missed" color="red" />
          <DashboardCard icon={<TrendingUp size={24} />} value={overall.totalClasses} label="Total Classes" color="blue" />
        </div>
      )}

      {overall && (
        <div className="table-container" style={{ marginBottom: 24 }}>
          <div style={{ padding: 24, textAlign: 'center' }}>
            <h3 style={{ marginBottom: 8 }}>Attendance Status</h3>
            <AttendanceBadge status={overall.status} />
            <div className="progress-bar" style={{ maxWidth: 400, margin: '16px auto' }}>
              <div className={`progress-fill ${overall.status === 'GOOD' ? 'good' : overall.status === 'WARNING' ? 'warning' : 'critical'}`}
                style={{ width: `${Math.min(overall.percentage, 100)}%` }} />
            </div>
            {overall.status === 'CRITICAL' && (
              <p style={{ color: 'var(--danger)', marginTop: 8, fontSize: 14, fontWeight: 600 }}>
                ⚠️ Your attendance is below 75%. Please attend classes regularly.
              </p>
            )}
          </div>
        </div>
      )}

      {subjectReports.length > 0 && (
        <div className="table-container">
          <div className="table-header"><span className="table-title">Subject-wise Attendance</span></div>
          <div className="table-wrapper">
            <table><thead><tr><th>Subject Code</th><th>Subject</th><th>Total</th><th>Present</th><th>Absent</th><th>Percentage</th><th>Status</th></tr></thead>
              <tbody>{subjectReports.map(r => (
                <tr key={r.subjectId}><td><strong>{r.subjectCode}</strong></td><td>{r.subjectName}</td><td>{r.totalClasses}</td><td>{r.presentClasses}</td><td>{r.absentClasses}</td>
                  <td>
                    <strong>{r.percentage}%</strong>
                    <div className="progress-bar"><div className={`progress-fill ${r.status === 'GOOD' ? 'good' : r.status === 'WARNING' ? 'warning' : 'critical'}`} style={{ width: `${Math.min(r.percentage, 100)}%` }} /></div>
                  </td>
                  <td><AttendanceBadge status={r.status} /></td>
                </tr>))}</tbody></table>
          </div>
        </div>
      )}
    </div>
  );
}
