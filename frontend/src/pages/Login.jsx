import { useState } from 'react';
import { useNavigate } from 'react-router-dom';
import { authService } from '../services/api';
import { GraduationCap, Eye, EyeOff, ShieldCheck, UserCheck, UserPlus, User } from 'lucide-react';
import toast from 'react-hot-toast';

export default function Login() {
  const [username, setUsername] = useState('');
  const [password, setPassword] = useState('');
  const [showPassword, setShowPassword] = useState(false);
  const [loading, setLoading] = useState(false);
  const [error, setError] = useState('');
  const navigate = useNavigate();

  const demoAccounts = [
    { role: 'Admin', icon: ShieldCheck, user: 'admin', pass: 'Admin@123', color: 'badge-purple' },
    { role: 'HOD', icon: UserCheck, user: 'hod', pass: 'Hod@123', color: 'badge-blue' },
    { role: 'Faculty', icon: UserPlus, user: 'faculty', pass: 'Faculty@123', color: 'badge-green' },
    { role: 'Student', icon: User, user: 'student', pass: 'Student@123', color: 'badge-amber' },
  ];

  const handleQuickFill = (acc) => {
    setUsername(acc.user);
    setPassword(acc.pass);
    setError('');
    toast.success(`Loaded credentials for ${acc.role} (${acc.user})`);
  };

  const handleLogin = async (e) => {
    e.preventDefault();
    setError('');
    if (!username.trim() || !password.trim()) {
      setError('Please enter both username and password.');
      return;
    }
    setLoading(true);
    try {
      const res = await authService.login({ username, password });
      const data = res.data.data;
      localStorage.setItem('token', data.token);
      localStorage.setItem('user', JSON.stringify({
        userId: data.userId,
        username: data.username,
        fullName: data.fullName,
        email: data.email,
        role: data.role
      }));
      toast.success(`Welcome, ${data.fullName}!`);
      const routes = {
        ADMIN: '/admin/dashboard',
        HOD: '/hod/dashboard',
        FACULTY: '/faculty/dashboard',
        STUDENT: '/student/dashboard'
      };
      navigate(routes[data.role] || '/login');
    } catch (err) {
      setError(err.response?.data?.message || 'Invalid username or password.');
    } finally {
      setLoading(false);
    }
  };

  return (
    <div className="login-page">
      <div className="login-card">
        <div className="logo-icon">
          <GraduationCap size={56} />
        </div>
        <h1>SAMS</h1>
        <p className="subtitle">Student Attendance Management System</p>

        {/* Dynamic Demo Credentials Helper */}
        <div className="demo-credentials-box" style={{
          marginBottom: '20px',
          padding: '12px 14px',
          background: 'rgba(255, 255, 255, 0.04)',
          borderRadius: '12px',
          border: '1px solid rgba(255, 255, 255, 0.08)'
        }}>
          <p style={{
            fontSize: '11px',
            fontWeight: 600,
            textTransform: 'uppercase',
            letterSpacing: '0.05em',
            color: 'var(--text-muted)',
            marginBottom: '10px',
            textAlign: 'center'
          }}>
            ⚡ Click to Auto-Fill Dynamic Test Account:
          </p>
          <div style={{ display: 'grid', gridTemplateColumns: '1fr 1fr', gap: '8px' }}>
            {demoAccounts.map((acc) => {
              const IconComp = acc.icon;
              return (
                <button
                  key={acc.role}
                  type="button"
                  onClick={() => handleQuickFill(acc)}
                  style={{
                    display: 'flex',
                    alignItems: 'center',
                    justifyContent: 'center',
                    gap: '6px',
                    padding: '8px 10px',
                    borderRadius: '8px',
                    border: '1px solid var(--border-color)',
                    background: username === acc.user ? 'rgba(99, 102, 241, 0.2)' : 'rgba(255, 255, 255, 0.03)',
                    color: 'var(--text-main)',
                    fontSize: '12px',
                    fontWeight: 500,
                    cursor: 'pointer',
                    transition: 'all 0.2s ease'
                  }}
                >
                  <IconComp size={14} />
                  <span>{acc.role}</span>
                </button>
              );
            })}
          </div>
        </div>

        <form onSubmit={handleLogin}>
          <div className="form-group">
            <label>Username</label>
            <input
              type="text"
              className="form-control"
              placeholder="Enter username or click demo role above"
              value={username}
              onChange={e => setUsername(e.target.value)}
              autoFocus
            />
          </div>

          <div className="form-group">
            <label>Password</label>
            <div className="password-input">
              <input
                type={showPassword ? 'text' : 'password'}
                className="form-control"
                placeholder="Enter password"
                value={password}
                onChange={e => setPassword(e.target.value)}
              />
              <button
                type="button"
                className="password-toggle"
                onClick={() => setShowPassword(!showPassword)}
              >
                {showPassword ? <EyeOff size={18} /> : <Eye size={18} />}
              </button>
            </div>
          </div>

          {error && <div className="form-error" style={{ marginBottom: 16 }}>{error}</div>}

          <button type="submit" className="btn btn-primary btn-lg btn-block" disabled={loading}>
            {loading ? 'Signing in...' : 'Sign In'}
          </button>
        </form>

        <div style={{ marginTop: 20, textAlign: 'center', fontSize: 11, color: 'var(--text-muted)' }}>
          <p style={{ margin: 0 }}>Passwords: <strong>Role@123</strong> (e.g. Admin@123, Hod@123)</p>
        </div>
      </div>
    </div>
  );
}
