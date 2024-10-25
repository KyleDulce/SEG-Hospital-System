import { ComponentFixture, TestBed } from '@angular/core/testing';

import { ModifyInfectionDialogComponent } from './modify-infection-dialog.component';

describe('ModifyInfectionDialogComponent', () => {
  let component: ModifyInfectionDialogComponent;
  let fixture: ComponentFixture<ModifyInfectionDialogComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [ModifyInfectionDialogComponent]
    });
    fixture = TestBed.createComponent(ModifyInfectionDialogComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
