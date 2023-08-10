import { HttpClientModule } from "@angular/common/http";
import { NgModule } from "@angular/core";
import { RouterModule } from "@angular/router";
import { CommonModule } from "@angular/common";
import { HTTP_INTERCEPTORS } from '@angular/common/http';

import { NotFoundComponent } from "./components/not-found/not-found.component";
import { JwtInterceptor } from '../shared/jwt.interceptor';
import { MatAutocompleteModule } from "@angular/material/autocomplete";
import { MatInputModule } from "@angular/material/input";
import { MatFormFieldModule } from "@angular/material/form-field";
import { MatIconModule } from "@angular/material/icon";
import { ReactiveFormsModule } from "@angular/forms";
import { MatSelectModule } from "@angular/material/select";

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
    ],
    providers: [
        { provide: HTTP_INTERCEPTORS, useClass: JwtInterceptor, multi: true }
    ]
})

export class SharedModule {
    constructor(){}
}