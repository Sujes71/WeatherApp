import { Component, EventEmitter, Input, OnInit, Output } from '@angular/core';
import { SharedService } from 'src/app/core/shared/services/shared.service';
import { SelectService } from '../../services/select.service';

   
@Component({
  selector: 'app-select',
  templateUrl: './select.component.html',
  styleUrls: ['./select.component.scss']
})
export class SelectComponent implements OnInit {
    
    @Input() forecast: any;
    unitSelected: string = '';
    showedTemp = '';

    constructor(private selectService: SelectService, private sharedService: SharedService) {}
    
    ngOnInit(): void {
        this.sharedService.forecastToSelect$.subscribe( {
          next: (response) => {
            this.forecast = response;     
            this.sharedService.setForecastToDisplay(this.forecast);
            if(this.unitSelected === 'G_FAH')
              this.getConversion(this.forecast.temperature.avg, this.unitSelected);
          },
          error: (error) =>{ console.log(error); }
        });
    }

    getConversion(avg: number, unit: string) {
        this.selectService.getConversion(avg, unit).subscribe({
          next: (response) => {
            this.forecast.temperature.unit = response.unit; 
            this.forecast.temperature.avg = response.avg;
            this.unitSelected = response.unit;
            this.showedTemp = this.unitSelected === 'G_CEL' ? 
                        this.forecast.temperature.avg + 'ºC' : this.showedTemp = this.forecast.temperature.avg + 'ºF';
            this.sharedService.setShowedTemp(this.showedTemp);
            this.sharedService.setForecastToDisplay(this.forecast);
          },
          error: (error) => {
            console.log(error);
          }
        })
      }

      changeTemUnit(event: any) {
        this.getConversion(this.forecast.temperature.avg, event.target.value)
      }
}