import dayjs from 'dayjs';

export interface IVenta {
  id?: string;
  fecha?: string | null;
  precio?: number | null;
  menu?: number | null;
}

export const defaultValue: Readonly<IVenta> = {};
