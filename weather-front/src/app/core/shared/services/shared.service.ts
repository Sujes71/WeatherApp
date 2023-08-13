import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable, catchError, throwError } from 'rxjs';
import { BehaviorSubject } from 'rxjs';

@Injectable({
  providedIn: 'root',
})
export class SharedService { 

  private forecastDTO = new BehaviorSubject<any>(null);
  forecasDTO$ = this.forecastDTO.asObservable();

  private readyFlag = new BehaviorSubject<boolean>(false);
  readyFlag$ = this.readyFlag.asObservable();

  private forecastToSelect = new BehaviorSubject<any>(null);
  forecastToSelect$ = this.forecastToSelect.asObservable();

  private forecastToDisplay = new BehaviorSubject<any>(null);
  forecastToDisplay$ = this.forecastToDisplay.asObservable();

  private showedTemp = new BehaviorSubject<string>('');
  showedTemp$ = this.showedTemp.asObservable();

  private readyIndex = new BehaviorSubject<boolean>(false);
  readyIndex$ = this.readyIndex.asObservable();

  constructor(private http: HttpClient) { }

  getToken(code: String): Observable<string> {
    const url = `/api/auth/token/${code}`;
    return this.http.get(url, {responseType: 'text'}).pipe(
        catchError(this.handleError)
    );
  }

  setForecastDTO(forecastDTO: any) {
    this.forecastDTO.next(forecastDTO);
  }

  setShowedTemp(temp: string) {
    this.showedTemp.next(temp);
  }

  setrReadyIndex(readyIndex: boolean) {
    this.readyIndex.next(readyIndex);
  }

  setReadyFlag(flag: boolean) {
    this.readyFlag.next(flag);
  }

  setForecastToSelect(forecastToSelect: any) {
    this.forecastToSelect.next(forecastToSelect);
  }

  setForecastToDisplay(forecastToDisplay: any): void {
    this.forecastToDisplay.next(forecastToDisplay);
  }

  public handleError(error: Response) {
    console.log(error);
    const msg = 'Error status code: ' + error.status + ' status:' + error.statusText + '\n';
    return throwError(() => new Error(msg));
  }

}