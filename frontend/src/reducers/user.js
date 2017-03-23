import { browserHistory } from 'react-router';

const initialState = (() => {
  let currentUser = JSON.parse(sessionStorage.getItem('user'));
  if (!currentUser) {
    currentUser = JSON.parse(localStorage.getItem('user'));
  }
  return currentUser;
})();

export const user = (state = initialState, action) => {
  switch (action.type) {
    case 'LOGIN_USER_SUCCESS':
    case 'UPDATE_USER_SUCCESS':
      return {
        ...state,
        ...action.payload
      };
    case 'LOGOUT_USER_SUCCESS':
      browserHistory.push('/');
      return null;
    default:
      return state;
  }
};
