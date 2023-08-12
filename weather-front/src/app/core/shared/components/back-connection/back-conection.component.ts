import { Component, OnInit } from '@angular/core';
import { SharedService } from '../../services/shared.service';
import { catchError, tap } from 'rxjs';

   
@Component({
  selector: 'app-back-connection',
  templateUrl: './back-connection.component.html',
  styleUrls: ['./back-connection.component.scss']
})
export class BackConnectionComponent implements OnInit {
  randomCode: string = '';
  constructor(private sharedService: SharedService) { }
    
    ngOnInit(): void {
      const min = 1000000000;
      const max = 9999999999;
      this.randomCode = (Math.floor(Math.random() * (max - min + 1)) + min).toString();

      this.getToken().subscribe( {
        next: () => {
          this.sharedService.setReadyFlag(true);
        },
        error: (error) => { console.log(error);}
      })
    }

    getToken() {
      return this.sharedService.getToken(this.randomCode).pipe(
        tap((response) => {
          localStorage.setItem('jwtToken', response);
        }),
        catchError((error) => {
          return this.sharedService.handleError(error);
        })
      );
    }
}