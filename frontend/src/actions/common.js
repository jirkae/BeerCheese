// To be used as a reducer in action creators
import { CALL_API } from '../middleware/api';
// This will merge current state with changes in payload
export const mergeState = (state, payload) => ({
  ...state,
  ...payload
});

// To be used as a reducer in action creators
// This will replace current state with payload
export const ommitState = (state, payload) => ({ ...payload });

export const defaultDispatch = (type, payload, reducer = mergeState) =>
  dispatch =>
    dispatch({
      type,
      payload,
      reducer: reducer
    });

/**
 * Dispatch api call action
 * @param endpoint <string>
 * @param config <object> axios config object (check ../middleware/api)
 * @param actions <array> array of three action creators which will be called based on api result
 *                        (check ../middleware/api)
 * @param authenticated <boolean> if true, api middleware will add auth token to request header
 */
export const dispatchToAPI = ({ endpoint, config, actions, authenticated }) =>
  dispatch =>
    dispatch({
      [CALL_API]: {
        endpoint,
        config,
        actions,
        authenticated
      }
    });
