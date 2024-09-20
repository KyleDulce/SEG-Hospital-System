import { TestBed } from '@angular/core/testing';

import { RequestListService } from './request-list.service';

describe('RequestListService', () => {
  let service: RequestListService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(RequestListService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
