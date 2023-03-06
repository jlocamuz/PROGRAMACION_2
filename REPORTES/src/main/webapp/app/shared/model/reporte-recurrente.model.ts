import dayjs from 'dayjs';

export interface IReporteRecurrente {
  id?: number;
  accion?: string | null;
  franquiciaUUID?: string | null;
  tipo?: string | null;
  fechaInicio?: string | null;
  fechaFin?: string | null;
  intervalo?: string | null;
}

export const defaultValue: Readonly<IReporteRecurrente> = {};
