import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable, catchError } from 'rxjs';
import { SharedService } from 'src/app/core/shared/services/shared.service';

@Injectable({
  providedIn: 'root',
})
export class TableService { 

  constructor(private http: HttpClient, private sharedService: SharedService) { }

  getAllForecast(): Observable<Element[]> {
    const url = "/api/forecast/all";
    return this.http.get<Element[]>(url).pipe(
        catchError(this.sharedService.handleError)
    );
  }
  
  deleteForecast(id: String): Observable<String> {
    const url = `/api/command/forecast/delete/${id}`;
    return this.http.delete(url, { responseType: 'text' }).pipe(
      catchError(this.sharedService.handleError)
    );
  }  

}