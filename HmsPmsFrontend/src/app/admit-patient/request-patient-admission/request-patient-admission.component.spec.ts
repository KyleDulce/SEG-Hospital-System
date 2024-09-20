import { ComponentFixture, TestBed } from '@angular/core/testing';

import { RequestPatientAdmissionComponent } from './request-patient-admission.component';

describe('RequestPatientAdmissionComponent', () => {
  let component: RequestPatientAdmissionComponent;
  let fixture: ComponentFixture<RequestPatientAdmissionComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [RequestPatientAdmissionComponent]
    });
    fixture = TestBed.createComponent(RequestPatientAdmissionComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
