import dayjs from 'dayjs';

export interface IReporteHistorico {
  id?: number;
  accion?: string | null;
  franquiciaUUID?: string | null;
  tipo?: string | null;
  fechaInicio?: string | null;
  fechaFin?: string | null;
}

export const defaultValue: Readonly<IReporteHistorico> = {};
