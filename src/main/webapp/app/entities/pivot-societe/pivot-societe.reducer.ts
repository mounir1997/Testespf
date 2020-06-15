import axios from 'axios';
import { ICrudGetAction, ICrudGetAllAction, ICrudPutAction, ICrudDeleteAction } from 'react-jhipster';

import { cleanEntity } from 'app/shared/util/entity-utils';
import { REQUEST, SUCCESS, FAILURE } from 'app/shared/reducers/action-type.util';
import { SERVER_API_URL } from 'app/config/constants';

import { IPivotSociete, defaultValue } from 'app/shared/model/pivot-societe.model';

export const ACTION_TYPES = {
  FETCH_PIVOTSOCIETE_LIST: 'pivotSociete/FETCH_PIVOTSOCIETE_LIST',
  FETCH_PIVOTSOCIETE: 'pivotSociete/FETCH_PIVOTSOCIETE',
  CREATE_PIVOTSOCIETE: 'pivotSociete/CREATE_PIVOTSOCIETE',
  UPDATE_PIVOTSOCIETE: 'pivotSociete/UPDATE_PIVOTSOCIETE',
  DELETE_PIVOTSOCIETE: 'pivotSociete/DELETE_PIVOTSOCIETE',
  RESET: 'pivotSociete/RESET'
};

const initialState = {
  loading: false,
  errorMessage: null,
  entities: [] as ReadonlyArray<IPivotSociete>,
  entity: defaultValue,
  updating: false,
  updateSuccess: false
};

export type PivotSocieteState = Readonly<typeof initialState>;

// Reducer

export default (state: PivotSocieteState = initialState, action): PivotSocieteState => {
  switch (action.type) {
    case REQUEST(ACTION_TYPES.FETCH_PIVOTSOCIETE_LIST):
    case REQUEST(ACTION_TYPES.FETCH_PIVOTSOCIETE):
      return {
        ...state,
        errorMessage: null,
        updateSuccess: false,
        loading: true
      };
    case REQUEST(ACTION_TYPES.CREATE_PIVOTSOCIETE):
    case REQUEST(ACTION_TYPES.UPDATE_PIVOTSOCIETE):
    case REQUEST(ACTION_TYPES.DELETE_PIVOTSOCIETE):
      return {
        ...state,
        errorMessage: null,
        updateSuccess: false,
        updating: true
      };
    case FAILURE(ACTION_TYPES.FETCH_PIVOTSOCIETE_LIST):
    case FAILURE(ACTION_TYPES.FETCH_PIVOTSOCIETE):
    case FAILURE(ACTION_TYPES.CREATE_PIVOTSOCIETE):
    case FAILURE(ACTION_TYPES.UPDATE_PIVOTSOCIETE):
    case FAILURE(ACTION_TYPES.DELETE_PIVOTSOCIETE):
      return {
        ...state,
        loading: false,
        updating: false,
        updateSuccess: false,
        errorMessage: action.payload
      };
    case SUCCESS(ACTION_TYPES.FETCH_PIVOTSOCIETE_LIST):
      return {
        ...state,
        loading: false,
        entities: action.payload.data
      };
    case SUCCESS(ACTION_TYPES.FETCH_PIVOTSOCIETE):
      return {
        ...state,
        loading: false,
        entity: action.payload.data
      };
    case SUCCESS(ACTION_TYPES.CREATE_PIVOTSOCIETE):
    case SUCCESS(ACTION_TYPES.UPDATE_PIVOTSOCIETE):
      return {
        ...state,
        updating: false,
        updateSuccess: true,
        entity: action.payload.data
      };
    case SUCCESS(ACTION_TYPES.DELETE_PIVOTSOCIETE):
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

const apiUrl = SERVER_API_URL + '/api/pivot-societes';

// Actions

export const getEntities: ICrudGetAllAction<IPivotSociete> = (page, size, sort) => ({
  type: ACTION_TYPES.FETCH_PIVOTSOCIETE_LIST,
  payload: axios.get<IPivotSociete>(`${apiUrl}?cacheBuster=${new Date().getTime()}`)
});

export const getEntity: ICrudGetAction<IPivotSociete> = id => {
  const requestUrl = `${apiUrl}/${id}`;
  return {
    type: ACTION_TYPES.FETCH_PIVOTSOCIETE,
    payload: axios.get<IPivotSociete>(requestUrl)
  };
};

export const createEntity: ICrudPutAction<IPivotSociete> = entity => async dispatch => {
  const result = await dispatch({
    type: ACTION_TYPES.CREATE_PIVOTSOCIETE,
    payload: axios.post(apiUrl, cleanEntity(entity))
  });
  dispatch(getEntities());
  return result;
};

export const updateEntity: ICrudPutAction<IPivotSociete> = entity => async dispatch => {
  const result = await dispatch({
    type: ACTION_TYPES.UPDATE_PIVOTSOCIETE,
    payload: axios.put(apiUrl, cleanEntity(entity))
  });
  dispatch(getEntities());
  return result;
};

export const deleteEntity: ICrudDeleteAction<IPivotSociete> = id => async dispatch => {
  const requestUrl = `${apiUrl}/${id}`;
  const result = await dispatch({
    type: ACTION_TYPES.DELETE_PIVOTSOCIETE,
    payload: axios.delete(requestUrl)
  });
  dispatch(getEntities());
  return result;
};

export const reset = () => ({
  type: ACTION_TYPES.RESET
});
