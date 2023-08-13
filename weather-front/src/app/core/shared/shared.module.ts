import { CommonModule, DatePipe } from "@angular/common";
import { HTTP_INTERCEPTORS, HttpClientModule } from "@angular/common/http";
import { NgModule } from "@angular/core";
import { RouterModule } from "@angular/router";

import { ReactiveFormsModule } from "@angular/forms";
import { MatAutocompleteModule } from "@angular/material/autocomplete";
import { MatButtonModule } from '@angular/material/button';
import { MatDialogModule } from "@angular/material/dialog";
import { MatFormFieldModule } from "@angular/material/form-field";
import { MatIconModule } from "@angular/material/icon";
import { MatInputModule } from "@angular/material/input";
import { MatPaginatorModule } from '@angular/material/paginator';
import { MatSelectModule } from "@angular/material/select";
import { MatSortModule } from "@angular/material/sort";
import { MatTableModule } from "@angular/material/table";
import { MatTabsModule } from '@angular/material/tabs';
import { DataTablesModule } from "angular-datatables";
import { JwtInterceptor } from '../shared/interceptor/token-session.interceptor';
import { NotFoundComponent } from "./components/not-found/not-found.component";
import { SharedService } from "./services/shared.service";

@NgModule({
    imports: [
        HttpClientModule,
        RouterModule,
        CommonModule,
        MatAutocompleteModule,
        MatInputModule,
        MatFormFieldModule,
        MatIconModule,
        ReactiveFormsModule,
        MatSelectModule,
        MatTabsModule,
        MatButtonModule,
        DataTablesModule,
        MatTableModule,
        MatPaginatorModule,
        MatSortModule,
        MatDialogModule
    ],
    declarations: [
        NotFoundComponent
    ],
    exports: [
        HttpClientModule,
        NotFoundComponent,
        RouterModule,
        CommonModule,
        MatAutocompleteModule,
        MatInputModule,
        MatFormFieldModule,
        MatIconModule,
        ReactiveFormsModule,
        MatSelectModule,
        MatTabsModule,
        MatButtonModule,
        DataTablesModule,
        MatTableModule,
        MatPaginatorModule,
        MatSortModule,
        MatDialogModule,
    ],
    providers: [
        { provide: HTTP_INTERCEPTORS, useClass: JwtInterceptor, multi: true },
        SharedService, DatePipe
    ]
})

export class SharedModule {
    constructor(){}
}