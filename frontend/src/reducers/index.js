import { combineReducers } from 'redux';
import { isFunction, isNullOrUndef } from '../util/util';

export const AUTH = 'AUTH';
export const MODAL = 'MODAL';
export const USER = 'USER';
export const TOKEN = 'TOKEN';

const openModalInitialState = {
  name: null,
  data: null
};

const authInitialState = (() => {
  return {
    isFetching: false,
    isAuthenticated: !isNullOrUndef(localStorage.getItem('x-auth'))
  };
})();

const userInitialState = {};

const tokenInitialState = {
  refreshTokenPromise: null
};

const reducer = (initialState, type) => {
  return (state = initialState, action = {}) => {
    // console.log(action);
    // console.log(state);
    if (action.type === type && isFunction(action.reducer)) {
      return action.reducer(state, action.payload);
    } else {
      return state;
    }
  };
};

export const rootReducer = combineReducers({
  auth: reducer(authInitialState, AUTH),
  openModal: reducer(openModalInitialState, MODAL),
  user: reducer(userInitialState, USER),
  token: reducer(tokenInitialState, TOKEN)
});
