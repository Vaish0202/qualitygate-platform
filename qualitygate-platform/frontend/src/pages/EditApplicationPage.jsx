import { useEffect, useState } from 'react';
import { useNavigate, useParams } from 'react-router-dom';
import ApplicationForm from '../components/ApplicationForm';
import { getApplicationById, updateApplication } from '../api/applications';

function EditApplicationPage() {
  const { id } = useParams();
  const navigate = useNavigate();
  const [application, setApplication] = useState(null);
  const [loading, setLoading] = useState(true);
  const [error, setError] = useState(null);

  useEffect(() => {
    async function fetchApplication() {
      try {
        setLoading(true);
        const data = await getApplicationById(id);
        setApplication(data);
      } catch (err) {
        setError('Unable to load application.');
      } finally {
        setLoading(false);
      }
    }
    fetchApplication();
  }, [id]);

  const handleUpdate = async (values) => {
    await updateApplication(id, values);
    navigate(`/applications/${id}`);
  };

  if (loading) return <p className="status-message">Loading...</p>;
  if (error) return <p className="status-message status-error">{error}</p>;

  return (
    <div className="page">
      <h1>Edit Application</h1>
      <ApplicationForm
        initialValues={application}
        onSubmit={handleUpdate}
        submitLabel="Save Changes"
      />
    </div>
  );
}

export default EditApplicationPage;