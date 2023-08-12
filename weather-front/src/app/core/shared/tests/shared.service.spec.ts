import { TestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';
import { SharedService } from '../services/shared.service';

describe('SharedService', () => {
  let service: SharedService;
  let httpMock: HttpTestingController;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule],
      providers: [SharedService],
    });
    service = TestBed.inject(SharedService);
    httpMock = TestBed.inject(HttpTestingController);
  });

  afterEach(() => {
    httpMock.verify();
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