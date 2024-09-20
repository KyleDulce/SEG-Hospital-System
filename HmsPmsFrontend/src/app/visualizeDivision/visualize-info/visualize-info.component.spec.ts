import { ComponentFixture, TestBed } from '@angular/core/testing';

import { VisualizeInfoComponent } from './visualize-info.component';

describe('VisualizeInfoComponent', () => {
  let component: VisualizeInfoComponent;
  let fixture: ComponentFixture<VisualizeInfoComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [VisualizeInfoComponent]
    });
    fixture = TestBed.createComponent(VisualizeInfoComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
