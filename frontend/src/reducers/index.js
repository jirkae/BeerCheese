import { combineReducers } from 'redux';

const openModalInitialState = {
  name: null,
  data: null
};

const userInitialState = (() => {
  let currentUser = JSON.parse(sessionStorage.getItem('user'));
  if (!currentUser) {
    currentUser = JSON.parse(localStorage.getItem('user'));
  }
  return currentUser;
})();

const reducer = initialState => {
  return (state = initialState, action) => {
    if (action.reducer) {
      return action.reducer(state, action.payload);
    } else {
      return state;
    }
  };
};

export const rootReducer = combineReducers({
  user: reducer(userInitialState),
  openModal: reducer(openModalInitialState)
});
