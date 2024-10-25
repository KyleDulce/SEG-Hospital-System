import { Component } from '@angular/core';
import {PatientFileService} from "../../services/patient-file.service";
import {ActivatedRoute, Router} from "@angular/router";
import {AuthenticationService} from "../../../shared/services/authentication.service";
import {MatDialog} from "@angular/material/dialog";
import {
  Infection,
  PatientFile,
  DialogResult,
  DialogOpenOptions,
} from "../../../shared/model/patient-file.model";
import {StaffType} from "../../../shared/model/authentication.model";
import {ModifyInfectionDialogComponent} from "../../modify-infection-dialog/modify-infection-dialog.component";

@Component({
  selector: 'app-infection',
  templateUrl: './infection.component.html',
  styleUrls: ['./infection.component.css']
})
export class InfectionComponent {
  constructor(
    private patientFileService: PatientFileService,
    private router: Router,
    private activatedRoute: ActivatedRoute,
    private authenticationService: AuthenticationService,
    private dialog: MatDialog,
  ) {}

  infections!: Infection[];
  staffType?: StaffType;
  patientId: string | null = null;

  displayedColumns = ["identifier", "name", "startDate", "endDate"];

  errorMessage!: String;

  ngOnInit() {
    this.patientId = this.activatedRoute.snapshot.paramMap.get('patientId');

    if (this.patientId) {
      this.patientFileService.getPatientFile(this.patientId).subscribe({
        next: (patientFile: PatientFile) => {
          this.infections = patientFile.infections;
        },
        error: (error) => {
          this.errorMessage = "Patient not found";
        },
      });
    }

    this.staffType = this.authenticationService.getStaffType();
  }

  infect(): void {
    const dialogData: DialogOpenOptions = {
      patientId: this.patientId!
    };
    this.dialog.open(ModifyInfectionDialogComponent, {
      data: dialogData,
      disableClose: true
    }).afterClosed().subscribe(result => {
      if(result === DialogResult.RELOAD) {
        //reload page
        this.router.navigate(['/'], {
          skipLocationChange: true
        }).then(() => this.router.navigate(['/patient-file/' +  this.patientId]));
      }
    })
  }

}
