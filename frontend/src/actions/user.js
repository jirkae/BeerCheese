// import { browserHistory } from 'react-router';
// import { defaultDispatch, mergeState, ommitState } from './common';
// import { USER } from '../reducers/index';
//
// const defaultDispatchUser = (payload, reducer = ommitState) =>
//   defaultDispatch(USER, payload, reducer);
//
// export const updateUser = payload => defaultDispatch(payload, mergeState);
//
// export const logOutUser = () =>
//   defaultDispatch(null, (state, payload) => {
//     console.log(state);
//     browserHistory.push('/');
//     return null;
//   });
//
// export const loginUser = creds => {
//   const config = {
//     method: 'POST',
//     headers: { 'Content-Type': 'application/x-www-form-urlencoded' },
//     body: `username=${creds.username}&password=${creds.password}`
//   };
// };
