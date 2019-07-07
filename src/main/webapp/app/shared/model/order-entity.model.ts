import { Moment } from 'moment';
import { IOrderPosition } from 'app/shared/model/order-position.model';

export interface IOrderEntity {
  id?: number;
  orderDate?: Moment;
  orderPositions?: IOrderPosition[];
}

export const defaultValue: Readonly<IOrderEntity> = {};
