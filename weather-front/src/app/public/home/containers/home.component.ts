import { Component, OnInit } from '@angular/core';
import { PublicService } from '../../public.service';
import { Municipality } from 'src/app/core/models/municipality';
import { switchMap } from 'rxjs/operators';
import { FormControl } from '@angular/forms';
import { MatAutocompleteSelectedEvent } from '@angular/material/autocomplete';
import { Observable, debounceTime } from 'rxjs';
import { map, startWith } from 'rxjs/operators';
import { format } from 'date-fns';
import { es } from 'date-fns/locale';

@Component({
  selector: 'app-home',
  templateUrl: './home.component.html',
  styleUrls: ['./home.component.scss']
})
export class HomeComponent implements OnInit {
  formControl: FormControl = new FormControl();
  municipalities: Municipality[] = [];
  forecast: any = {};
  unitSelected: string = '';
  showedTemp = '';
  formattedDate: string = '';
  filteredMunicipalities: Observable<Municipality[]> = new Observable<Municipality[]>();
  checkImage: boolean = false;


    constructor(private publicService: PublicService) {}
    
    ngOnInit(): void {
      this.getInitialState();

      const storedMunicipioId = localStorage.getItem('MunicipalityCache');
      
      if(storedMunicipioId != null){
          this.getTomorrowForecast(storedMunicipioId);
          this.checkImage = true;
      } 
    }

    getInitialState() {
      this.publicService.getRandomCode().pipe(
        switchMap(codeResponse => {
          return this.publicService.getToken(codeResponse);
        })
      ).pipe(
        switchMap(tokenResponse => {
          localStorage.setItem('jwtToken', tokenResponse);
          return this.publicService.getMunicipalities();
        })
      ).subscribe({
        next: (response) => {
          this.municipalities = response;
          this.filteredMunicipalities = this.formControl.valueChanges.pipe(
            debounceTime(500),
            startWith(''),
            map(value => this.filterMunicipalities(value))
          );
        },
        error: (error) => {
          console.log(error);
        }
      })
    }

    getTomorrowForecast(id : string) {
      this.publicService.getMunTomorrowForecast(id).subscribe({
        next: (response) => {
          this.forecast = response;
          
          const date = new Date(this.forecast.date);
          const formattedDate = "EEEE, d 'de' MMMM 'de' y";
          this.formattedDate = format(date, formattedDate, { locale: es });

          if(this.unitSelected == "G_FAH"){
            this.getConversion(this.forecast.avg, this.unitSelected);
            this.showedTemp = this.forecast.avg +"ºF"
          }
          else
            this.showedTemp = this.forecast.avg +"ºC";
        },
        error: (error) => {
          console.log(error);
        }
      })
    }

    getConversion(avg: number, unit: string) {
      this.publicService.getConversion(avg, unit).subscribe({
        next: (response) => {
          this.forecast.unit = response.unit; 
          this.forecast.avg = response.avg;
          this.unitSelected = response.unit;
          if (this.unitSelected === "G_CEL") {
            this.showedTemp = this.forecast.avg + "ºC";
          } else {
            this.showedTemp = this.forecast.avg + "ºF";
          }
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
        this.checkImage = true;
        localStorage.setItem('MunicipalityCache', filteredMunByName.id);
    }
  }

    changeTemUnit(event: any) {
      this.getConversion(this.forecast.avg, event.target.value)
    }

    filterMunicipalities(name: string) { 
      return this.municipalities.filter(mun =>mun.name.toLocaleLowerCase().includes(name.toLocaleLowerCase()));
    }
}