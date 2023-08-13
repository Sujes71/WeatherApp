import { Component, OnInit } from '@angular/core';
import { SharedService } from 'src/app/core/shared/services/shared.service';

@Component({
  selector: 'app-home',
  templateUrl: './home.component.html',
  styleUrls: ['./home.component.scss']
})
export class HomeComponent implements OnInit {


    constructor( private sharedService: SharedService) {}
    
    ngOnInit(): void {

    }

    _setDataSource(indexNumber:number) {
      setTimeout(() => {
        switch (indexNumber) {
          case 0:
            break;
          case 1:
              this.sharedService.setrReadyIndex(true);
            break;
        }
      });
    }
  
}