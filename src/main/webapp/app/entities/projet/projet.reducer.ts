import axios from 'axios';
import { ICrudGetAction, ICrudGetAllAction, ICrudPutAction, ICrudDeleteAction } from 'react-jhipster';

import { cleanEntity } from 'app/shared/util/entity-utils';
import { REQUEST, SUCCESS, FAILURE } from 'app/shared/reducers/action-type.util';
import { SERVER_API_URL } from 'app/config/constants';

import { IProjet, defaultValue } from 'app/shared/model/projet.model';

export const ACTION_TYPES = {
  FETCH_PROJET_LIST: 'projet/FETCH_PROJET_LIST',
  FETCH_PROJET: 'projet/FETCH_PROJET',
  CREATE_PROJET: 'projet/CREATE_PROJET',
  UPDATE_PROJET: 'projet/UPDATE_PROJET',
  DELETE_PROJET: 'projet/DELETE_PROJET',
  RESET: 'projet/RESET'
};

const initialState = {
  loading: false,
  errorMessage: null,
  entities: [] as ReadonlyArray<IProjet>,
  entity: defaultValue,
  updating: false,
  updateSuccess: false
};

export type ProjetState = Readonly<typeof initialState>;

// Reducer

export default (state: ProjetState = initialState, action): ProjetState => {
  switch (action.type) {
    case REQUEST(ACTION_TYPES.FETCH_PROJET_LIST):
    case REQUEST(ACTION_TYPES.FETCH_PROJET):
      return {
        ...state,
        errorMessage: null,
        updateSuccess: false,
        loading: true
      };
    case REQUEST(ACTION_TYPES.CREATE_PROJET):
    case REQUEST(ACTION_TYPES.UPDATE_PROJET):
    case REQUEST(ACTION_TYPES.DELETE_PROJET):
      return {
        ...state,
        errorMessage: null,
        updateSuccess: false,
        updating: true
      };
    case FAILURE(ACTION_TYPES.FETCH_PROJET_LIST):
    case FAILURE(ACTION_TYPES.FETCH_PROJET):
    case FAILURE(ACTION_TYPES.CREATE_PROJET):
    case FAILURE(ACTION_TYPES.UPDATE_PROJET):
    case FAILURE(ACTION_TYPES.DELETE_PROJET):
      return {
        ...state,
        loading: false,
        updating: false,
        updateSuccess: false,
        errorMessage: action.payload
      };
    case SUCCESS(ACTION_TYPES.FETCH_PROJET_LIST):
      return {
        ...state,
        loading: false,
        entities: action.payload.data
      };
    case SUCCESS(ACTION_TYPES.FETCH_PROJET):
      return {
        ...state,
        loading: false,
        entity: action.payload.data
      };
    case SUCCESS(ACTION_TYPES.CREATE_PROJET):
    case SUCCESS(ACTION_TYPES.UPDATE_PROJET):
      return {
        ...state,
        updating: false,
        updateSuccess: true,
        entity: action.payload.data
      };
    case SUCCESS(ACTION_TYPES.DELETE_PROJET):
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

const apiUrl = SERVER_API_URL + '/api/projets';

// Actions

export const getEntities: ICrudGetAllAction<IProjet> = (page, size, sort) => ({
  type: ACTION_TYPES.FETCH_PROJET_LIST,
  payload: axios.get<IProjet>(`${apiUrl}?cacheBuster=${new Date().getTime()}`)
});

export const getEntity: ICrudGetAction<IProjet> = id => {
  const requestUrl = `${apiUrl}/${id}`;
  return {
    type: ACTION_TYPES.FETCH_PROJET,
    payload: axios.get<IProjet>(requestUrl)
  };
};

export const createEntity: ICrudPutAction<IProjet> = entity => async dispatch => {
  const result = await dispatch({
    type: ACTION_TYPES.CREATE_PROJET,
    payload: axios.post(apiUrl, cleanEntity(entity))
  });
  dispatch(getEntities());
  return result;
};

export const updateEntity: ICrudPutAction<IProjet> = entity => async dispatch => {
  const result = await dispatch({
    type: ACTION_TYPES.UPDATE_PROJET,
    payload: axios.put(apiUrl, cleanEntity(entity))
  });
  dispatch(getEntities());
  return result;
};

export const deleteEntity: ICrudDeleteAction<IProjet> = id => async dispatch => {
  const requestUrl = `${apiUrl}/${id}`;
  const result = await dispatch({
    type: ACTION_TYPES.DELETE_PROJET,
    payload: axios.delete(requestUrl)
  });
  dispatch(getEntities());
  return result;
};

export const reset = () => ({
  type: ACTION_TYPES.RESET
});
