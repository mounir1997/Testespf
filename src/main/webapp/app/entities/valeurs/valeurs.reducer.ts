import axios from 'axios';
import { ICrudGetAction, ICrudGetAllAction, ICrudPutAction, ICrudDeleteAction } from 'react-jhipster';

import { cleanEntity } from 'app/shared/util/entity-utils';
import { REQUEST, SUCCESS, FAILURE } from 'app/shared/reducers/action-type.util';
import { SERVER_API_URL } from 'app/config/constants';

import { IValeurs, defaultValue } from 'app/shared/model/valeurs.model';

export const ACTION_TYPES = {
  FETCH_VALEURS_LIST: 'valeurs/FETCH_VALEURS_LIST',
  FETCH_VALEURS: 'valeurs/FETCH_VALEURS',
  CREATE_VALEURS: 'valeurs/CREATE_VALEURS',
  UPDATE_VALEURS: 'valeurs/UPDATE_VALEURS',
  DELETE_VALEURS: 'valeurs/DELETE_VALEURS',
  RESET: 'valeurs/RESET'
};

const initialState = {
  loading: false,
  errorMessage: null,
  entities: [] as ReadonlyArray<IValeurs>,
  entity: defaultValue,
  updating: false,
  updateSuccess: false
};

export type ValeursState = Readonly<typeof initialState>;

// Reducer

export default (state: ValeursState = initialState, action): ValeursState => {
  switch (action.type) {
    case REQUEST(ACTION_TYPES.FETCH_VALEURS_LIST):
    case REQUEST(ACTION_TYPES.FETCH_VALEURS):
      return {
        ...state,
        errorMessage: null,
        updateSuccess: false,
        loading: true
      };
    case REQUEST(ACTION_TYPES.CREATE_VALEURS):
    case REQUEST(ACTION_TYPES.UPDATE_VALEURS):
    case REQUEST(ACTION_TYPES.DELETE_VALEURS):
      return {
        ...state,
        errorMessage: null,
        updateSuccess: false,
        updating: true
      };
    case FAILURE(ACTION_TYPES.FETCH_VALEURS_LIST):
    case FAILURE(ACTION_TYPES.FETCH_VALEURS):
    case FAILURE(ACTION_TYPES.CREATE_VALEURS):
    case FAILURE(ACTION_TYPES.UPDATE_VALEURS):
    case FAILURE(ACTION_TYPES.DELETE_VALEURS):
      return {
        ...state,
        loading: false,
        updating: false,
        updateSuccess: false,
        errorMessage: action.payload
      };
    case SUCCESS(ACTION_TYPES.FETCH_VALEURS_LIST):
      return {
        ...state,
        loading: false,
        entities: action.payload.data
      };
    case SUCCESS(ACTION_TYPES.FETCH_VALEURS):
      return {
        ...state,
        loading: false,
        entity: action.payload.data
      };
    case SUCCESS(ACTION_TYPES.CREATE_VALEURS):
    case SUCCESS(ACTION_TYPES.UPDATE_VALEURS):
      return {
        ...state,
        updating: false,
        updateSuccess: true,
        entity: action.payload.data
      };
    case SUCCESS(ACTION_TYPES.DELETE_VALEURS):
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

const apiUrl = SERVER_API_URL + '/api/valeurs';

// Actions

export const getEntities: ICrudGetAllAction<IValeurs> = (page, size, sort) => ({
  type: ACTION_TYPES.FETCH_VALEURS_LIST,
  payload: axios.get<IValeurs>(`${apiUrl}?cacheBuster=${new Date().getTime()}`)
});

export const getEntity: ICrudGetAction<IValeurs> = id => {
  const requestUrl = `${apiUrl}/${id}`;
  return {
    type: ACTION_TYPES.FETCH_VALEURS,
    payload: axios.get<IValeurs>(requestUrl)
  };
};

export const createEntity: ICrudPutAction<IValeurs> = entity => async dispatch => {
  const result = await dispatch({
    type: ACTION_TYPES.CREATE_VALEURS,
    payload: axios.post(apiUrl, cleanEntity(entity))
  });
  dispatch(getEntities());
  return result;
};

export const updateEntity: ICrudPutAction<IValeurs> = entity => async dispatch => {
  const result = await dispatch({
    type: ACTION_TYPES.UPDATE_VALEURS,
    payload: axios.put(apiUrl, cleanEntity(entity))
  });
  dispatch(getEntities());
  return result;
};

export const deleteEntity: ICrudDeleteAction<IValeurs> = id => async dispatch => {
  const requestUrl = `${apiUrl}/${id}`;
  const result = await dispatch({
    type: ACTION_TYPES.DELETE_VALEURS,
    payload: axios.delete(requestUrl)
  });
  dispatch(getEntities());
  return result;
};

export const reset = () => ({
  type: ACTION_TYPES.RESET
});
