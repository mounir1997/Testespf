import axios from 'axios';
import { ICrudGetAction, ICrudGetAllAction, ICrudPutAction, ICrudDeleteAction } from 'react-jhipster';

import { cleanEntity } from 'app/shared/util/entity-utils';
import { REQUEST, SUCCESS, FAILURE } from 'app/shared/reducers/action-type.util';
import { SERVER_API_URL } from 'app/config/constants';

import { IAttribut, defaultValue } from 'app/shared/model/attribut.model';

export const ACTION_TYPES = {
  FETCH_ATTRIBUT_LIST: 'attribut/FETCH_ATTRIBUT_LIST',
  FETCH_ATTRIBUT: 'attribut/FETCH_ATTRIBUT',
  CREATE_ATTRIBUT: 'attribut/CREATE_ATTRIBUT',
  UPDATE_ATTRIBUT: 'attribut/UPDATE_ATTRIBUT',
  DELETE_ATTRIBUT: 'attribut/DELETE_ATTRIBUT',
  RESET: 'attribut/RESET'
};

const initialState = {
  loading: false,
  errorMessage: null,
  entities: [] as ReadonlyArray<IAttribut>,
  entity: defaultValue,
  updating: false,
  updateSuccess: false
};

export type AttributState = Readonly<typeof initialState>;

// Reducer

export default (state: AttributState = initialState, action): AttributState => {
  switch (action.type) {
    case REQUEST(ACTION_TYPES.FETCH_ATTRIBUT_LIST):
    case REQUEST(ACTION_TYPES.FETCH_ATTRIBUT):
      return {
        ...state,
        errorMessage: null,
        updateSuccess: false,
        loading: true
      };
    case REQUEST(ACTION_TYPES.CREATE_ATTRIBUT):
    case REQUEST(ACTION_TYPES.UPDATE_ATTRIBUT):
    case REQUEST(ACTION_TYPES.DELETE_ATTRIBUT):
      return {
        ...state,
        errorMessage: null,
        updateSuccess: false,
        updating: true
      };
    case FAILURE(ACTION_TYPES.FETCH_ATTRIBUT_LIST):
    case FAILURE(ACTION_TYPES.FETCH_ATTRIBUT):
    case FAILURE(ACTION_TYPES.CREATE_ATTRIBUT):
    case FAILURE(ACTION_TYPES.UPDATE_ATTRIBUT):
    case FAILURE(ACTION_TYPES.DELETE_ATTRIBUT):
      return {
        ...state,
        loading: false,
        updating: false,
        updateSuccess: false,
        errorMessage: action.payload
      };
    case SUCCESS(ACTION_TYPES.FETCH_ATTRIBUT_LIST):
      return {
        ...state,
        loading: false,
        entities: action.payload.data
      };
    case SUCCESS(ACTION_TYPES.FETCH_ATTRIBUT):
      return {
        ...state,
        loading: false,
        entity: action.payload.data
      };
    case SUCCESS(ACTION_TYPES.CREATE_ATTRIBUT):
    case SUCCESS(ACTION_TYPES.UPDATE_ATTRIBUT):
      return {
        ...state,
        updating: false,
        updateSuccess: true,
        entity: action.payload.data
      };
    case SUCCESS(ACTION_TYPES.DELETE_ATTRIBUT):
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

const apiUrl = SERVER_API_URL + '/api/attributs';

// Actions

export const getEntities: ICrudGetAllAction<IAttribut> = (page, size, sort) => ({
  type: ACTION_TYPES.FETCH_ATTRIBUT_LIST,
  payload: axios.get<IAttribut>(`${apiUrl}?cacheBuster=${new Date().getTime()}`)
});

export const getEntity: ICrudGetAction<IAttribut> = id => {
  const requestUrl = `${apiUrl}/${id}`;
  return {
    type: ACTION_TYPES.FETCH_ATTRIBUT,
    payload: axios.get<IAttribut>(requestUrl)
  };
};

export const createEntity: ICrudPutAction<IAttribut> = entity => async dispatch => {
  const result = await dispatch({
    type: ACTION_TYPES.CREATE_ATTRIBUT,
    payload: axios.post(apiUrl, cleanEntity(entity))
  });
  dispatch(getEntities());
  return result;
};

export const updateEntity: ICrudPutAction<IAttribut> = entity => async dispatch => {
  const result = await dispatch({
    type: ACTION_TYPES.UPDATE_ATTRIBUT,
    payload: axios.put(apiUrl, cleanEntity(entity))
  });
  dispatch(getEntities());
  return result;
};

export const deleteEntity: ICrudDeleteAction<IAttribut> = id => async dispatch => {
  const requestUrl = `${apiUrl}/${id}`;
  const result = await dispatch({
    type: ACTION_TYPES.DELETE_ATTRIBUT,
    payload: axios.delete(requestUrl)
  });
  dispatch(getEntities());
  return result;
};

export const reset = () => ({
  type: ACTION_TYPES.RESET
});
