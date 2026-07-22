import { useEffect, useState } from 'react';
import { Link } from 'react-router-dom';
import { getApplications, deleteApplication } from '../api/applications';
import ConfirmDialog from '../components/ConfirmDialog';

function ApplicationsListPage() {
  const [applications, setApplications] = useState([]);
  const [loading, setLoading] = useState(true);
  const [error, setError] = useState(null);
  const [pendingDeleteId, setPendingDeleteId] = useState(null);

  const fetchApplications = async () => {
    try {
      setLoading(true);
      setError(null);
      const data = await getApplications();
      setApplications(data);
    } catch (err) {
      setError('Unable to load applications. Is the backend running?');
    } finally {
      setLoading(false);
    }
  };

  useEffect(() => {
    fetchApplications();
  }, []);

  const handleDeleteConfirmed = async () => {
    try {
      await deleteApplication(pendingDeleteId);
      setPendingDeleteId(null);
      await fetchApplications();
    } catch (err) {
      setError('Unable to delete application.');
      setPendingDeleteId(null);
    }
  };

  if (loading) {
    return <p className="status-message">Loading applications...</p>;
  }

  return (
    <div className="page">
      <div className="page-header">
        <h1>Applications</h1>
        <Link to="/applications/new" className="btn btn-primary">
          + New Application
        </Link>
      </div>

      {error && <p className="status-message status-error">{error}</p>}

      {applications.length === 0 ? (
        <p className="status-message">No applications yet.</p>
      ) : (
        <table>
          <thead>
            <tr>
              <th>Name</th>
              <th>Description</th>
              <th>Repository</th>
              <th>Created</th>
              <th></th>
            </tr>
          </thead>
          <tbody>
            {applications.map((app) => (
              <tr key={app.id}>
                <td>
                  <Link to={`/applications/${app.id}`}>{app.name}</Link>
                </td>
                <td>{app.description}</td>
                <td>{app.repositoryUrl}</td>
                <td>{new Date(app.createdAt).toLocaleString()}</td>
                <td>
                  <button
                    className="btn btn-danger btn-small"
                    onClick={() => setPendingDeleteId(app.id)}
                  >
                    Delete
                  </button>
                </td>
              </tr>
            ))}
          </tbody>
        </table>
      )}

      {pendingDeleteId && (
        <ConfirmDialog
          message="Delete this application? This cannot be undone."
          onConfirm={handleDeleteConfirmed}
          onCancel={() => setPendingDeleteId(null)}
        />
      )}
    </div>
  );
}

export default ApplicationsListPage;