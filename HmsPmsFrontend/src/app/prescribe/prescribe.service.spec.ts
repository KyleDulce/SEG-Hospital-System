import { TestBed } from '@angular/core/testing';

import { PrescribeService } from './prescribe.service';

describe('PrescribeService', () => {
  let service: PrescribeService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(PrescribeService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
