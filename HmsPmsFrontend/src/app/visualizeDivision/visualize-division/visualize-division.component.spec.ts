import { ComponentFixture, TestBed } from '@angular/core/testing';

import { VisualizeDivisionComponent } from './visualize-division.component';

describe('VisualizeDivisionComponent', () => {
  let component: VisualizeDivisionComponent;
  let fixture: ComponentFixture<VisualizeDivisionComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [VisualizeDivisionComponent]
    });
    fixture = TestBed.createComponent(VisualizeDivisionComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
