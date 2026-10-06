import axios from 'axios';

const API_URL = '/api';

const api = axios.create({
  baseURL: API_URL,
  headers: { 'Content-Type': 'application/json' }
});

// Add auth token to requests
api.interceptors.request.use(config => {
  const token = localStorage.getItem('token');
  if (token) {
    config.headers.Authorization = `Bearer ${token}`;
  }
  return config;
});

// Handle 401 responses
api.interceptors.response.use(
  response => response,
  error => {
    if (error.response?.status === 401) {
      localStorage.clear();
      window.location.href = '/login';
    }
    return Promise.reject(error);
  }
);

export default api;

// ===== Auth Service =====
export const authService = {
  login: (credentials) => api.post('/auth/login', credentials),
  logout: () => {
    localStorage.clear();
    window.location.href = '/login';
  },
  getUser: () => {
    const user = localStorage.getItem('user');
    return user ? JSON.parse(user) : null;
  },
  isAuthenticated: () => !!localStorage.getItem('token'),
  getRole: () => {
    const user = localStorage.getItem('user');
    return user ? JSON.parse(user).role : null;
  }
};

// ===== Dashboard Service =====
export const dashboardService = {
  getStats: () => api.get('/dashboard/stats')
};

// ===== Student Service =====
export const studentService = {
  getAll: (params) => api.get('/students', { params }),
  getById: (id) => api.get(`/students/${id}`),
  getByUserId: (userId) => api.get(`/students/user/${userId}`),
  create: (data) => api.post('/students', data),
  update: (id, data) => api.put(`/students/${id}`, data),
  delete: (id) => api.delete(`/students/${id}`)
};

// ===== Faculty Service =====
export const facultyService = {
  getAll: (params) => api.get('/faculty', { params }),
  getById: (id) => api.get(`/faculty/${id}`),
  getByUserId: (userId) => api.get(`/faculty/user/${userId}`),
  create: (data) => api.post('/faculty', data),
  update: (id, data) => api.put(`/faculty/${id}`, data),
  delete: (id) => api.delete(`/faculty/${id}`)
};

// ===== HOD Service =====
export const hodService = {
  getAll: () => api.get('/hod'),
  getById: (id) => api.get(`/hod/${id}`),
  getByUserId: (userId) => api.get(`/hod/user/${userId}`),
  getByDepartment: (deptId) => api.get(`/hod/department/${deptId}`),
  create: (data) => api.post('/hod', data),
  update: (id, data) => api.put(`/hod/${id}`, data),
  delete: (id) => api.delete(`/hod/${id}`)
};

// ===== Department Service =====
export const departmentService = {
  getAll: () => api.get('/departments'),
  getById: (id) => api.get(`/departments/${id}`),
  create: (data) => api.post('/departments', data),
  update: (id, data) => api.put(`/departments/${id}`, data),
  delete: (id) => api.delete(`/departments/${id}`)
};

// ===== Subject Service =====
export const subjectService = {
  getAll: (params) => api.get('/subjects', { params }),
  getById: (id) => api.get(`/subjects/${id}`),
  create: (data) => api.post('/subjects', data),
  update: (id, data) => api.put(`/subjects/${id}`, data),
  delete: (id) => api.delete(`/subjects/${id}`)
};

// ===== Class Service =====
export const classService = {
  getAll: (params) => api.get('/classes', { params }),
  getById: (id) => api.get(`/classes/${id}`),
  create: (data) => api.post('/classes', data),
  update: (id, data) => api.put(`/classes/${id}`, data),
  delete: (id) => api.delete(`/classes/${id}`)
};

// ===== Faculty Subject Service =====
export const facultySubjectService = {
  getAll: (params) => api.get('/faculty-subjects', { params }),
  create: (data) => api.post('/faculty-subjects', data),
  delete: (id) => api.delete(`/faculty-subjects/${id}`)
};

// ===== Attendance Service =====
export const attendanceService = {
  mark: (data) => api.post('/attendance', data),
  getAll: (params) => api.get('/attendance', { params }),
  update: (id, data) => api.put(`/attendance/${id}`, data),
  getDates: (subjectId, classId) => api.get('/attendance/dates', { params: { subjectId, classId } })
};

// ===== Report Service =====
export const reportService = {
  getStudentReport: (studentId) => api.get(`/reports/student/${studentId}`),
  getStudentOverall: (studentId) => api.get(`/reports/student/${studentId}/overall`),
  getSubjectReport: (subjectId) => api.get(`/reports/subject/${subjectId}`),
  getLowAttendance: (params) => api.get('/reports/low-attendance', { params }),
  getDepartmentReport: (deptId) => api.get(`/reports/department/${deptId}`)
};
