import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable, catchError } from 'rxjs';
import { Temperature } from 'src/app/core/models/temperature';
import { SharedService } from 'src/app/core/shared/services/shared.service';

@Injectable({
  providedIn: 'root',
})
export class SelectService { 

  constructor(private http: HttpClient, private sharedService: SharedService) { }


  getConversion(avg: Number, unit: String): Observable<Temperature> {
    const url = `/api/aemet/mun/conversion/${avg}/${unit}`;
    return this.http.get<Temperature>(url).pipe(
        catchError(this.sharedService.handleError)
    );
  }
}