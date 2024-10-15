import { Component, Inject } from '@angular/core';
import { FormControl, FormGroup, Validators } from '@angular/forms';
import { MAT_DIALOG_DATA, MatDialogRef } from '@angular/material/dialog';

@Component({
  selector: 'app-modify-infection-dialog',
  templateUrl: './modify-infection-dialog.component.html',
  styleUrls: ['./modify-infection-dialog.component.css'],
})
export class ModifyInfectionDialogComponent {
  protected formGroup = new FormGroup({
    infectionName: new FormControl<string>('', [Validators.required]),
    startDate: new FormControl<Date | null>(null, [Validators.required]),
    endDate: new FormControl<Date | null>(null),
  });

  constructor(
    @Inject(MAT_DIALOG_DATA) public data: any,
    private dialogRef: MatDialogRef<ModifyInfectionDialogComponent>
  ) {}

  public cancel() {
    this.dialogRef.close();
  }

  public okay() {
    //TODO add me
  }
}
