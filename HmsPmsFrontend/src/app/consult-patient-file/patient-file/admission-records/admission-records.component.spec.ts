import { ComponentFixture, TestBed } from '@angular/core/testing';

import { AdmissionRecordsComponent } from './admission-records.component';

describe('AdmissionRecordsComponent', () => {
  let component: AdmissionRecordsComponent;
  let fixture: ComponentFixture<AdmissionRecordsComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [AdmissionRecordsComponent]
    });
    fixture = TestBed.createComponent(AdmissionRecordsComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
