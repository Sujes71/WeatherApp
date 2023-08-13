import { Component, OnInit, ViewChild } from '@angular/core';

import { DatePipe } from '@angular/common';
import { FormControl } from '@angular/forms';
import { MatDialog } from '@angular/material/dialog';
import { MatPaginator } from '@angular/material/paginator';
import { MatSort } from '@angular/material/sort';
import { MatTableDataSource } from '@angular/material/table';
import { SharedService } from 'src/app/core/shared/services/shared.service';
import { TableService } from '../../services/table.service';
import { ConfirmDialogComponent } from '../confirm-dialog/confirm-dialog.component';
   
@Component({
  selector: 'app-table',
  templateUrl: './table.component.html',
  styleUrls: ['./table.component.scss']
})
export class TableComponent implements OnInit {
  forecasts:Element[] = [];
  dataSource = new MatTableDataSource<Element>(this.forecasts);
  displayedColumns: string[] = ['municipality', 'date', 'unit', 'avg', 'precipitation_avg', 'borrar'];
  filterControl = new FormControl('');
  @ViewChild(MatPaginator) paginator!: MatPaginator;
  @ViewChild(MatSort) sort!: MatSort;
  @ViewChild(MatSort) order!: MatSort;

  length!:number;
  pageSize = 5;
  pageSizeOptions: number[] = [5, 10, 15, 20];
  
    constructor(private tableService: TableService, private sharedService: SharedService, private datePipe: DatePipe, private dialog: MatDialog) {}
    
    ngOnInit(): void {
      this.sharedService.readyIndex$.subscribe({
        next: (flag) => {
          if (flag) {
            this.getAllForecast();
          }
        },
        error: (error) => { console.log(error); }
      });
      this.filterControl.valueChanges.subscribe({
        next: (value) => {
          if(value)
            this.dataSource.filter = value.trim().toLowerCase();
        },
        error: (error) => { console.log(error); }
      })
    }
    
    getAllForecast(){
      this.tableService.getAllForecast().subscribe({
        next: (response) => {
          this.forecasts = response;
          this.dataSource = new MatTableDataSource<Element>(this.forecasts);
          this.dataSource.paginator = this.paginator;
          this.length = this.forecasts.length;
          this.dataSource.sort = this.sort;
        },
        error: (error) => {
          console.log(error);
        }
      });
    }

    applyFilter(event: any) {
      const filterValue = event.target.value;
      this.dataSource.filter = filterValue.trim().toLowerCase();
    }

    deleteRow(row: Element) {
      const dialogRef = this.dialog.open(ConfirmDialogComponent, {
        data: {
          title: 'Confirmación',
          message: '¿Estás seguro de que deseas eliminar este elemento?',
        },
      });
    
      dialogRef.afterClosed().subscribe((result) => {
        if (result) {
          const index = this.dataSource.data.indexOf(row);
          if (index > -1) {
            this.dataSource.data.splice(index, 1);
            this.dataSource._updateChangeSubscription();
            console.log(row);
            this.tableService.deleteForecast(row.id).subscribe({
              next: (response) => {
                console.log(response);
              },
              error: (error) => {
                console.log(error);
              },
            });
          }
        }
      });   
  }
}