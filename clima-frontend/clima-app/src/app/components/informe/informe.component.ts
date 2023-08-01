import { Component, OnInit} from '@angular/core';
import { FormControl } from '@angular/forms';
import { MatAutocompleteSelectedEvent } from '@angular/material/autocomplete';
import { firstValueFrom, Observable, debounceTime } from 'rxjs';
import { startWith, map } from 'rxjs/operators';
import { InformeService } from '../../services/informe/informe.service';
import { Municipio } from 'src/app/models/municipio';
import { format } from 'date-fns';
import { es } from 'date-fns/locale';

@Component({
  selector: 'app-informe',
  templateUrl: './informe.component.html',
  styleUrls: ['./informe.component.scss']
})
export class InformeComponent {
  municipios: Municipio[] = [];
  municipioControl = new FormControl();
  fechaMunicipio: string = '';
  tempMedia: string = '';
  filteredMunicipios: Observable<string[]> = new Observable<string[]>();
  informeJSON: any | null = null;
  municipioEncontrado: boolean = true;
  unidadTemperaturaSeleccionada: string = "G_CEL";


  constructor(private InformeService: InformeService) {}

  ngOnInit(): void{
    this.getMunicipios();
  }

  async getClimaMunicipioManana(nombre: string): Promise<void> {
    try {
      this.informeJSON = await firstValueFrom(this.InformeService.getClimaMunicipioManana(nombre));; // Aquí esperamos a que la promesa se resuelva
      this.municipioEncontrado = true;
      const fecha = new Date(this.informeJSON.fecha);
      const formatoDeseado = "EEEE, d 'de' MMMM 'de' y";
      this.fechaMunicipio = format(fecha, formatoDeseado, { locale: es });
      this.tempMedia =   Number(this.informeJSON.mediaTemperatura.toFixed(0)) +"ºC";
      if(this.unidadTemperaturaSeleccionada == "G_FAH"){
        this.informeJSON.unidadTemperatura = "G_FAH";
        this.informeJSON.mediaTemperatura = this.celsiusAFahrenheit(this.informeJSON.mediaTemperatura);
        this.tempMedia =  this.informeJSON.mediaTemperatura +"ºF"
      }
    } catch (error) {
      this.informeJSON = null;
      setTimeout(() => {
        this.municipioEncontrado = false;
      }, 1000);
      console.error(error);
    }
  }
  
  async getMunicipios() : Promise<void>{
    try {
    this.municipios = await firstValueFrom(this.InformeService.getMunicipios());
    this.filteredMunicipios = this.municipioControl.valueChanges.pipe(
      debounceTime(500),
      startWith(''),
      map(value => this.filterMunicipios(value))
    );
  } catch (error) {
    console.error(error);
  }
}

filterMunicipios(value: string): string[] {
  const filterValue = value.toLowerCase();
  return this.municipios.filter(municipio => municipio.nombre.toLowerCase().includes(filterValue)).map(municipio => municipio.nombre);
}

onMunicipioSelected(event: MatAutocompleteSelectedEvent): void {
  if(!this.municipioEncontrado) 
    this.municipioEncontrado = true;
  this.getClimaMunicipioManana(event.option.value);
  
}

  cambiarUnidadTemperatura(event: any): void {
    const valorSeleccionado = event.target.value;
    if (valorSeleccionado === "G_CEL") {  
      this.unidadTemperaturaSeleccionada = "G_CEL";
      this.informeJSON.unidadTemperatura = "G_CEL";
      this.informeJSON.mediaTemperatura = this.fahrenheitACelsius(this.informeJSON.mediaTemperatura);
      this.tempMedia = this.informeJSON.mediaTemperatura + "ºC";
    } else if (valorSeleccionado === "G_FAH") {
      this.unidadTemperaturaSeleccionada = "G_FAH";
      this.informeJSON.unidadTemperatura = "G_FAH";
      this.informeJSON.mediaTemperatura = this.celsiusAFahrenheit(this.informeJSON.mediaTemperatura);
      this.tempMedia = this.informeJSON.mediaTemperatura + "ºF";
    }
  }

  celsiusAFahrenheit(celsius: number): number {
    const fahrenheit = (celsius * 9 / 5) + 32;
    return Number(fahrenheit.toFixed(0))
  }
  
  fahrenheitACelsius(fahrenheit: number): number {
    const celsius = (fahrenheit - 32) * 5 / 9;
    return Number(celsius.toFixed(0));
  }
}