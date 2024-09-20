import { TestBed } from '@angular/core/testing';

import { AdmitPatientService } from './admit-patient.service';

describe('AdmitPatientService', () => {
  let service: AdmitPatientService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(AdmitPatientService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
