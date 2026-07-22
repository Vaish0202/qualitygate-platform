import apiClient from './client';

export const getApplications = async () => {
  const response = await apiClient.get('/api/applications');
  return response.data;
};

export const getApplicationById = async (id) => {
  const response = await apiClient.get(`/api/applications/${id}`);
  return response.data;
};

export const createApplication = async (payload) => {
  const response = await apiClient.post('/api/applications', payload);
  return response.data;
};

export const updateApplication = async (id, payload) => {
  const response = await apiClient.put(`/api/applications/${id}`, payload);
  return response.data;
};

export const deleteApplication = async (id) => {
  await apiClient.delete(`/api/applications/${id}`);
};

export const getEnvironments = async (applicationId) => {
  const response = await apiClient.get(`/api/applications/${applicationId}/environments`);
  return response.data;
};