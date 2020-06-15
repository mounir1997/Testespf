import axios from 'axios';
import { ICrudGetAction, ICrudGetAllAction, ICrudPutAction, ICrudDeleteAction } from 'react-jhipster';

import { cleanEntity } from 'app/shared/util/entity-utils';
import { REQUEST, SUCCESS, FAILURE } from 'app/shared/reducers/action-type.util';
import { SERVER_API_URL } from 'app/config/constants';

import { IESPF, defaultValue } from 'app/shared/model/espf.model';

export const ACTION_TYPES = {
  FETCH_ESPF_LIST: 'eSPF/FETCH_ESPF_LIST',
  FETCH_ESPF: 'eSPF/FETCH_ESPF',
  CREATE_ESPF: 'eSPF/CREATE_ESPF',
  UPDATE_ESPF: 'eSPF/UPDATE_ESPF',
  DELETE_ESPF: 'eSPF/DELETE_ESPF',
  RESET: 'eSPF/RESET'
};

const initialState = {
  loading: false,
  errorMessage: null,
  entities: [] as ReadonlyArray<IESPF>,
  entity: defaultValue,
  updating: false,
  updateSuccess: false
};

export type ESPFState = Readonly<typeof initialState>;

// Reducer

export default (state: ESPFState = initialState, action): ESPFState => {
  switch (action.type) {
    case REQUEST(ACTION_TYPES.FETCH_ESPF_LIST):
    case REQUEST(ACTION_TYPES.FETCH_ESPF):
      return {
        ...state,
        errorMessage: null,
        updateSuccess: false,
        loading: true
      };
    case REQUEST(ACTION_TYPES.CREATE_ESPF):
    case REQUEST(ACTION_TYPES.UPDATE_ESPF):
    case REQUEST(ACTION_TYPES.DELETE_ESPF):
      return {
        ...state,
        errorMessage: null,
        updateSuccess: false,
        updating: true
      };
    case FAILURE(ACTION_TYPES.FETCH_ESPF_LIST):
    case FAILURE(ACTION_TYPES.FETCH_ESPF):
    case FAILURE(ACTION_TYPES.CREATE_ESPF):
    case FAILURE(ACTION_TYPES.UPDATE_ESPF):
    case FAILURE(ACTION_TYPES.DELETE_ESPF):
      return {
        ...state,
        loading: false,
        updating: false,
        updateSuccess: false,
        errorMessage: action.payload
      };
    case SUCCESS(ACTION_TYPES.FETCH_ESPF_LIST):
      return {
        ...state,
        loading: false,
        entities: action.payload.data
      };
    case SUCCESS(ACTION_TYPES.FETCH_ESPF):
      return {
        ...state,
        loading: false,
        entity: action.payload.data
      };
    case SUCCESS(ACTION_TYPES.CREATE_ESPF):
    case SUCCESS(ACTION_TYPES.UPDATE_ESPF):
      return {
        ...state,
        updating: false,
        updateSuccess: true,
        entity: action.payload.data
      };
    case SUCCESS(ACTION_TYPES.DELETE_ESPF):
      return {
        ...state,
        updating: false,
        updateSuccess: true,
        entity: {}
      };
    case ACTION_TYPES.RESET:
      return {
        ...initialState
      };
    default:
      return state;
  }
};

const apiUrl = SERVER_API_URL + '/api/espfs';

// Actions

export const getEntities: ICrudGetAllAction<IESPF> = (page, size, sort) => ({
  type: ACTION_TYPES.FETCH_ESPF_LIST,
  payload: axios.get<IESPF>(`${apiUrl}?cacheBuster=${new Date().getTime()}`)
});

export const getEntity: ICrudGetAction<IESPF> = id => {
  const requestUrl = `${apiUrl}/${id}`;
  return {
    type: ACTION_TYPES.FETCH_ESPF,
    payload: axios.get<IESPF>(requestUrl)
  };
};

export const createEntity: ICrudPutAction<IESPF> = entity => async dispatch => {
  const result = await dispatch({
    type: ACTION_TYPES.CREATE_ESPF,
    payload: axios.post(apiUrl, cleanEntity(entity))
  });
  dispatch(getEntities());
  return result;
};

export const updateEntity: ICrudPutAction<IESPF> = entity => async dispatch => {
  const result = await dispatch({
    type: ACTION_TYPES.UPDATE_ESPF,
    payload: axios.put(apiUrl, cleanEntity(entity))
  });
  dispatch(getEntities());
  return result;
};

export const deleteEntity: ICrudDeleteAction<IESPF> = id => async dispatch => {
  const requestUrl = `${apiUrl}/${id}`;
  const result = await dispatch({
    type: ACTION_TYPES.DELETE_ESPF,
    payload: axios.delete(requestUrl)
  });
  dispatch(getEntities());
  return result;
};

export const reset = () => ({
  type: ACTION_TYPES.RESET
});
