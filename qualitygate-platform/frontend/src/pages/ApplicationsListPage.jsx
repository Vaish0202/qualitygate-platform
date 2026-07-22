import { useEffect, useState } from 'react';
import { Link } from 'react-router-dom';
import { getApplications } from '../api/applications';

function ApplicationsListPage() {
  const [applications, setApplications] = useState([]);
  const [loading, setLoading] = useState(true);
  const [error, setError] = useState(null);

  useEffect(() => {
    let isMounted = true;

    async function fetchApplications() {
      try {
        setLoading(true);
        setError(null);
        const data = await getApplications();
        if (isMounted) {
          setApplications(data);
        }
      } catch (err) {
        if (isMounted) {
          setError('Unable to load applications. Is the backend running?');
        }
      } finally {
        if (isMounted) {
          setLoading(false);
        }
      }
    }

    fetchApplications();

    return () => {
      isMounted = false;
    };
  }, []);

  if (loading) {
    return <p className="status-message">Loading applications...</p>;
  }

  if (error) {
    return <p className="status-message status-error">{error}</p>;
  }

  if (applications.length === 0) {
    return <p className="status-message">No applications yet.</p>;
  }

  return (
    <div className="applications-list">
      <h1>Applications</h1>
      <table>
        <thead>
          <tr>
            <th>Name</th>
            <th>Description</th>
            <th>Repository</th>
            <th>Created</th>
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
            </tr>
          ))}
        </tbody>
      </table>
    </div>
  );
}

export default ApplicationsListPage;