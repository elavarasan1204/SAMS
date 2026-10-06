import { useState, useEffect } from 'react';
import { studentService, authService } from '../../services/api';
import { LoadingSpinner } from '../../components/Common';
import { User } from 'lucide-react';

export default function StudentProfile() {
  const [student, setStudent] = useState(null);
  const [loading, setLoading] = useState(true);
  const user = authService.getUser();

  useEffect(() => {
    studentService.getByUserId(user.userId)
      .then(res => setStudent(res.data.data))
      .catch(console.error)
      .finally(() => setLoading(false));
  }, []);

  if (loading) return <LoadingSpinner />;
  if (!student) return <div className="empty-state"><h3>Profile not found</h3></div>;

  const initials = student.fullName?.split(' ').map(n => n[0]).join('').toUpperCase();

  return (
    <div>
      <div className="page-header"><h2>My Profile</h2></div>
      <div className="profile-card">
        <div className="profile-header">
          <div className="profile-avatar">{initials}</div>
          <div>
            <h2 style={{ fontSize: 24, fontWeight: 700 }}>{student.fullName}</h2>
            <p style={{ color: 'var(--text-secondary)' }}>{student.registerNumber}</p>
          </div>
        </div>
        <div className="profile-details">
          <div className="profile-field"><label>Register Number</label><p>{student.registerNumber}</p></div>
          <div className="profile-field"><label>Email</label><p>{student.email}</p></div>
          <div className="profile-field"><label>Phone</label><p>{student.phone || '-'}</p></div>
          <div className="profile-field"><label>Date of Birth</label><p>{student.dateOfBirth || '-'}</p></div>
          <div className="profile-field"><label>Gender</label><p>{student.gender || '-'}</p></div>
          <div className="profile-field"><label>Department</label><p>{student.department?.name || '-'}</p></div>
          <div className="profile-field"><label>Class</label><p>{student.classEntity?.name || '-'}</p></div>
          <div className="profile-field"><label>Year</label><p>{student.year}</p></div>
          <div className="profile-field"><label>Semester</label><p>{student.semester}</p></div>
        </div>
      </div>
    </div>
  );
}
