import { Navigate } from 'react-router-dom';
import { authService } from '../services/api';

export function ProtectedRoute({ children }) {
  if (!authService.isAuthenticated()) {
    return <Navigate to="/login" replace />;
  }
  return children;
}

export function RoleBasedRoute({ children, allowedRoles }) {
  const role = authService.getRole();
  if (!authService.isAuthenticated()) {
    return <Navigate to="/login" replace />;
  }
  if (!allowedRoles.includes(role)) {
    return <Navigate to="/login" replace />;
  }
  return children;
}
