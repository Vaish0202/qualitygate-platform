import { useNavigate } from 'react-router-dom';
import ApplicationForm from '../components/ApplicationForm';
import { createApplication } from '../api/applications';

function CreateApplicationPage() {
  const navigate = useNavigate();

  const handleCreate = async (values) => {
    const created = await createApplication(values);
    navigate(`/applications/${created.id}`);
  };

  return (
    <div className="page">
      <h1>Create Application</h1>
      <ApplicationForm onSubmit={handleCreate} submitLabel="Create Application" />
    </div>
  );
}

export default CreateApplicationPage;