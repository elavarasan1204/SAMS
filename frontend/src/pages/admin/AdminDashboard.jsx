import { useState, useEffect } from 'react';
import { dashboardService } from '../../services/api';
import DashboardCard from '../../components/DashboardCard';
import { LoadingSpinner } from '../../components/Common';
import { Users, UserCheck, GraduationCap, Building2, BookOpen, School } from 'lucide-react';

export default function AdminDashboard() {
  const [stats, setStats] = useState(null);
  const [loading, setLoading] = useState(true);

  useEffect(() => {
    dashboardService.getStats()
      .then(res => setStats(res.data.data))
      .catch(console.error)
      .finally(() => setLoading(false));
  }, []);

  if (loading) return <LoadingSpinner />;

  return (
    <div>
      <div className="page-header">
        <h2>Admin Dashboard</h2>
      </div>

      <div className="dashboard-grid">
        <DashboardCard icon={<GraduationCap size={24} />} value={stats?.totalStudents || 0} label="Total Students" color="blue" />
        <DashboardCard icon={<Users size={24} />} value={stats?.totalFaculty || 0} label="Total Faculty" color="green" />
        <DashboardCard icon={<UserCheck size={24} />} value={stats?.totalHods || 0} label="Total HODs" color="orange" />
        <DashboardCard icon={<Building2 size={24} />} value={stats?.totalDepartments || 0} label="Departments" color="cyan" />
        <DashboardCard icon={<BookOpen size={24} />} value={stats?.totalSubjects || 0} label="Total Subjects" color="purple" />
        <DashboardCard icon={<School size={24} />} value={stats?.totalClasses || 0} label="Total Classes" color="red" />
      </div>
    </div>
  );
}
