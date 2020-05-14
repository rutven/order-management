import axios from 'axios';
import { ICrudGetAction, ICrudGetAllAction, ICrudPutAction, ICrudDeleteAction } from 'react-jhipster';

import { cleanEntity } from 'app/shared/util/entity-utils';
import { REQUEST, SUCCESS, FAILURE } from 'app/shared/reducers/action-type.util';

import { IOrderPosition, defaultValue } from 'app/shared/model/order-position.model';

export const ACTION_TYPES = {
  FETCH_ORDERPOSITION_LIST: 'orderPosition/FETCH_ORDERPOSITION_LIST',
  FETCH_ORDERPOSITION: 'orderPosition/FETCH_ORDERPOSITION',
  CREATE_ORDERPOSITION: 'orderPosition/CREATE_ORDERPOSITION',
  UPDATE_ORDERPOSITION: 'orderPosition/UPDATE_ORDERPOSITION',
  DELETE_ORDERPOSITION: 'orderPosition/DELETE_ORDERPOSITION',
  RESET: 'orderPosition/RESET'
};

const initialState = {
  loading: false,
  errorMessage: null,
  entities: [] as ReadonlyArray<IOrderPosition>,
  entity: defaultValue,
  updating: false,
  updateSuccess: false
};

export type OrderPositionState = Readonly<typeof initialState>;

// Reducer

export default (state: OrderPositionState = initialState, action): OrderPositionState => {
  switch (action.type) {
    case REQUEST(ACTION_TYPES.FETCH_ORDERPOSITION_LIST):
    case REQUEST(ACTION_TYPES.FETCH_ORDERPOSITION):
      return {
        ...state,
        errorMessage: null,
        updateSuccess: false,
        loading: true
      };
    case REQUEST(ACTION_TYPES.CREATE_ORDERPOSITION):
    case REQUEST(ACTION_TYPES.UPDATE_ORDERPOSITION):
    case REQUEST(ACTION_TYPES.DELETE_ORDERPOSITION):
      return {
        ...state,
        errorMessage: null,
        updateSuccess: false,
        updating: true
      };
    case FAILURE(ACTION_TYPES.FETCH_ORDERPOSITION_LIST):
    case FAILURE(ACTION_TYPES.FETCH_ORDERPOSITION):
    case FAILURE(ACTION_TYPES.CREATE_ORDERPOSITION):
    case FAILURE(ACTION_TYPES.UPDATE_ORDERPOSITION):
    case FAILURE(ACTION_TYPES.DELETE_ORDERPOSITION):
      return {
        ...state,
        loading: false,
        updating: false,
        updateSuccess: false,
        errorMessage: action.payload
      };
    case SUCCESS(ACTION_TYPES.FETCH_ORDERPOSITION_LIST):
      return {
        ...state,
        loading: false,
        entities: action.payload.data
      };
    case SUCCESS(ACTION_TYPES.FETCH_ORDERPOSITION):
      return {
        ...state,
        loading: false,
        entity: action.payload.data
      };
    case SUCCESS(ACTION_TYPES.CREATE_ORDERPOSITION):
    case SUCCESS(ACTION_TYPES.UPDATE_ORDERPOSITION):
      return {
        ...state,
        updating: false,
        updateSuccess: true,
        entity: action.payload.data
      };
    case SUCCESS(ACTION_TYPES.DELETE_ORDERPOSITION):
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

const apiUrl = 'api/order-positions';

// Actions

export const getEntities: ICrudGetAllAction<IOrderPosition> = (page, size, sort) => ({
  type: ACTION_TYPES.FETCH_ORDERPOSITION_LIST,
  payload: axios.get<IOrderPosition>(`${apiUrl}?cacheBuster=${new Date().getTime()}`)
});

export const getEntity: ICrudGetAction<IOrderPosition> = id => {
  const requestUrl = `${apiUrl}/${id}`;
  return {
    type: ACTION_TYPES.FETCH_ORDERPOSITION,
    payload: axios.get<IOrderPosition>(requestUrl)
  };
};

export const createEntity: ICrudPutAction<IOrderPosition> = entity => async dispatch => {
  const result = await dispatch({
    type: ACTION_TYPES.CREATE_ORDERPOSITION,
    payload: axios.post(apiUrl, cleanEntity(entity))
  });
  dispatch(getEntities());
  return result;
};

export const updateEntity: ICrudPutAction<IOrderPosition> = entity => async dispatch => {
  const result = await dispatch({
    type: ACTION_TYPES.UPDATE_ORDERPOSITION,
    payload: axios.put(apiUrl, cleanEntity(entity))
  });
  return result;
};

export const deleteEntity: ICrudDeleteAction<IOrderPosition> = id => async dispatch => {
  const requestUrl = `${apiUrl}/${id}`;
  const result = await dispatch({
    type: ACTION_TYPES.DELETE_ORDERPOSITION,
    payload: axios.delete(requestUrl)
  });
  return result;
};

export const reset = () => ({
  type: ACTION_TYPES.RESET
});
