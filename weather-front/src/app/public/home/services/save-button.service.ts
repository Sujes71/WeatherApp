import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable, catchError } from 'rxjs';
import { SharedService } from 'src/app/core/shared/services/shared.service';

@Injectable({
  providedIn: 'root',
})
export class SaveButtonService { 

    constructor(private http: HttpClient, private sharedService: SharedService ) { }

    addForecast(json: any): Observable<any> {
        const url = '/api/command/forecast/add';
        return this.http.post(url, json, { headers: {'Content-Type': 'application/json'} }).pipe(
          catchError(this.sharedService.handleError)
        );
      }
}