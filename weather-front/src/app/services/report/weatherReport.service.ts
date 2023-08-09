import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';

import { Temperature } from 'src/app/models/temperature';
import { Municipality } from '../../models/municipality';
import { Report } from '../../models/report';

@Injectable({
  providedIn: 'root',
})
export class WeatherReportService {

  constructor(private http: HttpClient) { }

  getMunPredictionTomorrow(id: string): Observable<Report> {
    const url = `/api/aemet/mun/prediction/tomorrow/${id}`;
    return this.http.get<Report>(url);
  }
  
  getMuns(): Observable<Municipality[]> {
    const url = '/api/aemet/mun/all';
    return this.http.get<Municipality[]>(url);
  }

  getConversion(avg: number, unit: string): Observable<Temperature> {
    const url = `/api/aemet/mun/conversion/${avg}/${unit}`;
    return this.http.get<Temperature>(url);
  }

  getToken(code: string): Observable<string> {
    const url = `/api/auth/token/${code}`;
    return this.http.get(url, {responseType: 'text'});
  }

  getRandomCode(): Observable<string> {
    const url = `/api/auth/code/random`;
    return this.http.get(url, {responseType: 'text'});
  }
  
}
