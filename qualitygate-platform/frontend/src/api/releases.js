import apiClient from './client';

export const getReleases = async () => {
  const response = await apiClient.get('/api/releases');
  return response.data;
};

export const getReleasesForApplication = async (applicationId) => {
  const releases = await getReleases();
  return releases.filter((release) => release.applicationId === applicationId);
};