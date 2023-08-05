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
import { Temperature } from 'src/app/models/temperature';
import { WeatherReportComponent } from "../components/report/weatherReport.component";
import { WeatherReportService } from "../services/report/weatherReport.service";

const dataConversion ={
    "avg": 68.0,
    "unit": "G_FAH"
}

const mockedWeatherReportService: {
    getConversion: () => Observable<Temperature>;
} = {
    getConversion: () => of(dataConversion),
};

describe("WeatherReportComponent", () => {
    beforeEach(async () => {
        await TestBed.configureTestingModule({
            declarations: [WeatherReportComponent],
            imports: 
            [
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
            providers: 
            [
                { provide: WeatherReportService, useValue:mockedWeatherReportService }
            ]
        }).compileComponents();
    });

    let fixture: ComponentFixture<WeatherReportComponent>;
    let weatherReportComponent: WeatherReportComponent;
    
    beforeEach( () => {
        fixture = TestBed.createComponent(WeatherReportComponent);
        weatherReportComponent = fixture.componentInstance;
        fixture.detectChanges();
    });

    it('should create the app', () => {
        expect(weatherReportComponent).toBeTruthy();
    });

    it('should call getConversion, getConversion()', fakeAsync(() => {
        const getConversionSpy = spyOn(mockedWeatherReportService, 'getConversion').and.returnValue(of(dataConversion));
        weatherReportComponent.getConversion(20, 'G_FAH');
        tick();

        expect(getConversionSpy).toHaveBeenCalled();

        fixture.detectChanges();
        expect(weatherReportComponent.reportJSON).toBeDefined();
        expect(weatherReportComponent.reportJSON.temAvg).toEqual(dataConversion.avg);

        getConversionSpy.and.returnValue(throwError (() => {'server error'}));
        weatherReportComponent.reportJSON.temAvg = null;
        weatherReportComponent.getConversion(20, 'G_FAH');
        tick();

        expect(getConversionSpy).toHaveBeenCalled();

        fixture.detectChanges();
        expect(weatherReportComponent.reportJSON.temAvg).toBeFalsy();
    }));
});