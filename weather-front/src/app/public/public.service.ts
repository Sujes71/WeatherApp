import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable, catchError, throwError } from 'rxjs';

import { Temperature } from 'src/app/core/models/temperature';
import { Municipality } from '../core/models/municipality';
import { Forecast } from '../core/models/forecast';

@Injectable({
  providedIn: 'root',
})
export class PublicService { 

  constructor(private http: HttpClient) { }

  getMunTomorrowForecast(id: string): Observable<Forecast> {
    const url = `/api/aemet/mun/prediction/tomorrow/${id}`;
    return this.http.get<Forecast>(url).pipe(
        catchError(this.handleError)
    )
  }
  
  getMunicipalities(): Observable<Municipality[]> {
    const url = '/api/aemet/mun/all';
    return this.http.get<Municipality[]>(url).pipe(
        catchError(this.handleError)
    );
  }

  getConversion(avg: number, unit: string): Observable<Temperature> {
    const url = `/api/aemet/mun/conversion/${avg}/${unit}`;
    return this.http.get<Temperature>(url).pipe(
        catchError(this.handleError)
    );
  }

  getToken(code: string): Observable<string> {
    const url = `/api/auth/token/${code}`;
    return this.http.get(url, {responseType: 'text'}).pipe(
        catchError(this.handleError)
    );
  }

  getRandomCode(): Observable<string> {
    const url = `/api/auth/code/random`;
    return this.http.get(url, {responseType: 'text'}).pipe(
        catchError(this.handleError)
    );
  }

  private handleError(error: Response) {
    console.log(error);
    const msg = 'Erros status code: ' + error.status + 'status' + error.statusText;
    return throwError(() => new Error(msg));
  }

}