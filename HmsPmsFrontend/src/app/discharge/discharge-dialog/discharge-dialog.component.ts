import { Component, Inject, OnInit } from '@angular/core';
import { AdmissionInfoResponse, emptyAdmissionInfoResponse } from 'src/app/shared/model/admission.model';
import { DischargeService } from '../discharge.service';
import { MAT_DIALOG_DATA, MatDialogRef } from '@angular/material/dialog';
import { DischargeDialogOpenData, DischargeDialogResult } from 'src/app/shared/model/patient-file.model';
import { MatSnackBar } from '@angular/material/snack-bar';
import { environment } from 'src/environments/environment';

@Component({
  selector: 'app-discharge-dialog',
  templateUrl: './discharge-dialog.component.html',
  styleUrls: ['./discharge-dialog.component.css']
})
export class DischargeDialogComponent implements OnInit {

  public reason: string = "No Reason";
  public data: AdmissionInfoResponse = emptyAdmissionInfoResponse;

  constructor(
    @Inject(MAT_DIALOG_DATA) public dialogData: DischargeDialogOpenData,
    private dialogRef: MatDialogRef<DischargeDialogComponent>,
    private dischargeService: DischargeService,
    private snackbar: MatSnackBar,) {}

  public get formattedDate(): string {
    if(this.data.dischargeDate > 0) {
      const dateobj = new Date(this.data.dischargeDate)
      return dateobj.toDateString();
    } else {
      return '';
    }
  }

  ngOnInit(): void {
    this.dischargeService.getAdmissionInfo(this.dialogData.patientId)
      .subscribe({
        next: response => {
          this.data = response;
        },
        error: (error: Error) => {
          this.snackbar.open("Something went wrong", 'Close', {
            duration: environment.snackbarDurationSeconds * 1000
          });
          console.error(error);
          this.dialogRef.close(DischargeDialogResult.CANCEL);
        }
      })
  }

  public cancel(): void {
    this.dialogRef.close(DischargeDialogResult.CANCEL);
  }

  public confirm(): void {
    if(!this.reason) {
      this.reason = "No Reason"
    }

    this.dischargeService.postDischarge({
      divisionId: this.data.divisionId,
      patientId: this.data.patientId,
      reasonForDischarge: this.reason
    })
    .subscribe({
      next: () => {
        this.snackbar.open("Patient Discharged", undefined, {
          duration: environment.snackbarDurationSeconds * 1000
        });
        this.dialogRef.close(DischargeDialogResult.RELOAD);
      }, 
      error: (error: Error) => {
        this.snackbar.open("Something went wrong", 'Close', {
          duration: environment.snackbarDurationSeconds * 1000
        });
        console.error(error);
        this.dialogRef.close(DischargeDialogResult.CANCEL);
      }
    })
  }
}
