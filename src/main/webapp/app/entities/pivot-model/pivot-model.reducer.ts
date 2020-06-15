import axios from 'axios';
import { ICrudGetAction, ICrudGetAllAction, ICrudPutAction, ICrudDeleteAction } from 'react-jhipster';

import { cleanEntity } from 'app/shared/util/entity-utils';
import { REQUEST, SUCCESS, FAILURE } from 'app/shared/reducers/action-type.util';
import { SERVER_API_URL } from 'app/config/constants';

import { IPivotModel, defaultValue } from 'app/shared/model/pivot-model.model';

export const ACTION_TYPES = {
  FETCH_PIVOTMODEL_LIST: 'pivotModel/FETCH_PIVOTMODEL_LIST',
  FETCH_PIVOTMODEL: 'pivotModel/FETCH_PIVOTMODEL',
  CREATE_PIVOTMODEL: 'pivotModel/CREATE_PIVOTMODEL',
  UPDATE_PIVOTMODEL: 'pivotModel/UPDATE_PIVOTMODEL',
  DELETE_PIVOTMODEL: 'pivotModel/DELETE_PIVOTMODEL',
  RESET: 'pivotModel/RESET'
};

const initialState = {
  loading: false,
  errorMessage: null,
  entities: [] as ReadonlyArray<IPivotModel>,
  entity: defaultValue,
  updating: false,
  updateSuccess: false
};

export type PivotModelState = Readonly<typeof initialState>;

// Reducer

export default (state: PivotModelState = initialState, action): PivotModelState => {
  switch (action.type) {
    case REQUEST(ACTION_TYPES.FETCH_PIVOTMODEL_LIST):
    case REQUEST(ACTION_TYPES.FETCH_PIVOTMODEL):
      return {
        ...state,
        errorMessage: null,
        updateSuccess: false,
        loading: true
      };
    case REQUEST(ACTION_TYPES.CREATE_PIVOTMODEL):
    case REQUEST(ACTION_TYPES.UPDATE_PIVOTMODEL):
    case REQUEST(ACTION_TYPES.DELETE_PIVOTMODEL):
      return {
        ...state,
        errorMessage: null,
        updateSuccess: false,
        updating: true
      };
    case FAILURE(ACTION_TYPES.FETCH_PIVOTMODEL_LIST):
    case FAILURE(ACTION_TYPES.FETCH_PIVOTMODEL):
    case FAILURE(ACTION_TYPES.CREATE_PIVOTMODEL):
    case FAILURE(ACTION_TYPES.UPDATE_PIVOTMODEL):
    case FAILURE(ACTION_TYPES.DELETE_PIVOTMODEL):
      return {
        ...state,
        loading: false,
        updating: false,
        updateSuccess: false,
        errorMessage: action.payload
      };
    case SUCCESS(ACTION_TYPES.FETCH_PIVOTMODEL_LIST):
      return {
        ...state,
        loading: false,
        entities: action.payload.data
      };
    case SUCCESS(ACTION_TYPES.FETCH_PIVOTMODEL):
      return {
        ...state,
        loading: false,
        entity: action.payload.data
      };
    case SUCCESS(ACTION_TYPES.CREATE_PIVOTMODEL):
    case SUCCESS(ACTION_TYPES.UPDATE_PIVOTMODEL):
      return {
        ...state,
        updating: false,
        updateSuccess: true,
        entity: action.payload.data
      };
    case SUCCESS(ACTION_TYPES.DELETE_PIVOTMODEL):
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

const apiUrl = SERVER_API_URL + '/api/pivot-models';

// Actions

export const getEntities: ICrudGetAllAction<IPivotModel> = (page, size, sort) => ({
  type: ACTION_TYPES.FETCH_PIVOTMODEL_LIST,
  payload: axios.get<IPivotModel>(`${apiUrl}?cacheBuster=${new Date().getTime()}`)
});

export const getEntity: ICrudGetAction<IPivotModel> = id => {
  const requestUrl = `${apiUrl}/${id}`;
  return {
    type: ACTION_TYPES.FETCH_PIVOTMODEL,
    payload: axios.get<IPivotModel>(requestUrl)
  };
};

export const createEntity: ICrudPutAction<IPivotModel> = entity => async dispatch => {
  const result = await dispatch({
    type: ACTION_TYPES.CREATE_PIVOTMODEL,
    payload: axios.post(apiUrl, cleanEntity(entity))
  });
  dispatch(getEntities());
  return result;
};

export const updateEntity: ICrudPutAction<IPivotModel> = entity => async dispatch => {
  const result = await dispatch({
    type: ACTION_TYPES.UPDATE_PIVOTMODEL,
    payload: axios.put(apiUrl, cleanEntity(entity))
  });
  dispatch(getEntities());
  return result;
};

export const deleteEntity: ICrudDeleteAction<IPivotModel> = id => async dispatch => {
  const requestUrl = `${apiUrl}/${id}`;
  const result = await dispatch({
    type: ACTION_TYPES.DELETE_PIVOTMODEL,
    payload: axios.delete(requestUrl)
  });
  dispatch(getEntities());
  return result;
};

export const reset = () => ({
  type: ACTION_TYPES.RESET
});
