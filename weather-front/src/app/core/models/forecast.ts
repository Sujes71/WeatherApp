import { ProbPrecipitation } from './probPrecipitation';
import { Municipality } from './municipality';
import { Temperature } from './temperature';

export interface Forecast {
  municipality: Municipality;
  date: Date;
  temperature: Temperature;
  probPrecipitations: ProbPrecipitation[];
}

export interface ForecastDTO {
  municipality: String;
  date: Date;
  temperature: Temperature;
  probPrecipitations: ProbPrecipitation[];
}