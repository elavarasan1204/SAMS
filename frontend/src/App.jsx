import { BrowserRouter, Routes, Route, Navigate } from 'react-router-dom';
import { RoleBasedRoute } from './components/ProtectedRoute';
import DashboardLayout from './layouts/DashboardLayout';
import Login from './pages/Login';

// Admin
import AdminDashboard from './pages/admin/AdminDashboard';
import ManageStudents from './pages/admin/ManageStudents';
import ManageFaculty from './pages/admin/ManageFaculty';
import ManageHod from './pages/admin/ManageHod';
import ManageDepartments from './pages/admin/ManageDepartments';
import ManageSubjects from './pages/admin/ManageSubjects';
import ManageClasses from './pages/admin/ManageClasses';
import ManageAssignments from './pages/admin/ManageAssignments';
import AdminReports from './pages/admin/AdminReports';

// Faculty
import FacultyDashboard from './pages/faculty/FacultyDashboard';
import MarkAttendance from './pages/faculty/MarkAttendance';
import AttendanceHistory from './pages/faculty/AttendanceHistory';

// Student
import StudentDashboard from './pages/student/StudentDashboard';
import StudentProfile from './pages/student/StudentProfile';
import StudentAttendanceHistory from './pages/student/StudentAttendanceHistory';

// HOD
import HodDashboard from './pages/hod/HodDashboard';
import HodLowAttendance from './pages/hod/HodLowAttendance';

import {
  LayoutDashboard, Users, UserCheck, Building2, BookOpen, School, Link,
  FileText, ClipboardCheck, History, User, GraduationCap, BarChart3,
  Calendar, AlertTriangle
} from 'lucide-react';

// Sidebar links
const adminLinks = [
  { path: '/admin/dashboard', label: 'Dashboard', icon: <LayoutDashboard size={20} />, end: true },
  { path: '/admin/students', label: 'Students', icon: <GraduationCap size={20} /> },
  { path: '/admin/faculty', label: 'Faculty', icon: <Users size={20} /> },
  { path: '/admin/hod', label: 'HOD', icon: <UserCheck size={20} /> },
  { path: '/admin/departments', label: 'Departments', icon: <Building2 size={20} /> },
  { path: '/admin/subjects', label: 'Subjects', icon: <BookOpen size={20} /> },
  { path: '/admin/classes', label: 'Classes', icon: <School size={20} /> },
  { path: '/admin/faculty-assignments', label: 'Faculty Assignments', icon: <Link size={20} /> },
  { path: '/admin/reports', label: 'Reports', icon: <FileText size={20} /> },
];

const hodLinks = [
  { path: '/hod/dashboard', label: 'Dashboard', icon: <LayoutDashboard size={20} />, end: true },
  { path: '/hod/students', label: 'Students', icon: <GraduationCap size={20} /> },
  { path: '/hod/faculty', label: 'Faculty', icon: <Users size={20} /> },
  { path: '/hod/subjects', label: 'Subjects', icon: <BookOpen size={20} /> },
  { path: '/hod/low-attendance', label: 'Low Attendance', icon: <AlertTriangle size={20} /> },
  { path: '/hod/reports', label: 'Reports', icon: <FileText size={20} /> },
];

const facultyLinks = [
  { path: '/faculty/dashboard', label: 'Dashboard', icon: <LayoutDashboard size={20} />, end: true },
  { path: '/faculty/attendance/mark', label: 'Mark Attendance', icon: <ClipboardCheck size={20} /> },
  { path: '/faculty/attendance/history', label: 'Attendance History', icon: <History size={20} /> },
  { path: '/faculty/reports', label: 'Reports', icon: <FileText size={20} /> },
];

const studentLinks = [
  { path: '/student/dashboard', label: 'Dashboard', icon: <LayoutDashboard size={20} />, end: true },
  { path: '/student/profile', label: 'My Profile', icon: <User size={20} /> },
  { path: '/student/attendance', label: 'My Attendance', icon: <BarChart3 size={20} /> },
  { path: '/student/history', label: 'Attendance History', icon: <Calendar size={20} /> },
];

export default function App() {
  return (
    <BrowserRouter>
      <Routes>
        <Route path="/login" element={<Login />} />
        <Route path="/" element={<Navigate to="/login" replace />} />

        {/* Admin Routes */}
        <Route path="/admin" element={
          <RoleBasedRoute allowedRoles={['ADMIN']}>
            <DashboardLayout links={adminLinks} role="Admin" title="Admin Panel" />
          </RoleBasedRoute>
        }>
          <Route path="dashboard" element={<AdminDashboard />} />
          <Route path="students" element={<ManageStudents />} />
          <Route path="faculty" element={<ManageFaculty />} />
          <Route path="hod" element={<ManageHod />} />
          <Route path="departments" element={<ManageDepartments />} />
          <Route path="subjects" element={<ManageSubjects />} />
          <Route path="classes" element={<ManageClasses />} />
          <Route path="faculty-assignments" element={<ManageAssignments />} />
          <Route path="reports" element={<AdminReports />} />
        </Route>

        {/* HOD Routes */}
        <Route path="/hod" element={
          <RoleBasedRoute allowedRoles={['HOD']}>
            <DashboardLayout links={hodLinks} role="HOD" title="HOD Panel" />
          </RoleBasedRoute>
        }>
          <Route path="dashboard" element={<HodDashboard />} />
          <Route path="students" element={<ManageStudents />} />
          <Route path="faculty" element={<ManageFaculty />} />
          <Route path="subjects" element={<ManageSubjects />} />
          <Route path="low-attendance" element={<HodLowAttendance />} />
          <Route path="reports" element={<AdminReports />} />
        </Route>

        {/* Faculty Routes */}
        <Route path="/faculty" element={
          <RoleBasedRoute allowedRoles={['FACULTY']}>
            <DashboardLayout links={facultyLinks} role="Faculty" title="Faculty Panel" />
          </RoleBasedRoute>
        }>
          <Route path="dashboard" element={<FacultyDashboard />} />
          <Route path="attendance/mark" element={<MarkAttendance />} />
          <Route path="attendance/history" element={<AttendanceHistory />} />
          <Route path="reports" element={<AdminReports />} />
        </Route>

        {/* Student Routes */}
        <Route path="/student" element={
          <RoleBasedRoute allowedRoles={['STUDENT']}>
            <DashboardLayout links={studentLinks} role="Student" title="Student Portal" />
          </RoleBasedRoute>
        }>
          <Route path="dashboard" element={<StudentDashboard />} />
          <Route path="profile" element={<StudentProfile />} />
          <Route path="attendance" element={<StudentDashboard />} />
          <Route path="history" element={<StudentAttendanceHistory />} />
        </Route>

        {/* Fallback */}
        <Route path="*" element={<Navigate to="/login" replace />} />
      </Routes>
    </BrowserRouter>
  );
}
