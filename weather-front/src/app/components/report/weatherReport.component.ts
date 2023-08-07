import { Component, OnInit } from '@angular/core';
import { FormControl } from '@angular/forms';
import { MatAutocompleteSelectedEvent } from '@angular/material/autocomplete';
import { format } from 'date-fns';
import { es } from 'date-fns/locale';
import { Observable, debounceTime, firstValueFrom } from 'rxjs';
import { map, startWith } from 'rxjs/operators';
import { Municipality } from '../../models/municipality';
import { WeatherReportService } from '../../services/report/weatherReport.service';

   
@Component({
  selector: 'app-weatherReport',
  templateUrl: './weatherReport.component.html',
  styleUrls: ['./weatherReport.component.scss']
})
export class WeatherReportComponent implements OnInit {
  muns: Municipality[] = [];
  checkImg: boolean = false;
  munControl = new FormControl();
  munDate: string = '';
  temAvg: string = '';
  filteredMuns: Observable<Municipality[]> = new Observable<Municipality[]>();
  reportJSON: any = {};
  unitSelected: string = "G_CEL";
  code: string = "";
  lastMunicipioId: string = "";

  constructor(private WeatherReportService: WeatherReportService) { }

  ngOnInit(): void {
    this.getMuns();
    const storedMunicipioId = localStorage.getItem('lastMunicipioId');

    if(storedMunicipioId != null){
      this.lastMunicipioId = storedMunicipioId;
        this.getMunPredictionTomorrow(this.lastMunicipioId);
        this.checkImg = true;
    } 
  }

  getMunPredictionTomorrow = async (id: string): Promise<void> => {
    try {
      this.reportJSON = await firstValueFrom(this.WeatherReportService.getMunPredictionTomorrow(id));
      const fecha = new Date(this.reportJSON.date);
      const formatoDeseado = "EEEE, d 'de' MMMM 'de' y";
      this.munDate = format(fecha, formatoDeseado, { locale: es });
      if(this.unitSelected == "G_FAH"){
        await this.getConversion(this.reportJSON.temAvg, this.unitSelected);
        this.temAvg = this.reportJSON.temAvg +"ºF"
      }
      else
        this.temAvg = this.reportJSON.temAvg +"ºC";
    } catch (error) {
      this.reportJSON = null;
      console.error(error);
    }
  }

  getMuns = async (): Promise<void> => {
    try {
      await this.getToken();
      this.muns = await firstValueFrom(this.WeatherReportService.getMuns());
      this.filteredMuns = this.munControl.valueChanges.pipe(
        debounceTime(500),
        startWith(''),
        map(value => this.filterMuns(value))
      );
    } catch (error) {
      console.error(error);
    }
  }

  getToken = async (): Promise<void> => {
    try {
      await this.getRandomCode();
      const code = await firstValueFrom(this.WeatherReportService.getToken(this.code));
      localStorage.setItem('jwtToken', code);
    } catch (error) {
      console.error(error);
    }
  }

  getRandomCode = async (): Promise<void> => {
    try {
      const random = await firstValueFrom(this.WeatherReportService.getRandomCode());
      this.code = random;
    } catch (error) {
      console.error(error);
    }
  }

  getConversion = async (avg: number, unit: string): Promise<void> => {
    try {
      const temp = await firstValueFrom(this.WeatherReportService.getConversion(avg, unit));
      this.reportJSON.temAvg = temp.avg;
      this.reportJSON.temUnit = temp.unit;
      this.unitSelected = temp.unit;
      if (this.unitSelected === "G_CEL") {
        this.temAvg = this.reportJSON.temAvg + "ºC";
      } else {
        this.temAvg = this.reportJSON.temAvg + "ºF";
      }
    } catch (error) {
      console.error(error);
    }
  }

  filterMuns = (value: string): Municipality[] => {
    const filterValue = value.toLowerCase();
    return this.muns.filter(mun => mun.name.toLowerCase().includes(filterValue));
  }

  onMunSelected = (event: MatAutocompleteSelectedEvent): void => {
    const selectedMunName = event.option.value;
    const selectedMun = this.muns.find(mun => mun.name === selectedMunName);

    if (selectedMun) {
      this.getMunPredictionTomorrow(selectedMun.id);
      this.checkImg = true;
      localStorage.setItem('lastMunicipioId', selectedMun.id);
    }
  }

  changeTemUnit = (event: any): void => {
    const valorSeleccionado = event.target.value;
    this.getConversion(this.reportJSON.temAvg, valorSeleccionado);
  }
}