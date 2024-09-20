import { Component, OnInit } from '@angular/core';
import { RequestListService } from '../request-list.service';
import { GetDivisionResponse, emptyGetDivisionResponse } from 'src/app/shared/model/visualize-divisions.models';
import { MatSnackBar } from '@angular/material/snack-bar';
import { environment } from 'src/environments/environment';
import { Router } from '@angular/router';
import { AdmitPatientDialogOpenData, AdmitPatientDialogResult, AdmitRequestFull } from 'src/app/shared/model/admission.model';
import { from, mergeMap, switchMap, tap, toArray } from 'rxjs';
import { MatDialog } from '@angular/material/dialog';
import { AdmitPatientDialogComponent } from 'src/app/admit-patient/admit-patient-dialog/admit-patient-dialog.component';

@Component({
  selector: 'app-request-list',
  templateUrl: './request-list.component.html',
  styleUrls: ['./request-list.component.css']
})
export class RequestListComponent implements OnInit {

  public divisionInformation: GetDivisionResponse = emptyGetDivisionResponse;
  public admitRequests: Array<AdmitRequestFull> = [];
  public tableColumns = ['patientId', 'patientName', 'rationale', 'priority'];

  constructor(private requestListService: RequestListService,
    private snackbar: MatSnackBar,
    private router: Router,
    private dialog: MatDialog) {}

  ngOnInit(): void {
    this.requestListService.getDivisionInformation()
      .pipe(
        tap(
          response => this.divisionInformation = response
        ),
        switchMap(response => from(response.division.admitRequests)),
        mergeMap(response => this.requestListService.getPatientInfoAsCombinedAdmitRequest(response)),
        toArray()
      )
      .subscribe({
        next: response => {
          this.admitRequests = response
        },
        error: (error: Error) => {
          this.snackbar.open("You are not authorized to access this page", "Close", {
            duration: environment.snackbarDurationSeconds
          });
          this.router.navigate(['/patient-file']);
          console.error(error);
        }
      });
  }

  public choosePatient(index: number): void {
    if(this.divisionInformation.division.status === 'complete') {
      this.snackbar.open("You cannot admit any patients since the division is full!", undefined, {
        duration: environment.snackbarDurationSeconds
      })
      return;
    }

    const patientId = this.admitRequests[index].patientId;

    const dialogData: AdmitPatientDialogOpenData = {
      patientId: patientId,
      isFromRequestList: true
    };

    this.dialog.open(AdmitPatientDialogComponent, {
      data: dialogData
    }).afterClosed().subscribe(result => {
      if(result === AdmitPatientDialogResult.RELOAD) {
        //reload page
        this.router.navigate(['/'], {
          skipLocationChange: true
        }).then(() => this.router.navigate(['requests']));
      } else if(result === AdmitPatientDialogResult.GO_REQUEST) {
        console.error("Illegal State!")
      }
    })
  }
}
