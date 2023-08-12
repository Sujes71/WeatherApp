import { Component, OnInit } from '@angular/core';
import { SharedService } from 'src/app/core/shared/services/shared.service';
import { Forecast } from 'src/app/core/models/forecast';
import { SaveButtonService } from '../../services/save-button.service';

@Component({
  selector: 'app-save-button',
  templateUrl: './save-button.component.html',
  styleUrls: ['./save-button.component.scss']
})
export class SaveButtonComponent implements OnInit {
  forecast: any;
  forecasts: Forecast[] = [];
  isButtonDisabled: boolean = true;

  constructor(private saveButtonService: SaveButtonService, private sharedService: SharedService) {}

  ngOnInit(): void {
    this.sharedService.forecasDTO$.subscribe( {
      next: (response) => {
        this.forecast = response;
      },
      error: (error) => { console.log(error); }
    })
  }

  addForecast() {
    this.sharedService.forecasDTO$.subscribe(forecast => {
      this.forecast = forecast;
    });
    if (this.forecast) {
      this.saveButtonService.addForecast(this.forecast).subscribe( {
          next: (response) => {
            console.log('El post ha sido exitoso' + response);
          },
          error: (error) => {
            console.log(error)
          }
        }
      );
    }
  }
  
  getAllForecast(){
    this.saveButtonService.getAllForecast().subscribe({
      next: (response) => {
        this.forecasts = response;
        console.log(response);
      },
      error: (error) => {
        console.log(error);
      }
    });
  }
}
