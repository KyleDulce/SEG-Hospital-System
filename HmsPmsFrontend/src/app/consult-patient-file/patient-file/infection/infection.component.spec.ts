import { ComponentFixture, TestBed } from '@angular/core/testing';

import { InfectionComponent } from './infection.component';

describe('InfectionComponent', () => {
  let component: InfectionComponent;
  let fixture: ComponentFixture<InfectionComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [InfectionComponent]
    });
    fixture = TestBed.createComponent(InfectionComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
