import axios from 'axios';
import { ICrudGetAction, ICrudGetAllAction, ICrudPutAction, ICrudDeleteAction } from 'react-jhipster';

import { cleanEntity } from 'app/shared/util/entity-utils';
import { REQUEST, SUCCESS, FAILURE } from 'app/shared/reducers/action-type.util';
import { SERVER_API_URL } from 'app/config/constants';

import { IChamps, defaultValue } from 'app/shared/model/champs.model';

export const ACTION_TYPES = {
  FETCH_CHAMPS_LIST: 'champs/FETCH_CHAMPS_LIST',
  FETCH_CHAMPS: 'champs/FETCH_CHAMPS',
  CREATE_CHAMPS: 'champs/CREATE_CHAMPS',
  UPDATE_CHAMPS: 'champs/UPDATE_CHAMPS',
  DELETE_CHAMPS: 'champs/DELETE_CHAMPS',
  RESET: 'champs/RESET'
};

const initialState = {
  loading: false,
  errorMessage: null,
  entities: [] as ReadonlyArray<IChamps>,
  entity: defaultValue,
  updating: false,
  updateSuccess: false
};

export type ChampsState = Readonly<typeof initialState>;

// Reducer

export default (state: ChampsState = initialState, action): ChampsState => {
  switch (action.type) {
    case REQUEST(ACTION_TYPES.FETCH_CHAMPS_LIST):
    case REQUEST(ACTION_TYPES.FETCH_CHAMPS):
      return {
        ...state,
        errorMessage: null,
        updateSuccess: false,
        loading: true
      };
    case REQUEST(ACTION_TYPES.CREATE_CHAMPS):
    case REQUEST(ACTION_TYPES.UPDATE_CHAMPS):
    case REQUEST(ACTION_TYPES.DELETE_CHAMPS):
      return {
        ...state,
        errorMessage: null,
        updateSuccess: false,
        updating: true
      };
    case FAILURE(ACTION_TYPES.FETCH_CHAMPS_LIST):
    case FAILURE(ACTION_TYPES.FETCH_CHAMPS):
    case FAILURE(ACTION_TYPES.CREATE_CHAMPS):
    case FAILURE(ACTION_TYPES.UPDATE_CHAMPS):
    case FAILURE(ACTION_TYPES.DELETE_CHAMPS):
      return {
        ...state,
        loading: false,
        updating: false,
        updateSuccess: false,
        errorMessage: action.payload
      };
    case SUCCESS(ACTION_TYPES.FETCH_CHAMPS_LIST):
      return {
        ...state,
        loading: false,
        entities: action.payload.data
      };
    case SUCCESS(ACTION_TYPES.FETCH_CHAMPS):
      return {
        ...state,
        loading: false,
        entity: action.payload.data
      };
    case SUCCESS(ACTION_TYPES.CREATE_CHAMPS):
    case SUCCESS(ACTION_TYPES.UPDATE_CHAMPS):
      return {
        ...state,
        updating: false,
        updateSuccess: true,
        entity: action.payload.data
      };
    case SUCCESS(ACTION_TYPES.DELETE_CHAMPS):
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

const apiUrl = SERVER_API_URL + '/api/champs';

// Actions

export const getEntities: ICrudGetAllAction<IChamps> = (page, size, sort) => ({
  type: ACTION_TYPES.FETCH_CHAMPS_LIST,
  payload: axios.get<IChamps>(`${apiUrl}?cacheBuster=${new Date().getTime()}`)
});

export const getEntity: ICrudGetAction<IChamps> = id => {
  const requestUrl = `${apiUrl}/${id}`;
  return {
    type: ACTION_TYPES.FETCH_CHAMPS,
    payload: axios.get<IChamps>(requestUrl)
  };
};

export const createEntity: ICrudPutAction<IChamps> = entity => async dispatch => {
  const result = await dispatch({
    type: ACTION_TYPES.CREATE_CHAMPS,
    payload: axios.post(apiUrl, cleanEntity(entity))
  });
  dispatch(getEntities());
  return result;
};

export const updateEntity: ICrudPutAction<IChamps> = entity => async dispatch => {
  const result = await dispatch({
    type: ACTION_TYPES.UPDATE_CHAMPS,
    payload: axios.put(apiUrl, cleanEntity(entity))
  });
  dispatch(getEntities());
  return result;
};

export const deleteEntity: ICrudDeleteAction<IChamps> = id => async dispatch => {
  const requestUrl = `${apiUrl}/${id}`;
  const result = await dispatch({
    type: ACTION_TYPES.DELETE_CHAMPS,
    payload: axios.delete(requestUrl)
  });
  dispatch(getEntities());
  return result;
};

export const reset = () => ({
  type: ACTION_TYPES.RESET
});
