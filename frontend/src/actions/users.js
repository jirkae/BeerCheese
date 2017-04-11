import { defaultDispatch, dispatchToAPI } from './common';
import { USERS } from '../reducers/index';

const defaultDispatchUsers = (payload, reducer) =>
  defaultDispatch(USERS, payload, reducer);

/* Action creators */

const usersRequested = () =>
  defaultDispatchUsers({
    isFetching: true,
    error: null
  });

const usersReceived = ({ response }) => {
  const users = response.data.users;
  return defaultDispatchUsers({
    isFetching: false,
    users
  });
};

const usersError = error =>
  defaultDispatchUsers({
    isFetching: false,
    error
  });

/* Thunks */

// access users endpoint
export const usersApi = config => {
  config = {
    method: 'GET',
    ...config
  };
  return dispatchToAPI({
    endpoint: '/users',
    config,
    authenticated: true,
    actions: [usersRequested, usersReceived, usersError]
  });
};
