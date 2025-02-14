import { ComponentFixture, TestBed } from '@angular/core/testing';

import { DischargeDialogComponent } from './discharge-dialog.component';

describe('DischargeDialogComponent', () => {
  let component: DischargeDialogComponent;
  let fixture: ComponentFixture<DischargeDialogComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [DischargeDialogComponent]
    });
    fixture = TestBed.createComponent(DischargeDialogComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
