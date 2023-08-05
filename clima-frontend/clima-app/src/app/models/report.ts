import { ProbPrecipitation } from './probPrecipitation';

export interface Report {
  id: string;
  name: string;
  date: Date;
  temAvg: number;
  temUnit: string;
  probPrecipitations: ProbPrecipitation[];
}