import { NgModule } from "@angular/core";
import { SharedModule } from "../core/shared/shared.module";
import { publicRoutingModule } from "./public-routing.module";
import { PublicComponent } from "./public.component";
import { HomeComponent } from "./home/containers/home.component";
import { LoginComponent } from "./login/containers/login.component";
import { SaveButtonComponent } from "./home/components/save-button/save-button.component";
import { InputAutocompleteComponent } from "./home/components/input-autocomplete/input-autocomplete.component";
import { InputAutocompleteService } from "./home/services/input-autocomplete.service";
import { SelectComponent } from "./home/components/select/select.component";
import { InfoDisplayComponent } from "./home/components/info-display/info-display.component";
import { BackConnectionComponent } from "../core/shared/components/back-connection/back-conection.component";
import { SaveButtonService } from "./home/services/save-button.service";
import { SelectService } from "./home/services/select.service";

@NgModule({
    imports: [
        publicRoutingModule,
        SharedModule,
    ],
    declarations: [
        PublicComponent,
        HomeComponent,
        LoginComponent,
        SaveButtonComponent,
        InputAutocompleteComponent,
        SelectComponent,
        InfoDisplayComponent,
        BackConnectionComponent,
    ],
    exports: [],
    providers: [
        SaveButtonService, 
        InputAutocompleteService, 
        SelectService
    ]
})

export class PublicModule {
    constructor(){}
}