import { ComponentFixture, TestBed } from '@angular/core/testing';

import { ModifyLocationHistoryDialogComponent } from './modify-location-history-dialog.component';

describe('ModifyLocationHistoryDialogComponent', () => {
  let component: ModifyLocationHistoryDialogComponent;
  let fixture: ComponentFixture<ModifyLocationHistoryDialogComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [ModifyLocationHistoryDialogComponent]
    });
    fixture = TestBed.createComponent(ModifyLocationHistoryDialogComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
