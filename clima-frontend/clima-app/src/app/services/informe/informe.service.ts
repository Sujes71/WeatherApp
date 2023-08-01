import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { Informe } from '../../models/informe';
import { Municipio } from '../../models/municipio';

@Injectable({
  providedIn: 'root'
})
export class InformeService {
  private apiUrl = 'http://localhost:8080';

  constructor(private http: HttpClient) { }

  getClimaMunicipioManana(nombre: string): Observable<Informe> {
    const url = `${this.apiUrl}/prediccion-municipio/${nombre}`;
    return this.http.get<Informe>(url);
  }
  getMunicipios(): Observable<Municipio[]> {
    const url = `${this.apiUrl}/municipios`;
    return this.http.get<Municipio[]>(url);
  }
}
