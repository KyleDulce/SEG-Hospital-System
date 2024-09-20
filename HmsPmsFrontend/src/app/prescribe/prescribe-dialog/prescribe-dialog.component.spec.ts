import { ComponentFixture, TestBed } from '@angular/core/testing';

import { PrescribeDialogComponent } from './prescribe-dialog.component';

describe('PrescribeDialogComponent', () => {
  let component: PrescribeDialogComponent;
  let fixture: ComponentFixture<PrescribeDialogComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [PrescribeDialogComponent]
    });
    fixture = TestBed.createComponent(PrescribeDialogComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
