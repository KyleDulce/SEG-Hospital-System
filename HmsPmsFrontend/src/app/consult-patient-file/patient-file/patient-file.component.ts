import { Component } from '@angular/core';
import { MatDialog } from '@angular/material/dialog';
import { ModifyInfectionDialogComponent } from '../modify-infection-dialog/modify-infection-dialog.component';

@Component({
  selector: 'app-patient-file',
  templateUrl: './patient-file.component.html',
  styleUrls: ['./patient-file.component.css'],
})
export class PatientFileComponent {
  constructor(private matDialog: MatDialog) {}

  public tempDialogOpen() {
    //FIXME: remove me
    this.matDialog.open(ModifyInfectionDialogComponent);
  }
}
