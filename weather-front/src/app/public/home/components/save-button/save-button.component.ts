import { Component, OnInit } from '@angular/core';
import { Forecast } from 'src/app/core/models/forecast';
import { SharedService } from 'src/app/core/shared/services/shared.service';
import { SaveButtonService } from '../../services/save-button.service';

@Component({
  selector: 'app-save-button',
  templateUrl: './save-button.component.html',
  styleUrls: ['./save-button.component.scss']
})
export class SaveButtonComponent implements OnInit {
  forecast: any;
  forecasts: Forecast[] = [];
  isButtonDisabled: boolean = false;

  constructor(private saveButtonService: SaveButtonService, private sharedService: SharedService) {}

  ngOnInit(): void {
    this.sharedService.forecasDTO$.subscribe( {
      next: (response) => {
        this.forecast = response;
        this.isButtonDisabled = false;
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
            this.isButtonDisabled = true;
            console.log(response);
            console.log('Post has been added successfully');
          },
          error: (error) => {
              console.log(error)
          }
        }
      );
    }
  }
}
