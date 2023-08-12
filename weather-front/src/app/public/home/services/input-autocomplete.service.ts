import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable, catchError } from 'rxjs';
import { Forecast } from 'src/app/core/models/forecast';
import { Municipality } from 'src/app/core/models/municipality';
import { SharedService } from 'src/app/core/shared/services/shared.service';

@Injectable({
  providedIn: 'root',
})
export class InputAutocompleteService { 

  constructor(private http: HttpClient, private sharedService: SharedService) { }

  getMunTomorrowForecast(id: String): Observable<Forecast> {
    const url = `/api/aemet/mun/prediction/tomorrow/${id}`;
    return this.http.get<Forecast>(url).pipe(
        catchError(this.sharedService.handleError)
    )
  }
  
  getMunicipalities(): Observable<Municipality[]> {
    const url = '/api/aemet/mun/all';
    return this.http.get<Municipality[]>(url).pipe(
        catchError(this.sharedService.handleError)
    );
  }

}