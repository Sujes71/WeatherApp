import { NgModule } from "@angular/core";
import { SharedModule } from "../core/shared/shared.module";
import { publicRoutingModule } from "./public-routing.module";
import { PublicComponent } from "./public.component";
import { HomeComponent } from "./home/containers/home.component";
import { SaveButtonComponent } from "./home/components/save-button/save-button.component";
import { InputAutocompleteComponent } from "./home/components/input-autocomplete/input-autocomplete.component";
import { InputAutocompleteService } from "./home/services/input-autocomplete.service";
import { SelectComponent } from "./home/components/select/select.component";
import { InfoDisplayComponent } from "./home/components/info-display/info-display.component";
import { BackConnectionComponent } from "../core/shared/components/back-connection/back-conection.component";
import { SaveButtonService } from "./home/services/save-button.service";
import { SelectService } from "./home/services/select.service";
import { TableComponent } from "./home/components/table/table.component";
import { TableService } from "./home/services/table.service";
import { ConfirmDialogComponent } from "./home/components/confirm-dialog/confirm-dialog.component";

@NgModule({
    imports: [
        publicRoutingModule,
        SharedModule,
    ],
    declarations: [
        PublicComponent,
        HomeComponent,
        SaveButtonComponent,
        InputAutocompleteComponent,
        SelectComponent,
        InfoDisplayComponent,
        BackConnectionComponent,
        TableComponent,
        ConfirmDialogComponent,
    ],
    exports: [],
    providers: [
        SaveButtonService, 
        InputAutocompleteService, 
        SelectService,
        TableService,
    ]
})

export class PublicModule {
    constructor(){}
}