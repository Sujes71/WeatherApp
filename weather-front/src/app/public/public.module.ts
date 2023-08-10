import { NgModule } from "@angular/core";
import { SharedModule } from "../core/shared/shared.module";
import { publicRoutingModule } from "./public-routing.module";
import { PublicComponent } from "./public.component";
import { HomeComponent } from "./home/containers/home.component";
import { LoginComponent } from "./home/login/containers/login.component";
import { PublicService } from "./public.service";

@NgModule({
    imports: [
        publicRoutingModule,
        SharedModule,
    ],
    declarations: [
        PublicComponent,
        HomeComponent,
        LoginComponent
    ],
    exports: [],
    providers: [ PublicService ]
})

export class PublicModule {
    constructor(){}
}