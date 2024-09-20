import { ChangeDetectorRef, Component, Input } from '@angular/core';
import { Admission, AdmitPatientDialogOpenData, AdmitPatientDialogResult, RequestAdmissionDialogOpenData, RequestAdmissionDialogResult } from '../../../shared/model/admission.model';
import { DischargeDialogOpenData, PatientFile } from '../../../shared/model/patient-file.model';
import { PatientFileService } from '../../services/patient-file.service';
import { ActivatedRoute, Router } from '@angular/router';
import { MatDialog } from '@angular/material/dialog';
import { AdmitPatientDialogComponent } from 'src/app/admit-patient/admit-patient-dialog/admit-patient-dialog.component';
import { AuthenticationService } from 'src/app/shared/services/authentication.service';
import { StaffType } from 'src/app/shared/model/authentication.model';
import { DischargeDialogComponent } from 'src/app/discharge/discharge-dialog/discharge-dialog.component';
import { RequestPatientAdmissionComponent } from 'src/app/admit-patient/request-patient-admission/request-patient-admission.component';

@Component({
  selector: 'app-admission-records',
  templateUrl: './admission-records.component.html',
  styleUrls: ['./admission-records.component.css']
})
export class AdmissionRecordsComponent {

  constructor(
    private patientFileService: PatientFileService,
    private router: Router,
    private activatedRoute: ActivatedRoute,
    private dialog: MatDialog,
    private authenticationService: AuthenticationService,
    private changeDetectorRef: ChangeDetectorRef
  ) {}

  private patientId: string | null = null;
  staffType?: StaffType;

  admissionRecords!: Admission[];

  displayedColumns = ["recordId", "localDoctor", "divisionId", "roomNum", "bedNumber", "privInsuranceNum", "discharged"];

  errorMessage!: String;

  get isPatientAdmitted(): boolean {
    for(let x = 0; x < this.admissionRecords.length; x++) {
      if(!this.admissionRecords[x].discharged) {
        return true;
      }
    }
    return false;
  }

  ngOnInit() {
    this.patientId = this.activatedRoute.snapshot.paramMap.get('patientId');

    if (this.patientId) {
      this.patientFileService.getPatientFile(this.patientId).subscribe({
        next: (patientFile: PatientFile) => {
          this.admissionRecords = patientFile.admissionRecords;
        },
        error: (error) => {
          this.errorMessage = "Patient not found";
        },
      });
    }

    this.staffType = this.authenticationService.getStaffType();

    this.changeDetectorRef.detectChanges();
  }

  public admitPatient() {
    if(!this.patientId || this.staffType !== 'ChargeNurse') {
      return;
    }

    const dialogOptions: AdmitPatientDialogOpenData = {
      patientId: this.patientId,
      isFromRequestList: false
    }
    this.dialog.open(AdmitPatientDialogComponent, {
      data: dialogOptions,
      disableClose: true
    }).afterClosed().subscribe(result => {
      if(result === AdmitPatientDialogResult.RELOAD) {
        //reload page
        this.router.navigate(['/'], {
          skipLocationChange: true
        }).then(() => this.router.navigate(['/patient-file/' +  this.patientId]));
      }
    })
  }

  public dischargePatient() {
    if(!this.patientId || this.staffType !== 'ChargeNurse') {
      return;
    }
    const dialogOption: DischargeDialogOpenData = {
      patientId: this.patientId
    }
    this.dialog.open(DischargeDialogComponent, {
      data: dialogOption,
      disableClose: true
    }).afterClosed().subscribe(result => {
      if(result === AdmitPatientDialogResult.RELOAD) {
        //reload page
        this.router.navigate(['/'], {
          skipLocationChange: true
        }).then(() => this.router.navigate(['/patient-file/' +  this.patientId]));
      }
    });
  }

  public requestAdmission() {
    if(!this.patientId || this.staffType !== 'ChargeNurse') {
      return;
    }

    const dialogOptions: RequestAdmissionDialogOpenData = {
      patientId: this.patientId
    }
    this.dialog.open(RequestPatientAdmissionComponent, {
      data: dialogOptions,
      disableClose: true
    }).afterClosed().subscribe(result => {
      if(result === RequestAdmissionDialogResult.RELOAD) {
        //reload page
        this.router.navigate(['/'], {
          skipLocationChange: true
        }).then(() => this.router.navigate(['/patient-file/' +  this.patientId]));
      }
    });
  }
}

