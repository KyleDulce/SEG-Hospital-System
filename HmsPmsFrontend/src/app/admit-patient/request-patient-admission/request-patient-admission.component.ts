
import { Component, Inject } from '@angular/core';
import { MAT_DIALOG_DATA, MatDialog, MatDialogRef } from '@angular/material/dialog';
import { FormControl, FormGroup, Validators } from '@angular/forms';
import { AdmitPatientService } from '../admit-patient.service';
import { AdmitPatientDialogOpenData, AdmitPatientDialogResult, RequestAdmissionDialogOpenData, RequestAdmissionDialogResult } from 'src/app/shared/model/admission.model';
import { GetDivisionResponse, SimpleDivision, emptyGetDivisionResponse } from 'src/app/shared/model/visualize-divisions.models';
import { MatSnackBar } from '@angular/material/snack-bar';
import { environment } from 'src/environments/environment';
import { AuthenticationService } from 'src/app/shared/services/authentication.service';
import { VisualizeDivisionService } from 'src/app/visualizeDivision/visualize-division.service';
import { AdmitPatientDialogComponent } from '../admit-patient-dialog/admit-patient-dialog.component';
import { Router } from '@angular/router';


@Component({
  selector: 'app-request-patient-admission',
  templateUrl: './request-patient-admission.component.html',
  styleUrls: ['./request-patient-admission.component.css']
})
export class RequestPatientAdmissionComponent {
  requestAdmissionForm = new FormGroup({
    priority: new FormControl('', { validators: [Validators.required, Validators.min(0), Validators.pattern(/^[0-9]\d*$/)] }),
    localDoctorId: new FormControl('', { validators: [Validators.required] }),
    requestRationale: new FormControl('', { validators: [Validators.required] }),
  });

  selectedDivisionId: string | null = null;
  allDivisions: GetDivisionResponse[] = [];

  displayedColumns: Array<string> = ['roomNum', 'bedNum', 'occupancy'];

  divisionInformation: GetDivisionResponse = emptyGetDivisionResponse //empty object to avoid error
  requestedAdmission = false;
  employeeId = this.authenticationService.username;
  divisionsAvailable: Array<SimpleDivision> = [];
  divisonResponsible: string | null = null;

  constructor(@Inject(MAT_DIALOG_DATA) public data: RequestAdmissionDialogOpenData,
    private dialogRef: MatDialogRef<RequestPatientAdmissionComponent>,
    private admitPatientService: AdmitPatientService,
    private snackbar: MatSnackBar,
    private authenticationService: AuthenticationService,
    private visualizeDivisionService: VisualizeDivisionService,
    private dialog: MatDialog,
    private router: Router,) { }

  ngOnInit(): void {
    this.admitPatientService.getDivisionInformation()
      .subscribe({
        next: response => {
          this.divisionInformation = response;
          this.selectedDivisionId = this.divisionInformation.division.identifier;
          this.divisonResponsible = this.divisionInformation.division.identifier;
          this.visualizeDivisionService.getAllDivisions()
            .subscribe({
              next: allDivisionsResponse => {
                this.divisionsAvailable = allDivisionsResponse.divisions;
              },
              error: (error: Error) => {
                console.error(error);
              }
            });
        },
        error: (error: Error) => {
          console.error(error);
          this.dialogRef.close(RequestAdmissionDialogResult.CANCEL);
        }
      });
  }

  cancel(): void {
    this.dialogRef.close(RequestAdmissionDialogResult.CANCEL);
  }

  submitRequest() {
    if (!this.requestAdmissionForm.valid) {
      this.requestAdmissionForm.markAllAsTouched();
      this.snackbar.open('Missing/incorrect information. Please check the form.', 'Close', {
        duration: environment.snackbarDurationSeconds * 1000
      });
      return;
    }

    this.admitPatientService.requestPatientAdmission(
      {
        patientId: this.data.patientId,
        requestingChargeNurseId: this.employeeId,
        divisionId: this.divisionInformation.division.identifier,
        localDoctorId: this.requestAdmissionForm.controls['localDoctorId'].value!,
        priority: +this.requestAdmissionForm.controls['priority'].value!,
        requestRationale: this.requestAdmissionForm.controls['requestRationale'].value!,
      }
    ).subscribe({
      next: () => {
        this.snackbar.open("Admission Request successful!", undefined, {
          duration: environment.snackbarDurationSeconds * 1000
        });
        this.dialogRef.close(AdmitPatientDialogResult.RELOAD);
      },
      error: (error: Error) => {
        this.snackbar.open("Invalid Request!", undefined, {
          duration: environment.snackbarDurationSeconds * 1000
        });
        console.log(error);
      }
    })

  }
  divisionSelectionChange(): void {
    if (this.selectedDivisionId) {
      this.visualizeDivisionService.getDivisionById(this.selectedDivisionId)
        .subscribe({
          next: selectedDivision => {
            this.divisionInformation = selectedDivision;
          },
          error: (error: Error) => {
            console.error('Error getting division data:', error);
          }
        });
    }
  }

  get divisionComplete(): boolean {
    return this.divisionInformation.division.status === 'complete'
  }

  admitPatient() {
    this.dialogRef.close(AdmitPatientDialogResult.GO_REQUEST);

    const dialogOptions: AdmitPatientDialogOpenData = {
      patientId: this.data.patientId,
      isFromRequestList: false
    }
    this.dialog.open(AdmitPatientDialogComponent, {
      data: dialogOptions,
      disableClose: true
    }).afterClosed().subscribe(result => {
      if (result === RequestAdmissionDialogResult.RELOAD) {
        //reload page
        this.router.navigate(['/'], {
          skipLocationChange: true
        }).then(() => this.router.navigate(['/patient-file/' + this.data.patientId]));
      } else if (result === AdmitPatientDialogResult.GO_REQUEST) {
        // TODO Go to request page 
      }
    })
  }
}
