import axios from 'axios';
import { ICrudGetAction, ICrudGetAllAction, ICrudPutAction, ICrudDeleteAction } from 'react-jhipster';

import { cleanEntity } from 'app/shared/util/entity-utils';
import { REQUEST, SUCCESS, FAILURE } from 'app/shared/reducers/action-type.util';
import { SERVER_API_URL } from 'app/config/constants';

import { ILigneESPF, defaultValue } from 'app/shared/model/ligne-espf.model';

export const ACTION_TYPES = {
  FETCH_LIGNEESPF_LIST: 'ligneESPF/FETCH_LIGNEESPF_LIST',
  FETCH_LIGNEESPF: 'ligneESPF/FETCH_LIGNEESPF',
  CREATE_LIGNEESPF: 'ligneESPF/CREATE_LIGNEESPF',
  UPDATE_LIGNEESPF: 'ligneESPF/UPDATE_LIGNEESPF',
  DELETE_LIGNEESPF: 'ligneESPF/DELETE_LIGNEESPF',
  RESET: 'ligneESPF/RESET'
};

const initialState = {
  loading: false,
  errorMessage: null,
  entities: [] as ReadonlyArray<ILigneESPF>,
  entity: defaultValue,
  updating: false,
  updateSuccess: false
};

export type LigneESPFState = Readonly<typeof initialState>;

// Reducer

export default (state: LigneESPFState = initialState, action): LigneESPFState => {
  switch (action.type) {
    case REQUEST(ACTION_TYPES.FETCH_LIGNEESPF_LIST):
    case REQUEST(ACTION_TYPES.FETCH_LIGNEESPF):
      return {
        ...state,
        errorMessage: null,
        updateSuccess: false,
        loading: true
      };
    case REQUEST(ACTION_TYPES.CREATE_LIGNEESPF):
    case REQUEST(ACTION_TYPES.UPDATE_LIGNEESPF):
    case REQUEST(ACTION_TYPES.DELETE_LIGNEESPF):
      return {
        ...state,
        errorMessage: null,
        updateSuccess: false,
        updating: true
      };
    case FAILURE(ACTION_TYPES.FETCH_LIGNEESPF_LIST):
    case FAILURE(ACTION_TYPES.FETCH_LIGNEESPF):
    case FAILURE(ACTION_TYPES.CREATE_LIGNEESPF):
    case FAILURE(ACTION_TYPES.UPDATE_LIGNEESPF):
    case FAILURE(ACTION_TYPES.DELETE_LIGNEESPF):
      return {
        ...state,
        loading: false,
        updating: false,
        updateSuccess: false,
        errorMessage: action.payload
      };
    case SUCCESS(ACTION_TYPES.FETCH_LIGNEESPF_LIST):
      return {
        ...state,
        loading: false,
        entities: action.payload.data
      };
    case SUCCESS(ACTION_TYPES.FETCH_LIGNEESPF):
      return {
        ...state,
        loading: false,
        entity: action.payload.data
      };
    case SUCCESS(ACTION_TYPES.CREATE_LIGNEESPF):
    case SUCCESS(ACTION_TYPES.UPDATE_LIGNEESPF):
      return {
        ...state,
        updating: false,
        updateSuccess: true,
        entity: action.payload.data
      };
    case SUCCESS(ACTION_TYPES.DELETE_LIGNEESPF):
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

const apiUrl = SERVER_API_URL + '/api/ligne-espfs';

// Actions

export const getEntities: ICrudGetAllAction<ILigneESPF> = (page, size, sort) => ({
  type: ACTION_TYPES.FETCH_LIGNEESPF_LIST,
  payload: axios.get<ILigneESPF>(`${apiUrl}?cacheBuster=${new Date().getTime()}`)
});

export const getEntity: ICrudGetAction<ILigneESPF> = id => {
  const requestUrl = `${apiUrl}/${id}`;
  return {
    type: ACTION_TYPES.FETCH_LIGNEESPF,
    payload: axios.get<ILigneESPF>(requestUrl)
  };
};

export const createEntity: ICrudPutAction<ILigneESPF> = entity => async dispatch => {
  const result = await dispatch({
    type: ACTION_TYPES.CREATE_LIGNEESPF,
    payload: axios.post(apiUrl, cleanEntity(entity))
  });
  dispatch(getEntities());
  return result;
};

export const updateEntity: ICrudPutAction<ILigneESPF> = entity => async dispatch => {
  const result = await dispatch({
    type: ACTION_TYPES.UPDATE_LIGNEESPF,
    payload: axios.put(apiUrl, cleanEntity(entity))
  });
  dispatch(getEntities());
  return result;
};

export const deleteEntity: ICrudDeleteAction<ILigneESPF> = id => async dispatch => {
  const requestUrl = `${apiUrl}/${id}`;
  const result = await dispatch({
    type: ACTION_TYPES.DELETE_LIGNEESPF,
    payload: axios.delete(requestUrl)
  });
  dispatch(getEntities());
  return result;
};

export const reset = () => ({
  type: ACTION_TYPES.RESET
});
