import { ProbPrecipitacion } from './probPrecipitacion';

export interface Informe {
  nombre: string | null;
  fecha: Date | null;
  mediaTemperatura: number | null;
  unidadTemperatura: string | null;
  probPrecipitacion: ProbPrecipitacion[];
  
}