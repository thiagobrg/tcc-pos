export const getToken = (): string | null => {
  return sessionStorage.getItem('token');
};
