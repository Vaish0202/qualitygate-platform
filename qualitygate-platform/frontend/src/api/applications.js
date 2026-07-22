import apiClient from './client';

export const getApplications = async () => {
  const response = await apiClient.get('/api/applications');
  return response.data;
};

export const getApplicationById = async (id) => {
  const response = await apiClient.get(`/api/applications/${id}`);
  return response.data;
};