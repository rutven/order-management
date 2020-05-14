import { IProduct } from 'app/shared/model/product.model';
import { IOrderEntity } from 'app/shared/model/order-entity.model';

export interface IOrderPosition {
  id?: number;
  quantity?: number;
  product?: IProduct;
  orderEntity?: IOrderEntity;
}

export const defaultValue: Readonly<IOrderPosition> = {};
