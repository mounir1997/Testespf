import axios from 'axios';
import { ICrudGetAction, ICrudGetAllAction, ICrudPutAction, ICrudDeleteAction } from 'react-jhipster';

import { cleanEntity } from 'app/shared/util/entity-utils';
import { REQUEST, SUCCESS, FAILURE } from 'app/shared/reducers/action-type.util';
import { SERVER_API_URL } from 'app/config/constants';

import { IValeurChamp, defaultValue } from 'app/shared/model/valeur-champ.model';

export const ACTION_TYPES = {
  FETCH_VALEURCHAMP_LIST: 'valeurChamp/FETCH_VALEURCHAMP_LIST',
  FETCH_VALEURCHAMP: 'valeurChamp/FETCH_VALEURCHAMP',
  CREATE_VALEURCHAMP: 'valeurChamp/CREATE_VALEURCHAMP',
  UPDATE_VALEURCHAMP: 'valeurChamp/UPDATE_VALEURCHAMP',
  DELETE_VALEURCHAMP: 'valeurChamp/DELETE_VALEURCHAMP',
  RESET: 'valeurChamp/RESET'
};

const initialState = {
  loading: false,
  errorMessage: null,
  entities: [] as ReadonlyArray<IValeurChamp>,
  entity: defaultValue,
  updating: false,
  updateSuccess: false
};

export type ValeurChampState = Readonly<typeof initialState>;

// Reducer

export default (state: ValeurChampState = initialState, action): ValeurChampState => {
  switch (action.type) {
    case REQUEST(ACTION_TYPES.FETCH_VALEURCHAMP_LIST):
    case REQUEST(ACTION_TYPES.FETCH_VALEURCHAMP):
      return {
        ...state,
        errorMessage: null,
        updateSuccess: false,
        loading: true
      };
    case REQUEST(ACTION_TYPES.CREATE_VALEURCHAMP):
    case REQUEST(ACTION_TYPES.UPDATE_VALEURCHAMP):
    case REQUEST(ACTION_TYPES.DELETE_VALEURCHAMP):
      return {
        ...state,
        errorMessage: null,
        updateSuccess: false,
        updating: true
      };
    case FAILURE(ACTION_TYPES.FETCH_VALEURCHAMP_LIST):
    case FAILURE(ACTION_TYPES.FETCH_VALEURCHAMP):
    case FAILURE(ACTION_TYPES.CREATE_VALEURCHAMP):
    case FAILURE(ACTION_TYPES.UPDATE_VALEURCHAMP):
    case FAILURE(ACTION_TYPES.DELETE_VALEURCHAMP):
      return {
        ...state,
        loading: false,
        updating: false,
        updateSuccess: false,
        errorMessage: action.payload
      };
    case SUCCESS(ACTION_TYPES.FETCH_VALEURCHAMP_LIST):
      return {
        ...state,
        loading: false,
        entities: action.payload.data
      };
    case SUCCESS(ACTION_TYPES.FETCH_VALEURCHAMP):
      return {
        ...state,
        loading: false,
        entity: action.payload.data
      };
    case SUCCESS(ACTION_TYPES.CREATE_VALEURCHAMP):
    case SUCCESS(ACTION_TYPES.UPDATE_VALEURCHAMP):
      return {
        ...state,
        updating: false,
        updateSuccess: true,
        entity: action.payload.data
      };
    case SUCCESS(ACTION_TYPES.DELETE_VALEURCHAMP):
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

const apiUrl = SERVER_API_URL + '/api/valeur-champs';

// Actions

export const getEntities: ICrudGetAllAction<IValeurChamp> = (page, size, sort) => ({
  type: ACTION_TYPES.FETCH_VALEURCHAMP_LIST,
  payload: axios.get<IValeurChamp>(`${apiUrl}?cacheBuster=${new Date().getTime()}`)
});

export const getEntity: ICrudGetAction<IValeurChamp> = id => {
  const requestUrl = `${apiUrl}/${id}`;
  return {
    type: ACTION_TYPES.FETCH_VALEURCHAMP,
    payload: axios.get<IValeurChamp>(requestUrl)
  };
};

export const createEntity: ICrudPutAction<IValeurChamp> = entity => async dispatch => {
  const result = await dispatch({
    type: ACTION_TYPES.CREATE_VALEURCHAMP,
    payload: axios.post(apiUrl, cleanEntity(entity))
  });
  dispatch(getEntities());
  return result;
};

export const updateEntity: ICrudPutAction<IValeurChamp> = entity => async dispatch => {
  const result = await dispatch({
    type: ACTION_TYPES.UPDATE_VALEURCHAMP,
    payload: axios.put(apiUrl, cleanEntity(entity))
  });
  dispatch(getEntities());
  return result;
};

export const deleteEntity: ICrudDeleteAction<IValeurChamp> = id => async dispatch => {
  const requestUrl = `${apiUrl}/${id}`;
  const result = await dispatch({
    type: ACTION_TYPES.DELETE_VALEURCHAMP,
    payload: axios.delete(requestUrl)
  });
  dispatch(getEntities());
  return result;
};

export const reset = () => ({
  type: ACTION_TYPES.RESET
});
