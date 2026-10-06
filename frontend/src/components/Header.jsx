import { Menu } from 'lucide-react';
import { authService } from '../services/api';

export default function Header({ title, onMenuClick }) {
  const user = authService.getUser();
  const initials = user?.fullName?.split(' ').map(n => n[0]).join('').toUpperCase() || 'U';

  return (
    <header className="header">
      <div className="header-left">
        <button className="mobile-menu-btn" onClick={onMenuClick}>
          <Menu size={24} />
        </button>
        <h1>{title}</h1>
      </div>
      <div className="header-right">
        <div className="user-info">
          <div>
            <div className="user-name">{user?.fullName}</div>
            <div className="user-role">{user?.role}</div>
          </div>
          <div className="user-avatar">{initials}</div>
        </div>
      </div>
    </header>
  );
}
