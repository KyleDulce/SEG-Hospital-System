import { ComponentFixture, TestBed } from '@angular/core/testing';

import { SearchPatientComponent } from './search-patient.component';

describe('SearchPatientComponent', () => {
  let component: SearchPatientComponent;
  let fixture: ComponentFixture<SearchPatientComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [SearchPatientComponent]
    });
    fixture = TestBed.createComponent(SearchPatientComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
