import { TestBed } from '@angular/core/testing';
import { Municipality } from '../../../core/models/municipality';
import { Forecast } from '../../../core/models/forecast';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';
import { InputAutocompleteService } from '../services/input-autocomplete.service';

describe('InputAutocompleteService', () => {
  let service: InputAutocompleteService;
  let httpMock: HttpTestingController;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule],
      providers: [InputAutocompleteService],
    });
    service = TestBed.inject(InputAutocompleteService);
    httpMock = TestBed.inject(HttpTestingController);
  });

  afterEach(() => {
    httpMock.verify();
  });

  it('should call getMunPredictionTomorrow, getMunPredictionTomorrow()', () => {
    const dummyMunicipaliy = {
      name: "Ababuj",
      id: "id44001"
    }
    const dummyTemperature = {
      avg: 16.5,
      unit: "G_CEL"
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

    service.getMunTomorrowForecast(dummyMunicipaliy.id).subscribe((forecast) => {
      expect(forecast).toEqual(dummyForecast);
    });

    const req = httpMock.expectOne(`/api/aemet/mun/prediction/tomorrow/${dummyMunicipaliy.id}`);
    expect(req.request.method).toBe('GET');
    req.flush(dummyForecast);
  });

  it('should call getMuns, getMuns()', () => {
    const dummyMunicipalities: Municipality[] = [
    {
      name: "Ababuj", id:"id44001"
    },
    {
        name: "Abades", id:"id40001"
    }
];

    service.getMunicipalities().subscribe((mun) => {
      expect(mun).toEqual(dummyMunicipalities);
    });

    const req = httpMock.expectOne(`/api/aemet/mun/all`);
    expect(req.request.method).toBe('GET');
    req.flush(dummyMunicipalities);
  });
  

});