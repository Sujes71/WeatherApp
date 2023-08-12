import { Component, OnInit } from '@angular/core';
import { format } from 'date-fns';
import { es } from 'date-fns/locale';
import { SharedService } from 'src/app/core/shared/services/shared.service';

   
@Component({
  selector: 'app-info-display',
  templateUrl: './info-display.component.html',
  styleUrls: ['./info-display.component.scss']
})
export class InfoDisplayComponent implements OnInit {
    forecast: any;
    formattedDate: string = '';
    showedTemp: string = '';

    constructor(private sharedService: SharedService) {}
    
    ngOnInit(): void {
        this.sharedService.forecastToDisplay$.subscribe( {
          next: (response) => {
            this.forecast = response;
            
            this.asign();
          }
        });
        this.sharedService.showedTemp$.subscribe( {
          next: (response) => {
            this.showedTemp = response;
          }
        })
    }
    asign() {
      if (this.forecast) { // Verificar si forecast tiene un valor antes de acceder a sus propiedades
        const date = new Date(this.forecast.date);
        const formattedDate = "EEEE, d 'de' MMMM 'de' y";
        this.formattedDate = format(date, formattedDate, { locale: es });
        if(this.showedTemp === '')
          this.showedTemp = this.forecast.temperature.avg + 'ºC';
      }
    }

}