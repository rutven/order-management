import { IOrderPosition } from 'app/shared/model//order-position.model';

export interface IProduct {
  id?: number;
  name?: string;
  description?: string;
  orderPositions?: IOrderPosition[];
}

export const defaultValue: Readonly<IProduct> = {};
