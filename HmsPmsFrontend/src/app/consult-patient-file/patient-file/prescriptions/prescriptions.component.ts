import { Component } from '@angular/core';
import { Router, ActivatedRoute } from '@angular/router';
import { PatientFile, PrescribeDialogResult, PrescribeMedDialogOpenOptions, Prescription } from '../../../shared/model/patient-file.model';
import { PatientFileService } from '../../services/patient-file.service';
import { StaffType } from 'src/app/shared/model/authentication.model';
import { AuthenticationService } from 'src/app/shared/services/authentication.service';
import { MatDialog } from '@angular/material/dialog';
import { PrescribeDialogComponent } from 'src/app/prescribe/prescribe-dialog/prescribe-dialog.component';

@Component({
  selector: 'app-prescriptions',
  templateUrl: './prescriptions.component.html',
  styleUrls: ['./prescriptions.component.css']
})
export class PrescriptionsComponent {

  constructor(
    private patientFileService: PatientFileService,
    private router: Router,
    private activatedRoute: ActivatedRoute,
    private authenticationService: AuthenticationService,
    private dialog: MatDialog,
  ) {}

  prescriptions!: Prescription[];
  staffType?: StaffType;
  patientId: string | null = null;

  displayedColumns = ["identifier", "name", "unitByDay", "adminByDay", "methodOfAdministration", "startDate", "endDate"];

  errorMessage!: String;

  ngOnInit() {
    this.patientId = this.activatedRoute.snapshot.paramMap.get('patientId');

    if (this.patientId) {
      this.patientFileService.getPatientFile(this.patientId).subscribe({
        next: (patientFile: PatientFile) => {
          this.prescriptions = patientFile.prescriptions;
        },
        error: (error) => {
          this.errorMessage = "Patient not found";
        },
      });
    }

    this.staffType = this.authenticationService.getStaffType();
  }

  prescribe(): void {
    const dialogData: PrescribeMedDialogOpenOptions = {
      patientId: this.patientId!
    };
    this.dialog.open(PrescribeDialogComponent, {
      data: dialogData,
      disableClose: true
    }).afterClosed().subscribe(result => {
      if(result === PrescribeDialogResult.RELOAD) {
        //reload page
        this.router.navigate(['/'], {
          skipLocationChange: true
        }).then(() => this.router.navigate(['/patient-file/' +  this.patientId]));
      }
    })
  }

}
