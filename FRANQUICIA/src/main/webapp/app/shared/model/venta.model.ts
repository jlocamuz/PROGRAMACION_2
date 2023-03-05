import dayjs from 'dayjs';
import { IMenu } from 'app/shared/model/menu.model';

export interface IVenta {
  id?: number;
  fecha?: string | null;
  precio?: number | null;
  menu?: IMenu | null;
}

export const defaultValue: Readonly<IVenta> = {};
