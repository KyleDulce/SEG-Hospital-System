import { TestBed } from '@angular/core/testing';

import { VisualizeDivisionService } from './visualize-division.service';

describe('VisualizeDivisionService', () => {
  let service: VisualizeDivisionService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(VisualizeDivisionService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
