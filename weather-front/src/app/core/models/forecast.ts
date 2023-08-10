import { ProbPrecipitation } from './probPrecipitation';

export interface Forecast {
  id: string;
  name: string;
  date: Date;
  avg: number;
  unit: string;
  probPrecipitations: ProbPrecipitation[];
}