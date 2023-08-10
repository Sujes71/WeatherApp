import { HttpClientModule } from "@angular/common/http";
import { ComponentFixture, TestBed, fakeAsync, tick } from "@angular/core/testing";
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { MatAutocompleteModule } from '@angular/material/autocomplete';
import { MatFormFieldModule } from '@angular/material/form-field';
import { MatIconModule } from '@angular/material/icon';
import { MatInputModule } from '@angular/material/input';
import { MatSelectModule } from '@angular/material/select';
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';
import { Observable, of, throwError } from 'rxjs';
import { Temperature } from 'src/app/core/models/temperature';
import { HomeComponent } from "./home.component";
import { PublicService } from "../../public.service";

const dataConversion = {
    "avg": 68.0,
    "unit": "G_FAH"
}


const mockedPublicService: {
    getConversion: () => Observable<Temperature>,
    getRandomCode: () => Observable<String>

} = {
    getConversion: () => of(dataConversion),
    getRandomCode: () => of("anyString"),
};

describe("HomeComponent", () => {
    beforeEach( async () => {
        await TestBed.configureTestingModule({
            declarations: [HomeComponent],
            imports: [
                HttpClientModule,
                MatAutocompleteModule,
                MatInputModule,
                MatFormFieldModule,
                MatSelectModule,
                MatIconModule,
                FormsModule,
                ReactiveFormsModule,
                BrowserAnimationsModule
            ],
            providers: [
                { provide: PublicService, useValue: mockedPublicService }
            ]
        }).compileComponents();
    });

    let fixture: ComponentFixture<HomeComponent>;
    let homeComponent: HomeComponent;

    beforeEach( () => {
        fixture = TestBed.createComponent(HomeComponent);
        homeComponent = fixture.componentInstance;
        fixture.detectChanges();
    });

    it('should create the app', () => {
        expect(homeComponent).toBeTruthy();
    });

    it('should call getConversion, getConversion()', fakeAsync(() => {
        const getConversionSpy = spyOn(mockedPublicService, 'getConversion').and.returnValue(of(dataConversion));
        homeComponent.getConversion(20, 'G_FAH');
        tick();

        expect(getConversionSpy).toHaveBeenCalled();

        fixture.detectChanges();
        expect(homeComponent.forecast).toBeDefined();
        expect(homeComponent.forecast.avg).toEqual(dataConversion.avg);

        getConversionSpy.and.returnValue(throwError(() => 'server error'));
        homeComponent.forecast.avg = null;
        homeComponent.getConversion(20, 'G_FAH');
        tick();

        expect(getConversionSpy).toHaveBeenCalled();

        fixture.detectChanges();
        expect(homeComponent.forecast.avg).toBeFalsy();
    }));
});
