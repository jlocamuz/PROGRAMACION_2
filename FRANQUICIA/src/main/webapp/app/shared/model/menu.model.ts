import dayjs from 'dayjs';
import { IVenta } from 'app/shared/model/venta.model';

export interface IMenu {
  id?: string;
  nombre?: string | null;
  descripcion?: string | null;
  precio?: number | null;
  urlImagen?: string | null;
  activo?: boolean | null;
  creado?: string | null;
  actualizado?: string | null;
  ventas?: IVenta[] | null;
}

export const defaultValue: Readonly<IMenu> = {
  activo: false,
};
