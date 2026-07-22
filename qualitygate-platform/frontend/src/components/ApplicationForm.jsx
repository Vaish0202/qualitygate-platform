import { useState } from 'react';

function ApplicationForm({ initialValues, onSubmit, submitLabel }) {
  const [name, setName] = useState(initialValues?.name || '');
  const [description, setDescription] = useState(initialValues?.description || '');
  const [repositoryUrl, setRepositoryUrl] = useState(initialValues?.repositoryUrl || '');
  const [errors, setErrors] = useState({});
  const [submitting, setSubmitting] = useState(false);
  const [submitError, setSubmitError] = useState(null);

  const validate = () => {
    const newErrors = {};
    if (!name.trim()) {
      newErrors.name = 'Application name is required';
    }
    if (!repositoryUrl.trim()) {
      newErrors.repositoryUrl = 'Repository URL is required';
    }
    setErrors(newErrors);
    return Object.keys(newErrors).length === 0;
  };

  const handleSubmit = async (event) => {
    event.preventDefault();
    setSubmitError(null);

    if (!validate()) {
      return;
    }

    try {
      setSubmitting(true);
      await onSubmit({ name, description, repositoryUrl });
    } catch (err) {
      const backendMessage = err?.response?.data?.message;
      setSubmitError(backendMessage || 'Something went wrong. Please try again.');
    } finally {
      setSubmitting(false);
    }
  };

  return (
    <form className="application-form" onSubmit={handleSubmit}>
      <div className="form-field">
        <label htmlFor="name">Name</label>
        <input
          id="name"
          type="text"
          value={name}
          onChange={(e) => setName(e.target.value)}
        />
        {errors.name && <span className="field-error">{errors.name}</span>}
      </div>

      <div className="form-field">
        <label htmlFor="description">Description</label>
        <textarea
          id="description"
          value={description}
          onChange={(e) => setDescription(e.target.value)}
          rows={3}
        />
      </div>

      <div className="form-field">
        <label htmlFor="repositoryUrl">Repository URL</label>
        <input
          id="repositoryUrl"
          type="text"
          value={repositoryUrl}
          onChange={(e) => setRepositoryUrl(e.target.value)}
        />
        {errors.repositoryUrl && <span className="field-error">{errors.repositoryUrl}</span>}
      </div>

      {submitError && <p className="status-message status-error">{submitError}</p>}

      <button type="submit" className="btn btn-primary" disabled={submitting}>
        {submitting ? 'Saving...' : submitLabel}
      </button>
    </form>
  );
}

export default ApplicationForm;