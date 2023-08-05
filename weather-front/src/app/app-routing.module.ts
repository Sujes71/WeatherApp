import { NgModule } from '@angular/core';
import { MatAutocompleteModule } from '@angular/material/autocomplete';
import { MatFormFieldModule } from '@angular/material/form-field';
import { MatIconModule } from '@angular/material/icon';
import { MatInputModule } from '@angular/material/input';
import { MatSelectModule } from '@angular/material/select';
import { RouterModule } from '@angular/router';


@NgModule({
  imports: [
    MatAutocompleteModule, 
    MatInputModule, 
    MatFormFieldModule, 
    MatSelectModule, 
    MatIconModule
  ],
  exports: [RouterModule]
})
export class AppRoutingModule { }