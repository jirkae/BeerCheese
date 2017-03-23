import axios, { CancelToken } from 'axios';

const api = axios.create({
  //TODO pull this into config file
  baseURL: 'https://beer-jansyk13.rhcloud.com/api'
});

export function getCancelTokenSource() {
  return CancelToken.source();
}

export default api;
