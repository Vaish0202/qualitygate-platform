import { useEffect, useState } from 'react';
import { Link, useParams } from 'react-router-dom';
import { getApplicationById, getEnvironments } from '../api/applications';
import { getReleasesForApplication } from '../api/releases';
import StatusBadge from '../components/StatusBadge';

function ApplicationDetailsPage() {
  const { id } = useParams();
  const [application, setApplication] = useState(null);
  const [environments, setEnvironments] = useState([]);
  const [releases, setReleases] = useState([]);
  const [loading, setLoading] = useState(true);
  const [error, setError] = useState(null);

  useEffect(() => {
    let isMounted = true;

    async function fetchAll() {
      try {
        setLoading(true);
        setError(null);
        const [appData, envData, releaseData] = await Promise.all([
          getApplicationById(id),
          getEnvironments(id),
          getReleasesForApplication(Number(id)),
        ]);
        if (isMounted) {
          setApplication(appData);
          setEnvironments(envData);
          setReleases(releaseData);
        }
      } catch (err) {
        if (isMounted) {
          setError('Unable to load application details.');
        }
      } finally {
        if (isMounted) {
          setLoading(false);
        }
      }
    }

    fetchAll();
    return () => {
      isMounted = false;
    };
  }, [id]);

  if (loading) return <p className="status-message">Loading application details...</p>;
  if (error) return <p className="status-message status-error">{error}</p>;
  if (!application) return <p className="status-message">Application not found.</p>;

  return (
    <div className="page">
      <div className="page-header">
        <h1>{application.name}</h1>
        <Link to={`/applications/${id}/edit`} className="btn btn-secondary">
          Edit
        </Link>
      </div>

      <p className="app-description">{application.description}</p>
      <p>
        <strong>Repository:</strong> {application.repositoryUrl}
      </p>

      <h2>Environments</h2>
      {environments.length === 0 ? (
        <p className="status-message">No environments provisioned.</p>
      ) : (
        <div className="environment-cards">
          {environments.map((env) => (
            <div key={env.id} className="environment-card">
              <h3>{env.name}</h3>
              <p>{env.url || 'No URL configured'}</p>
            </div>
          ))}
        </div>
      )}

      <h2>Release History</h2>
      {releases.length === 0 ? (
        <p className="status-message">No releases yet.</p>
      ) : (
        <table>
          <thead>
            <tr>
              <th>Version</th>
              <th>Environment</th>
              <th>Status</th>
              <th>Approved By</th>
              <th>Deployed At</th>
            </tr>
          </thead>
          <tbody>
            {releases.map((release) => (
              <tr key={release.id}>
                <td>{release.version}</td>
                <td>{release.environmentName}</td>
                <td>
                  <StatusBadge status={release.status} />
                </td>
                <td>{release.approvedBy || '—'}</td>
                <td>
                  {release.deployedAt
                    ? new Date(release.deployedAt).toLocaleString()
                    : '—'}
                </td>
              </tr>
            ))}
          </tbody>
        </table>
      )}
    </div>
  );
}

export default ApplicationDetailsPage;