import { useState, useEffect } from 'react';
import { hodService, studentService, facultyService, subjectService, authService, dashboardService } from '../../services/api';
import DashboardCard from '../../components/DashboardCard';
import { LoadingSpinner } from '../../components/Common';
import { Users, GraduationCap, BookOpen, AlertTriangle } from 'lucide-react';

export default function HodDashboard() {
  const [stats, setStats] = useState({ students: 0, faculty: 0, subjects: 0 });
  const [loading, setLoading] = useState(true);
  const user = authService.getUser();

  useEffect(() => {
    const fetchData = async () => {
      try {
        const hodRes = await hodService.getByUserId(user.userId);
        const hod = hodRes.data.data;
        const deptId = hod.department?.id;

        const [stuRes, facRes, subRes] = await Promise.all([
          studentService.getAll({ departmentId: deptId }),
          facultyService.getAll({ departmentId: deptId }),
          subjectService.getAll({ departmentId: deptId })
        ]);

        setStats({
          students: (stuRes.data.data || []).length,
          faculty: (facRes.data.data || []).length,
          subjects: (subRes.data.data || []).length,
          department: hod.department?.name
        });
      } catch (err) { console.error(err); }
      finally { setLoading(false); }
    };
    fetchData();
  }, []);

  if (loading) return <LoadingSpinner />;

  return (
    <div>
      <div className="page-header"><h2>HOD Dashboard — {stats.department}</h2></div>
      <div className="dashboard-grid">
        <DashboardCard icon={<GraduationCap size={24} />} value={stats.students} label="Department Students" color="blue" />
        <DashboardCard icon={<Users size={24} />} value={stats.faculty} label="Department Faculty" color="green" />
        <DashboardCard icon={<BookOpen size={24} />} value={stats.subjects} label="Department Subjects" color="orange" />
      </div>
    </div>
  );
}
