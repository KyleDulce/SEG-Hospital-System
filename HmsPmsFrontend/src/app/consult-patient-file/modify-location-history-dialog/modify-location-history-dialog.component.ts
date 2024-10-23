import { Component, Inject } from '@angular/core';
import { FormControl, FormGroup, Validators } from '@angular/forms';
import { MAT_DIALOG_DATA, MatDialogRef } from '@angular/material/dialog';

@Component({
  selector: 'app-modify-location-history-dialog',
  templateUrl: './modify-location-history-dialog.component.html',
  styleUrls: ['./modify-location-history-dialog.component.css'],
})
export class ModifyLocationHistoryDialogComponent {
  protected formGroup = new FormGroup({
    streetNum: new FormControl<string | null>(null, [Validators.required]),
    aptNum: new FormControl<string | null>(null),
    streetName: new FormControl<string | null>(null, [Validators.required]),
    city: new FormControl<string | null>(null, [Validators.required]),
    postalCode: new FormControl<string | null>(null, [Validators.required]),
    province: new FormControl<string | null>(null, [Validators.required]),
    country: new FormControl<string | null>(null, [Validators.required]),
    startDate: new FormControl<Date | null>(null, [Validators.required]),
    endDate: new FormControl<Date | null>(null, [Validators.required]),
  });

  constructor(
    @Inject(MAT_DIALOG_DATA) public data: any,
    private dialogRef: MatDialogRef<ModifyLocationHistoryDialogComponent>
  ) {}

  public cancel() {
    this.dialogRef.close();
  }

  public okay() {
    //TODO add me
  }
}
