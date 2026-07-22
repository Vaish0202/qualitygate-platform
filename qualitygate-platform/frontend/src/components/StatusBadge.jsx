const STATUS_STYLES = {
  PENDING: { background: '#fef3c7', color: '#92400e' },
  AWAITING_APPROVAL: { background: '#fef3c7', color: '#92400e' },
  APPROVED: { background: '#dbeafe', color: '#1e40af' },
  DEPLOYED: { background: '#d1fae5', color: '#065f46' },
  FAILED: { background: '#fee2e2', color: '#991b1b' },
  ROLLED_BACK: { background: '#e5e7eb', color: '#374151' },
  PASSED: { background: '#d1fae5', color: '#065f46' },
  SKIPPED: { background: '#e5e7eb', color: '#374151' },
};

function StatusBadge({ status }) {
  const style = STATUS_STYLES[status] || { background: '#e5e7eb', color: '#374151' };
  return (
    <span
      className="status-badge"
      style={{ backgroundColor: style.background, color: style.color }}
    >
      {status}
    </span>
  );
}

export default StatusBadge;