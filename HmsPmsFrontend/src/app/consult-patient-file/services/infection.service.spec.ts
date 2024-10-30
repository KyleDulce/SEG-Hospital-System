import { TestBed } from '@angular/core/testing';

import { InfectionService } from './infection.service';

describe('InfectionService', () => {
  let service: InfectionService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(InfectionService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
