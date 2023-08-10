import { TestBed } from '@angular/core/testing';
import { Municipality } from '../core/models/municipality';
import { Forecast } from '../core/models/forecast';
import { PublicService } from './public.service';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';

describe('PublicService', () => {
  let service: PublicService;
  let httpMock: HttpTestingController;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule],
      providers: [PublicService],
    });
    service = TestBed.inject(PublicService);
    httpMock = TestBed.inject(HttpTestingController);
  });

  afterEach(() => {
    httpMock.verify();
  });

  it('should call getMunPredictionTomorrow, getMunPredictionTomorrow()', () => {
    const dummyForecast: Forecast = {
        id: "id44001",
        name: "Ababuj",
        date: new Date("2023-08-05T00:00:00.000+00:00"),
        avg: 16.5,
        unit: "G_CEL",
        probPrecipitations: [
            { value: 0, period: "00-06" },
            { value: 0, period: "06-12" },
            { value: 0, period: "12-18" },
            { value: 0, period: "18-24" }
          ]
    };
    const munId = "id44001";

    service.getMunTomorrowForecast(munId).subscribe((forecast) => {
      expect(forecast).toEqual(dummyForecast);
    });

    const req = httpMock.expectOne(`/api/aemet/mun/prediction/tomorrow/${munId}`);
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

  it('should call getToken and simulate response', () => {
    const dummyCode: string = 'testingCodeRandomForGenerateJWT';
  
    service.getToken(dummyCode).subscribe((code) => {
      expect(code).toBeDefined();
      expect(typeof code).toBe('string');
    });
  
    const req = httpMock.expectOne(`/api/auth/token/${dummyCode}`);
    expect(req.request.method).toBe('GET');

    req.flush({ token: 'dummyTokenValue' });
  });
  

});