import { defaultDispatch } from './common';

export const hideModals = () => defaultDispatch(
  {
    name:null,
    data:null
  },
  (state, payload) => payload
);

export const openModal = (payload) => {
  console.log(payload);
  return defaultDispatch(
    payload,
    (state, payload) => payload
  );
};





