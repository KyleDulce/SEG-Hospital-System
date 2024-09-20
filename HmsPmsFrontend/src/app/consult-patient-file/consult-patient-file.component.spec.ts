import { ComponentFixture, TestBed } from '@angular/core/testing';

import { ConsultPatientFileComponent } from './consult-patient-file.component';

describe('ConsultPatientFileComponent', () => {
  let component: ConsultPatientFileComponent;
  let fixture: ComponentFixture<ConsultPatientFileComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [ConsultPatientFileComponent]
    });
    fixture = TestBed.createComponent(ConsultPatientFileComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
