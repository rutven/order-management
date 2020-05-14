import axios from 'axios';
import { ICrudGetAction, ICrudGetAllAction, ICrudPutAction, ICrudDeleteAction } from 'react-jhipster';

import { cleanEntity } from 'app/shared/util/entity-utils';
import { REQUEST, SUCCESS, FAILURE } from 'app/shared/reducers/action-type.util';

import { IOrderEntity, defaultValue } from 'app/shared/model/order-entity.model';

export const ACTION_TYPES = {
  FETCH_ORDERENTITY_LIST: 'orderEntity/FETCH_ORDERENTITY_LIST',
  FETCH_ORDERENTITY: 'orderEntity/FETCH_ORDERENTITY',
  CREATE_ORDERENTITY: 'orderEntity/CREATE_ORDERENTITY',
  UPDATE_ORDERENTITY: 'orderEntity/UPDATE_ORDERENTITY',
  DELETE_ORDERENTITY: 'orderEntity/DELETE_ORDERENTITY',
  RESET: 'orderEntity/RESET'
};

const initialState = {
  loading: false,
  errorMessage: null,
  entities: [] as ReadonlyArray<IOrderEntity>,
  entity: defaultValue,
  updating: false,
  updateSuccess: false
};

export type OrderEntityState = Readonly<typeof initialState>;

// Reducer

export default (state: OrderEntityState = initialState, action): OrderEntityState => {
  switch (action.type) {
    case REQUEST(ACTION_TYPES.FETCH_ORDERENTITY_LIST):
    case REQUEST(ACTION_TYPES.FETCH_ORDERENTITY):
      return {
        ...state,
        errorMessage: null,
        updateSuccess: false,
        loading: true
      };
    case REQUEST(ACTION_TYPES.CREATE_ORDERENTITY):
    case REQUEST(ACTION_TYPES.UPDATE_ORDERENTITY):
    case REQUEST(ACTION_TYPES.DELETE_ORDERENTITY):
      return {
        ...state,
        errorMessage: null,
        updateSuccess: false,
        updating: true
      };
    case FAILURE(ACTION_TYPES.FETCH_ORDERENTITY_LIST):
    case FAILURE(ACTION_TYPES.FETCH_ORDERENTITY):
    case FAILURE(ACTION_TYPES.CREATE_ORDERENTITY):
    case FAILURE(ACTION_TYPES.UPDATE_ORDERENTITY):
    case FAILURE(ACTION_TYPES.DELETE_ORDERENTITY):
      return {
        ...state,
        loading: false,
        updating: false,
        updateSuccess: false,
        errorMessage: action.payload
      };
    case SUCCESS(ACTION_TYPES.FETCH_ORDERENTITY_LIST):
      return {
        ...state,
        loading: false,
        entities: action.payload.data
      };
    case SUCCESS(ACTION_TYPES.FETCH_ORDERENTITY):
      return {
        ...state,
        loading: false,
        entity: action.payload.data
      };
    case SUCCESS(ACTION_TYPES.CREATE_ORDERENTITY):
    case SUCCESS(ACTION_TYPES.UPDATE_ORDERENTITY):
      return {
        ...state,
        updating: false,
        updateSuccess: true,
        entity: action.payload.data
      };
    case SUCCESS(ACTION_TYPES.DELETE_ORDERENTITY):
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

const apiUrl = 'api/order-entities';

// Actions

export const getEntities: ICrudGetAllAction<IOrderEntity> = (page, size, sort) => ({
  type: ACTION_TYPES.FETCH_ORDERENTITY_LIST,
  payload: axios.get<IOrderEntity>(`${apiUrl}?cacheBuster=${new Date().getTime()}`)
});

export const getEntity: ICrudGetAction<IOrderEntity> = id => {
  const requestUrl = `${apiUrl}/${id}`;
  return {
    type: ACTION_TYPES.FETCH_ORDERENTITY,
    payload: axios.get<IOrderEntity>(requestUrl)
  };
};

export const createEntity: ICrudPutAction<IOrderEntity> = entity => async dispatch => {
  const result = await dispatch({
    type: ACTION_TYPES.CREATE_ORDERENTITY,
    payload: axios.post(apiUrl, cleanEntity(entity))
  });
  dispatch(getEntities());
  return result;
};

export const updateEntity: ICrudPutAction<IOrderEntity> = entity => async dispatch => {
  const result = await dispatch({
    type: ACTION_TYPES.UPDATE_ORDERENTITY,
    payload: axios.put(apiUrl, cleanEntity(entity))
  });
  dispatch(getEntities());
  return result;
};

export const deleteEntity: ICrudDeleteAction<IOrderEntity> = id => async dispatch => {
  const requestUrl = `${apiUrl}/${id}`;
  const result = await dispatch({
    type: ACTION_TYPES.DELETE_ORDERENTITY,
    payload: axios.delete(requestUrl)
  });
  dispatch(getEntities());
  return result;
};

export const reset = () => ({
  type: ACTION_TYPES.RESET
});
