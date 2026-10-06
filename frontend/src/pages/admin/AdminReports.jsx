import { useState, useEffect } from 'react';
import { reportService, subjectService, departmentService } from '../../services/api';
import { LoadingSpinner, EmptyState, AttendanceBadge } from '../../components/Common';
import { FileText, Printer } from 'lucide-react';

export default function AdminReports() {
  const [reportType, setReportType] = useState('subject');
  const [subjects, setSubjects] = useState([]);
  const [departments, setDepartments] = useState([]);
  const [selectedId, setSelectedId] = useState('');
  const [reportData, setReportData] = useState([]);
  const [loading, setLoading] = useState(false);

  useEffect(() => {
    subjectService.getAll().then(res => setSubjects(res.data.data || []));
    departmentService.getAll().then(res => setDepartments(res.data.data || []));
  }, []);

  const generateReport = async () => {
    if (!selectedId) return;
    setLoading(true);
    try {
      let res;
      if (reportType === 'subject') { res = await reportService.getSubjectReport(selectedId); }
      else if (reportType === 'lowAttendance') { res = await reportService.getLowAttendance({ departmentId: selectedId }); }
      setReportData(res.data.data || []);
    } catch { setReportData([]); }
    finally { setLoading(false); }
  };

  return (
    <div>
      <div className="page-header"><h2>Reports</h2><button className="btn btn-secondary no-print" onClick={() => window.print()}><Printer size={18} /> Print</button></div>
      <div className="filter-bar no-print">
        <select value={reportType} onChange={e => { setReportType(e.target.value); setSelectedId(''); setReportData([]); }}>
          <option value="subject">Subject Report</option>
          <option value="lowAttendance">Low Attendance</option>
        </select>
        {reportType === 'subject' && (
          <select value={selectedId} onChange={e => setSelectedId(e.target.value)}>
            <option value="">Select Subject</option>{subjects.map(s => <option key={s.id} value={s.id}>{s.name} ({s.subjectCode})</option>)}
          </select>
        )}
        {reportType === 'lowAttendance' && (
          <select value={selectedId} onChange={e => setSelectedId(e.target.value)}>
            <option value="">Select Department</option>{departments.map(d => <option key={d.id} value={d.id}>{d.name}</option>)}
          </select>
        )}
        <button className="btn btn-primary" onClick={generateReport}>Generate</button>
      </div>

      {loading ? <LoadingSpinner /> : reportData.length === 0 ? (
        <EmptyState icon={<FileText size={64} />} title="No report data" message="Select filters and click Generate." />
      ) : (
        <div className="table-container">
          <div className="table-header"><span className="table-title">Report Results ({reportData.length} records)</span></div>
          <div className="table-wrapper">
            <table><thead><tr><th>Reg. No</th><th>Student</th><th>Class</th><th>Total</th><th>Present</th><th>Absent</th><th>Percentage</th><th>Status</th></tr></thead>
              <tbody>{reportData.map((r, i) => (
                <tr key={i}><td>{r.registerNumber}</td><td>{r.studentName}</td><td>{r.className}</td><td>{r.totalClasses}</td><td>{r.presentClasses}</td><td>{r.absentClasses}</td>
                  <td><strong>{r.percentage}%</strong></td><td><AttendanceBadge status={r.status} /></td>
                </tr>))}</tbody></table>
          </div>
        </div>
      )}
    </div>
  );
}
