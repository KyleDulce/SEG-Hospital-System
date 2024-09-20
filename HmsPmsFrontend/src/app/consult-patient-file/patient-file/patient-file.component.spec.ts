import { ComponentFixture, TestBed } from '@angular/core/testing';

import { PatientFileComponent } from './patient-file.component';

describe('PatientFileComponent', () => {
  let component: PatientFileComponent;
  let fixture: ComponentFixture<PatientFileComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [PatientFileComponent]
    });
    fixture = TestBed.createComponent(PatientFileComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
