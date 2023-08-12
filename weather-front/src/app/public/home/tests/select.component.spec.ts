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
import { SelectComponent } from "../components/select/select.component";
import { SelectService } from "../services/select.service";
import { SharedService } from "src/app/core/shared/services/shared.service";
import { Forecast } from "src/app/core/models/forecast";
import { response } from "express";

const dummyTemperature = {
    "avg": 68.0,
    "unit": "G_FAH"
}
const dummyMunicipaliy = {
    name: "Ababuj",
    id: "id44001"
  }
  const dummyForecast: Forecast = {
      municipality: dummyMunicipaliy,
      date: new Date("2023-08-05T00:00:00.000+00:00"),
      temperature: dummyTemperature,
      probPrecipitations: [
          { value: 0, period: "00-06" },
          { value: 0, period: "06-12" },
          { value: 0, period: "12-18" },
          { value: 0, period: "18-24" }
        ]
  };
  const dummyShowedTemp = "25ºC";

const mockedSelectService: {
    getConversion: () => Observable<Temperature>,

} = {
    getConversion: () => of(dummyTemperature),
};

const mockedSharedService: {

    forecastToSelect$: Observable<Forecast>,
    setForecastToDisplay: jasmine.Spy,
    setShowedTemp: jasmine.Spy,
} = {

    forecastToSelect$: of(dummyForecast),
    setForecastToDisplay: jasmine.createSpy('setForecastToDisplay'),
    setShowedTemp: jasmine.createSpy('setShowedTemp'),
  };

describe("SelectComponent", () => {
    beforeEach( async () => {
        await TestBed.configureTestingModule({
            declarations: [SelectComponent],
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
                { provide: SelectService, useValue: mockedSelectService },
                { provide: SharedService, useValue: mockedSharedService }
            ]
        }).compileComponents();
    });

    let fixture: ComponentFixture<SelectComponent>;
    let selectComponent: SelectComponent;

    beforeEach( () => {
        fixture = TestBed.createComponent(SelectComponent);
        selectComponent = fixture.componentInstance;
        fixture.detectChanges();
    });

    it('should create the app', () => {
        expect(selectComponent).toBeTruthy();
    });

    it('should call getConversion, getConversion()', fakeAsync(() => {
        const getConversionSpy = spyOn(mockedSelectService, 'getConversion').and.returnValue(of(dummyTemperature));

        selectComponent.getConversion(20, 'G_FAH');
        tick();

        expect(getConversionSpy).toHaveBeenCalled();

        fixture.detectChanges();
        expect(selectComponent.forecast).toBeDefined();
        expect(selectComponent.forecast.temperature.avg).toEqual(dummyTemperature.avg);

        getConversionSpy.and.returnValue(throwError(() => 'server error'));
        selectComponent.forecast.temperature.avg = null;
        selectComponent.getConversion(20, 'G_FAH');
        tick();

        expect(getConversionSpy).toHaveBeenCalled();

        fixture.detectChanges();
        expect(selectComponent.forecast.temperature.avg).toBeFalsy();
    }));
});
