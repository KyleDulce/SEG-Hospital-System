import { Component } from '@angular/core';
import { MatDialog } from '@angular/material/dialog';
import { ActivatedRoute, Router } from '@angular/router';
import { StaffType } from 'src/app/shared/model/authentication.model';
import {
  DialogResult,
  LocationResponse,
  PatientFile,
} from 'src/app/shared/model/patient-file.model';
import { AuthenticationService } from 'src/app/shared/services/authentication.service';
import { PatientFileService } from '../../services/patient-file.service';
import { ModifyLocationHistoryDialogComponent } from '../../modify-location-history-dialog/modify-location-history-dialog.component';
import { LocationOpenDialogOptions } from 'src/app/shared/model/location.model';
import { map } from 'rxjs';

interface LocationHistoryRow {
  locationName: string;
  dataObj: LocationResponse;
}

@Component({
  selector: 'app-location-history',
  templateUrl: './location-history.component.html',
  styleUrls: ['./location-history.component.css'],
})
export class LocationHistoryComponent {
  constructor(
    private patientFileService: PatientFileService,
    private router: Router,
    private activatedRoute: ActivatedRoute,
    private authenticationService: AuthenticationService,
    private dialog: MatDialog
  ) {}

  locations: LocationHistoryRow[] = [];
  staffType?: StaffType;
  patientId: string | null = null;

  errorMessage!: String;

  ngOnInit() {
    this.patientId = this.activatedRoute.snapshot.paramMap.get('patientId');

    if (this.patientId) {
      this.patientFileService
        .getPatientFile(this.patientId)
        .pipe(
          map((patientFile: PatientFile) => {
            return patientFile.locations.map((locationResponse) => {
              const location = locationResponse.location;
              return {
                locationName: `${location.aptNumber}, ${location.streetNum} ${location.streetName}, ${location.city}`,
                dataObj: locationResponse,
              } satisfies LocationHistoryRow;
            });
          })
        )
        .subscribe({
          next: (result) => {
            this.locations = result;
          },
          error: (error) => {
            this.errorMessage = 'Patient not found';
          },
        });
    }

    this.staffType = this.authenticationService.getStaffType();
  }

  openNewLocationDialog(): void {
    const dialogData: LocationOpenDialogOptions = {
      patientId: this.patientId!,
    };
    this.dialog
      .open(ModifyLocationHistoryDialogComponent, {
        data: dialogData,
        disableClose: true,
      })
      .afterClosed()
      .subscribe((result) => {
        if (result === DialogResult.RELOAD) {
          //reload page
          this.router
            .navigate(['/'], {
              skipLocationChange: true,
            })
            .then(() =>
              this.router.navigate(['/patient-file/' + this.patientId])
            );
        }
      });
  }

  openEditLocationDialog(location: LocationResponse): void {
    const dialogData: LocationOpenDialogOptions = {
      patientLocation: location,
      patientId: this.patientId!,
    };
    this.dialog
      .open(ModifyLocationHistoryDialogComponent, {
        data: dialogData,
        disableClose: true,
      })
      .afterClosed()
      .subscribe((result) => {
        if (result === DialogResult.RELOAD) {
          //reload page
          this.router
            .navigate(['/'], {
              skipLocationChange: true,
            })
            .then(() =>
              this.router.navigate(['/patient-file/' + this.patientId])
            );
        }
      });
  }
}
