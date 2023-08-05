import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';
import { TestBed } from '@angular/core/testing';
import { Municipality } from '../models/municipality';
import { Report } from '../models/report';
import { WeatherReportService } from '../services/report/weatherReport.service';

describe('WeatherReportService', () => {
  let service: WeatherReportService;
  let httpMock: HttpTestingController;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule],
      providers: [WeatherReportService],
    });
    service = TestBed.inject(WeatherReportService);
    httpMock = TestBed.inject(HttpTestingController);
  });

  afterEach(() => {
    httpMock.verify();
  });

  it('should call getMunPredictionTomorrow, getMunPredictionTomorrow()', () => {
    const dummyReport: Report = {
        id: "id44001",
        name: "Ababuj",
        date: new Date("2023-08-05T00:00:00.000+00:00"),
        temAvg: 16.5,
        temUnit: "G_CEL",
        probPrecipitations: [
            { value: 0, period: "00-06" },
            { value: 0, period: "06-12" },
            { value: 0, period: "12-18" },
            { value: 0, period: "18-24" }
          ]
    };
    const munId = "id44001";

    service.getMunPredictionTomorrow(munId).subscribe((report) => {
      expect(report).toEqual(dummyReport);
    });

    const req = httpMock.expectOne(`api/aemet/mun/prediction/tomorrow/${munId}`);
    expect(req.request.method).toBe('GET');
    req.flush(dummyReport);
  });

  it('should call getMuns, getMuns()', () => {
    const dummyReport: Municipality[] = [
    {
      name: "Ababuj", id:"id44001"
    },
    {
        name: "Abades", id:"id40001"
    }
];

    service.getMuns().subscribe((report) => {
      expect(report).toEqual(dummyReport);
    });

    const req = httpMock.expectOne(`api/aemet/mun/all`);
    expect(req.request.method).toBe('GET');
    req.flush(dummyReport);
  });

  it('should call getToken and simulate response', () => {
    const dummyReport: string = 'testingCodeRandomForGenerateJWT';
  
    service.getToken(dummyReport).subscribe((report) => {
      expect(report).toBeDefined();
      expect(typeof report).toBe('string');
    });
  
    const req = httpMock.expectOne(`api/auth/token/${dummyReport}`);
    expect(req.request.method).toBe('GET');

    req.flush({ token: 'dummyTokenValue' });
  });
  

});