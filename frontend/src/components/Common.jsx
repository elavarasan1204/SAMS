import { X, AlertTriangle } from 'lucide-react';

export function Modal({ isOpen, onClose, title, children, footer }) {
  if (!isOpen) return null;
  return (
    <div className="modal-overlay" onClick={onClose}>
      <div className="modal" onClick={e => e.stopPropagation()}>
        <div className="modal-header">
          <h2>{title}</h2>
          <button className="modal-close" onClick={onClose}><X size={20} /></button>
        </div>
        <div className="modal-body">{children}</div>
        {footer && <div className="modal-footer">{footer}</div>}
      </div>
    </div>
  );
}

export function ConfirmDialog({ isOpen, onClose, onConfirm, title, message }) {
  if (!isOpen) return null;
  return (
    <div className="modal-overlay" onClick={onClose}>
      <div className="modal" onClick={e => e.stopPropagation()} style={{ maxWidth: 420 }}>
        <div className="modal-body">
          <div className="confirm-dialog">
            <AlertTriangle size={48} />
            <h3>{title || 'Are you sure?'}</h3>
            <p>{message || 'This action cannot be undone.'}</p>
            <div className="btn-group" style={{ justifyContent: 'center' }}>
              <button className="btn btn-secondary" onClick={onClose}>Cancel</button>
              <button className="btn btn-danger" onClick={onConfirm}>Delete</button>
            </div>
          </div>
        </div>
      </div>
    </div>
  );
}

export function LoadingSpinner() {
  return (
    <div className="loading-spinner">
      <div className="spinner"></div>
    </div>
  );
}

export function EmptyState({ icon, title, message }) {
  return (
    <div className="empty-state">
      {icon}
      <h3>{title || 'No data found'}</h3>
      <p>{message || 'There are no records to display.'}</p>
    </div>
  );
}

export function AttendanceBadge({ status }) {
  const cls = status === 'GOOD' ? 'badge-good' : status === 'WARNING' ? 'badge-warning' : 'badge-critical';
  return <span className={`badge ${cls}`}>{status}</span>;
}

export function SearchBar({ value, onChange, placeholder }) {
  return (
    <div className="search-bar">
      <svg xmlns="http://www.w3.org/2000/svg" viewBox="0 0 24 24" fill="none" stroke="currentColor" strokeWidth="2" strokeLinecap="round" strokeLinejoin="round"><circle cx="11" cy="11" r="8"/><path d="m21 21-4.3-4.3"/></svg>
      <input
        type="text"
        value={value}
        onChange={e => onChange(e.target.value)}
        placeholder={placeholder || 'Search...'}
      />
    </div>
  );
}
