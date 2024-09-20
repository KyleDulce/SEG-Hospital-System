import { Component, Inject, OnInit } from '@angular/core';
import { MAT_DIALOG_DATA, MatDialog, MatDialogRef } from '@angular/material/dialog';
import { FormControl, FormGroup, Validators } from '@angular/forms';
import { AdmitPatientService } from '../admit-patient.service';
import {RequestAdmissionDialogOpenData, RequestAdmissionDialogResult } from 'src/app/shared/model/admission.model';
import { RequestPatientAdmissionComponent } from '../request-patient-admission/request-patient-admission.component';
import { Router } from '@angular/router';
import { AdmitPatientDialogOpenData, AdmitPatientDialogResult, AdmitPatientRequest, RejectPatientAdmissionRequest } from 'src/app/shared/model/admission.model';
import { GetDivisionResponse, emptyGetDivisionResponse } from 'src/app/shared/model/visualize-divisions.models';
import { MatSnackBar } from '@angular/material/snack-bar';
import { environment } from 'src/environments/environment';
import { Observable } from 'rxjs';

@Component({
  selector: 'app-admit-patient-dialog',
  templateUrl: './admit-patient-dialog.component.html',
  styleUrls: ['./admit-patient-dialog.component.css']
})
export class AdmitPatientDialogComponent implements OnInit {

  admitPatientForm = new FormGroup({
    insuranceNum: new FormControl('', {validators: [Validators.required, Validators.min(0)]}),
    localDoctorId: new FormControl('', {validators: [Validators.required]}),
    roomNum: new FormControl('', {validators: [Validators.required, Validators.min(0)]}),
    bedNum: new FormControl('', {validators: [Validators.required, Validators.min(0)]}),
  });
  
  displayedColumns: Array<string> = ['roomNum', 'bedNum', 'occupancy'];

  divisionInformation: GetDivisionResponse = emptyGetDivisionResponse //empty object to avoid error

  constructor(@Inject(MAT_DIALOG_DATA) public data: AdmitPatientDialogOpenData,
    private dialogRef: MatDialogRef<AdmitPatientDialogComponent>,
    private admitPatientService: AdmitPatientService,
    private snackbar: MatSnackBar,
    private router: Router,
    private dialog: MatDialog,) {}

  get divisionComplete(): boolean {
    return this.divisionInformation.division.status === 'complete'
  }

  ngOnInit(): void {
    this.admitPatientService.getDivisionInformation()
      .subscribe({
        next: response => this.divisionInformation = response,
        error: (error: Error) => {
          this.snackbar.open(error.message, undefined, {
            duration: environment.snackbarDurationSeconds * 1000
          });
          console.error(error);
          this.dialogRef.close(AdmitPatientDialogResult.CANCEL);
        }
      });
  }

  cancel(): void {
    this.dialogRef.close(AdmitPatientDialogResult.CANCEL);
  }

  request(): void {
    this.dialogRef.close(AdmitPatientDialogResult.GO_REQUEST);
   
    const dialogOptions: RequestAdmissionDialogOpenData = {
      patientId: this.data.patientId
    }
    this.dialog.open(RequestPatientAdmissionComponent, {
      data: dialogOptions,
      disableClose: true
    }).afterClosed().subscribe(result => {
      if(result === RequestAdmissionDialogResult.RELOAD) {
        //reload page
        this.router.navigate(['/'], {
          skipLocationChange: true
        }).then(() => this.router.navigate(['/patient-file/' +  this.data.patientId]));
      } else if(result === RequestAdmissionDialogResult.GO_REQUEST) {
        // TODO Go to request page 
      }
    })
  }
  

  submit(): void {
    if(!this.admitPatientForm.valid) {
      this.admitPatientForm.markAllAsTouched();
      return;
    }

    let requestObservable: Observable<void>;
    const requestData: AdmitPatientRequest = {
      patientId: this.data.patientId,
      divisionId: this.divisionInformation.division.identifier,
      privateInsuranceNumber: +this.admitPatientForm.controls['insuranceNum'].value!,
      localDoctorId: this.admitPatientForm.controls['localDoctorId'].value!,
      roomNum: +this.admitPatientForm.controls['roomNum'].value!,
      bedNum: +this.admitPatientForm.controls['bedNum'].value!,
    }

    if(!this.data.isFromRequestList) {
      requestObservable = this.admitPatientService.postAdmitPatient(requestData);
    } else {
      requestObservable = this.admitPatientService.postAdmitFromRequestList(requestData);
    }

    requestObservable.subscribe({
      next: () => {
        this.snackbar.open("Patient Admitted", undefined, {
          duration: environment.snackbarDurationSeconds * 1000
        });
        this.dialogRef.close(AdmitPatientDialogResult.RELOAD);
      },
      error: (error: Error) => {
        this.snackbar.open("Invalid Request!", undefined, {
          duration: environment.snackbarDurationSeconds * 1000
        });
      }
    })
  }
  



  reject(): void {
    const rejectData: RejectPatientAdmissionRequest = {
      divisionId: this.divisionInformation.division.identifier,
      patientId: this.data.patientId
    }

    this.admitPatientService.postRejectFromRequestList(rejectData)
      .subscribe({
        next: () => {
          this.snackbar.open("Patient Rejected", undefined, {
            duration: environment.snackbarDurationSeconds * 1000
          });
          this.dialogRef.close(AdmitPatientDialogResult.RELOAD);
        },
        error: (error: Error) => {
          this.snackbar.open("Invalid Request!", undefined, {
            duration: environment.snackbarDurationSeconds * 1000
          });
        }
      })
  }
}

