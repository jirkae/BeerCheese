import { browserHistory } from 'react-router';
import { defaultDispatch, mergeState } from './common';

export const loginUser = (payload) => defaultDispatch(
  payload,
  mergeState
);

export const updateUser = (payload) => defaultDispatch(
  payload,
  mergeState
);

export const logOutUser = () => defaultDispatch(
  null,
  (state, payload) => {
    browserHistory.push('/');
    return null;
  }
);