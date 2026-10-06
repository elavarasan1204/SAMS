import { NavLink, useNavigate } from 'react-router-dom';
import { authService } from '../services/api';
import { LogOut, GraduationCap } from 'lucide-react';

export default function Sidebar({ links, role, isOpen, onClose }) {
  const navigate = useNavigate();
  const user = authService.getUser();

  const handleLogout = () => {
    authService.logout();
    navigate('/login');
  };

  return (
    <>
      <div className={`sidebar-overlay ${isOpen ? 'open' : ''}`} onClick={onClose} />
      <aside className={`sidebar ${isOpen ? 'open' : ''}`}>
        <div className="sidebar-header">
          <GraduationCap size={28} />
          <div>
            <h2>SAMS</h2>
            <span className="role-badge">{role}</span>
          </div>
        </div>

        <nav className="sidebar-nav">
          {links.map(link => (
            <NavLink
              key={link.path}
              to={link.path}
              className={({ isActive }) => `sidebar-link ${isActive ? 'active' : ''}`}
              onClick={onClose}
              end={link.end}
            >
              {link.icon}
              <span>{link.label}</span>
            </NavLink>
          ))}
        </nav>

        <div className="sidebar-footer">
          <button className="sidebar-link" onClick={handleLogout}>
            <LogOut size={20} />
            <span>Logout</span>
          </button>
        </div>
      </aside>
    </>
  );
}
