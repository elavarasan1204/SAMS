import { useState, useEffect } from 'react';
import { facultySubjectService, facultyService, dashboardService } from '../../services/api';
import { authService } from '../../services/api';
import DashboardCard from '../../components/DashboardCard';
import { LoadingSpinner } from '../../components/Common';
import { BookOpen, School, ClipboardCheck, Calendar } from 'lucide-react';

export default function FacultyDashboard() {
  const [assignments, setAssignments] = useState([]);
  const [loading, setLoading] = useState(true);
  const user = authService.getUser();

  useEffect(() => {
    const fetchData = async () => {
      try {
        const facRes = await facultyService.getByUserId(user.userId);
        const faculty = facRes.data.data;
        const assRes = await facultySubjectService.getAll({ facultyId: faculty.id });
        setAssignments(assRes.data.data || []);
      } catch (err) { console.error(err); }
      finally { setLoading(false); }
    };
    fetchData();
  }, []);

  if (loading) return <LoadingSpinner />;

  const uniqueSubjects = [...new Set(assignments.map(a => a.subject?.id))].length;
  const uniqueClasses = [...new Set(assignments.map(a => a.classEntity?.id))].length;

  return (
    <div>
      <div className="page-header"><h2>Faculty Dashboard</h2></div>
      <div className="dashboard-grid">
        <DashboardCard icon={<BookOpen size={24} />} value={uniqueSubjects} label="Assigned Subjects" color="blue" />
        <DashboardCard icon={<School size={24} />} value={uniqueClasses} label="Assigned Classes" color="green" />
        <DashboardCard icon={<ClipboardCheck size={24} />} value={assignments.length} label="Total Assignments" color="orange" />
      </div>

      <div className="table-container">
        <div className="table-header"><span className="table-title">My Assignments</span></div>
        <div className="table-wrapper">
          <table><thead><tr><th>Subject</th><th>Code</th><th>Class</th><th>Academic Year</th></tr></thead>
            <tbody>{assignments.map(a => (
              <tr key={a.id}><td>{a.subject?.name}</td><td>{a.subject?.subjectCode}</td><td>{a.classEntity?.name}</td><td>{a.academicYear}</td></tr>
            ))}</tbody></table>
        </div>
      </div>
    </div>
  );
}
