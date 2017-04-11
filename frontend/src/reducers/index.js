import { combineReducers } from 'redux';
import { isFunction, isNullOrUndef } from '../util/util';

export const AUTH = 'AUTH';
export const MODAL = 'MODAL';
export const USER = 'USER';
export const TOKEN = 'TOKEN';
export const USERS = 'USERS';
export const PRODUCTS = 'PRODUCTS';

const openModalInitialState = {
  name: null,
  data: null
};

const authInitialState = {
  isFetching: false,
  isAuthenticated: !isNullOrUndef(localStorage.getItem('x-auth'))
};

const userInitialState = {
  isFetching: false,
  user: null,
  error: null
};

const usersInitialState = {
  isFetching: false,
  users: null,
  error: null
};

const tokenInitialState = {
  refreshTokenPromise: null
};

const productsInitialState = {
  isFetching: false,
  products: null,
  categories: null,
  error: null
};

const reducer = (initialState, type) => {
  return (state = initialState, action = {}) => {
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
  users: reducer(usersInitialState, USERS),
  products: reducer(productsInitialState, PRODUCTS),
  token: reducer(tokenInitialState, TOKEN)
});
