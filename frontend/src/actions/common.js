export const defaultDispatch = (payload, reducer) => (dispatch) => dispatch({
  type: '',
  payload,
  reducer: reducer
});

export const mergeState = (state, payload) => ({
  ...state,
  ...payload
});