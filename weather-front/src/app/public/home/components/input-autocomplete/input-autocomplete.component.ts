import { Component, EventEmitter, OnInit, Output } from '@angular/core';
import { Municipality } from 'src/app/core/models/municipality';
import { FormControl } from '@angular/forms';
import { MatAutocompleteSelectedEvent } from '@angular/material/autocomplete';
import { Observable, debounceTime } from 'rxjs';
import { map, startWith } from 'rxjs/operators';
import { SharedService } from 'src/app/core/shared/services/shared.service';
import { Forecast, ForecastDTO } from 'src/app/core/models/forecast';
import { InputAutocompleteService } from '../../services/input-autocomplete.service';

@Component({
  selector: 'app-input-autocomplete',
  templateUrl: './input-autocomplete.component.html',
  styleUrls: ['./input-autocomplete.component.scss']
})
export class InputAutocompleteComponent implements OnInit {
    formControl: FormControl = new FormControl();
    municipalities: Municipality[] = [];
    forecast: any = {};
    filteredMunicipalities: Observable<Municipality[]> = new Observable<Municipality[]>();

    constructor(private inputAutoCompleteService: InputAutocompleteService, private sharedService: SharedService) {}
    
    ngOnInit(): void {
      this.sharedService.readyFlag$.subscribe({
        next: (flag) => {
          if (flag) {
            this.getMunicipalities();
          }
        },
        error: (error) => { console.log(error); }
      });
    }
    

    getMunicipalities() {
        this.inputAutoCompleteService.getMunicipalities().subscribe({
            next: (response) => {
                this.municipalities = response;
                this.filteredMunicipalities = this.formControl.valueChanges.pipe(
                    debounceTime(500),
                    startWith(''),
                    map(value => this.filterMunicipalities(value))
                  );
                  const municipalityCache = localStorage.getItem('MunicipalityCache');
                  if(this.municipalities && municipalityCache)
                    this.getTomorrowForecast(municipalityCache);
            },
            error: (error) => { console.log(error); }
        })

      }
  
      getTomorrowForecast(id : string) {
        this.inputAutoCompleteService.getMunTomorrowForecast(id).subscribe({
          next: (response) => {
            this.forecast = response;
            const forecastDTO = this.forecastToDTO(response);
            this.sharedService.setForecastDTO(forecastDTO);
            this.sharedService.setForecastToSelect(this.forecast);
          },
          error: (error) => {
            console.log(error);
          }
        })
      }

      onMunSelected(event: MatAutocompleteSelectedEvent) {
        const selectedMunName = event.option.value;
        const filteredMunByName = this.municipalities.find(mun => mun.name === selectedMunName)
        if(filteredMunByName){
          this.getTomorrowForecast(filteredMunByName.id);
          localStorage.setItem('MunicipalityCache', filteredMunByName.id);
      }
    }
  
    forecastToDTO(forecast: Forecast): ForecastDTO {
      const forecastDTO: ForecastDTO = {
        temperature: forecast.temperature,
        probPrecipitations: forecast.probPrecipitations,
        date: forecast.date,
        municipality: forecast.municipality.name,
      };  
      return forecastDTO;
    }

    filterMunicipalities(name: string) { 
        return this.municipalities.filter(mun =>mun.name.toLocaleLowerCase().includes(name.toLocaleLowerCase()));
      }
}