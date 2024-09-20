import { Component, Inject } from '@angular/core';
import { FormControl, FormGroup, Validators } from '@angular/forms';
import { MAT_DIALOG_DATA, MatDialogRef } from '@angular/material/dialog';
import { MatSnackBar } from '@angular/material/snack-bar';
import { PrescribeDialogResult, PrescribeMedDialogOpenOptions, PrescribeMedicationRequest } from 'src/app/shared/model/patient-file.model';
import { PrescribeService } from '../prescribe.service';
import { environment } from 'src/environments/environment';

@Component({
  selector: 'app-prescribe-dialog',
  templateUrl: './prescribe-dialog.component.html',
  styleUrls: ['./prescribe-dialog.component.css']
})
export class PrescribeDialogComponent {

  public prescribeForm = new FormGroup({
    medId: new FormControl<number | null>(null, [Validators.required]),
    name: new FormControl<string>('', [Validators.required]),
    methodOfAdmin: new FormControl<string>('', [Validators.required]),
    unitpd: new FormControl<number | null>(null, [Validators.required]),
    adminpd: new FormControl<number | null>(null, [Validators.required]),
    start: new FormControl<Date | null>(null, [Validators.required]),
    end: new FormControl<Date | null>(null, [Validators.required]),
  })

  constructor(@Inject(MAT_DIALOG_DATA) public data: PrescribeMedDialogOpenOptions,
    private dialogRef: MatDialogRef<PrescribeDialogComponent>,
    private prescribeService: PrescribeService,
    private snackbar: MatSnackBar) {}

  public cancel(): void {
    this.dialogRef.close(PrescribeDialogResult.CANCEL)
  }

  public prescribe(): void {
    if(!this.prescribeForm.valid) {
      this.prescribeForm.markAllAsTouched()
      return;
    }
    const data: PrescribeMedicationRequest = {
      patientId: this.data.patientId,
      drugNumber: this.prescribeForm.controls['medId'].value!,
      drugName: this.prescribeForm.controls['name'].value!,
      methodOfAdmin: this.prescribeForm.controls['methodOfAdmin'].value!,
      numAdminPerDay: this.prescribeForm.controls['adminpd'].value!,
      unitsByDay: this.prescribeForm.controls['unitpd'].value!,
      startDate: this.prescribeForm.controls['start'].value!.getTime(),
      endDate: this.prescribeForm.controls['end'].value!.getTime(),
    }

    this.prescribeService.postPrescription(data)
    .subscribe({
      next: () => {
        this.snackbar.open("Prescription Added", undefined, {
          duration: environment.snackbarDurationSeconds * 1000
        });
        this.dialogRef.close(PrescribeDialogResult.RELOAD);
      },
      error: (error: any) => {
        let message:string;
        if(error.error) {
          message = error.error;
        } else {
          message = "unexpected error";
        }
        
        this.snackbar.open(message, undefined, {
          duration: environment.snackbarDurationSeconds * 1000
        });
        this.dialogRef.close(PrescribeDialogResult.CANCEL);
      }
    })
  }
}
