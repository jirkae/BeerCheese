export function isNullOrUndef(obj) {
  return isUndefined(obj) || isNull(obj);
}

export function isFunction(obj) {
  return typeof obj === 'function';
}

export function isNull(obj) {
  return obj === null;
}

export const isArray = Array.isArray;

export function isUndefined(obj) {
  return obj === undefined;
}

export function isString(obj) {
  return typeof obj === 'string';
}
