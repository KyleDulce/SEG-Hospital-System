import { Component, Inject } from '@angular/core';
import { FormControl, FormGroup, Validators } from '@angular/forms';
import { MAT_DIALOG_DATA, MatDialogRef } from '@angular/material/dialog';
import {
  LocationOpenDialogOptions,
  LocationRequest,
  LocationTrackingRequest,
} from 'src/app/shared/model/location.model';
import { LocationService } from '../services/location.service';
import {
  DialogResult,
  PatientLocation,
} from 'src/app/shared/model/patient-file.model';
import { MatSnackBar } from '@angular/material/snack-bar';
import { environment } from 'src/environments/environment';
import { switchMap } from 'rxjs';

@Component({
  selector: 'app-modify-location-history-dialog',
  templateUrl: './modify-location-history-dialog.component.html',
  styleUrls: ['./modify-location-history-dialog.component.css'],
})
export class ModifyLocationHistoryDialogComponent {
  constructor(
    @Inject(MAT_DIALOG_DATA) public data: LocationOpenDialogOptions | null,
    private dialogRef: MatDialogRef<ModifyLocationHistoryDialogComponent>,
    private locationService: LocationService,
    private snackbar: MatSnackBar
  ) {}

  private getInitialValueOrNull(keyName: keyof PatientLocation): any {
    return this.data != null
      ? this.data.patientLocation?.location[keyName]
      : null;
  }

  protected formGroup = new FormGroup({
    streetNum: new FormControl<string | null>(
      this.getInitialValueOrNull('streetNum'),
      [Validators.required]
    ),
    aptNum: new FormControl<string | null>(
      this.getInitialValueOrNull('aptNumber'),
      [Validators.required]
    ),
    streetName: new FormControl<string | null>(
      this.getInitialValueOrNull('streetName'),
      [Validators.required]
    ),
    city: new FormControl<string | null>(this.getInitialValueOrNull('city'), [
      Validators.required,
    ]),
    postalCode: new FormControl<string | null>(
      this.getInitialValueOrNull('postalCode'),
      [Validators.required, Validators.minLength(6), Validators.maxLength(6)]
    ),
    province: new FormControl<string | null>(
      this.getInitialValueOrNull('province'),
      [Validators.required]
    ),
    country: new FormControl<string | null>(
      this.getInitialValueOrNull('country'),
      [Validators.required]
    ),
    startDate: new FormControl<Date | null>(
      this.data != null
        ? new Date(this.data.patientLocation?.startDate ?? '')
        : null,
      [Validators.required]
    ),
    endDate: new FormControl<Date | null>(
      this.data != null
        ? new Date(this.data.patientLocation?.endDate ?? '')
        : null,
      [Validators.required]
    ),
  });

  public cancel() {
    this.dialogRef.close();
  }

  public okay() {
    if (!this.formGroup.valid) {
      this.formGroup.markAllAsTouched();
      return;
    }

    const requestData: LocationRequest = {
      aptNumber: Number.parseInt(
        this.formGroup.controls['aptNum'].value ?? '0'
      ),
      streetNum: Number.parseInt(
        this.formGroup.controls['streetNum'].value ?? '0'
      ),
      streetName: this.formGroup.controls['streetName'].value!,
      city: this.formGroup.controls['city'].value!,
      country: this.formGroup.controls['country'].value!,
      province: this.formGroup.controls['province'].value!,
      postalCode: this.formGroup.controls['postalCode'].value!,
    };

    const trackingRequest: LocationTrackingRequest = {
      endDate: this.formGroup.controls['endDate'].value?.toISOString()!,
      startDate: this.formGroup.controls['startDate'].value?.toISOString()!,
    };

    if (this.data?.patientLocation != null) {
      const locationId = this.data.patientLocation.location.locationId;
      this.locationService
        .updateLocation(locationId, requestData)
        .pipe(
          switchMap(() =>
            this.locationService.updatePatientLocation(
              locationId,
              this.data?.patientId!,
              trackingRequest
            )
          )
        )
        .subscribe({
          next: () => {
            this.snackbar.open('Location Added', undefined, {
              duration: environment.snackbarDurationSeconds * 1000,
            });
            this.dialogRef.close(DialogResult.RELOAD);
          },
          error: (error: any) => {
            console.error(error);
            let message: string;
            if (error.error) {
              message = error.error;
            } else {
              message = 'unexpected error';
            }

            this.snackbar.open(message, undefined, {
              duration: environment.snackbarDurationSeconds * 1000,
            });
            this.dialogRef.close(DialogResult.CANCEL);
          },
        });
    } else {
      this.locationService
        .addLocation(requestData)
        .pipe(
          switchMap((returnedId) =>
            this.locationService.updatePatientLocation(
              returnedId,
              this.data?.patientId!,
              trackingRequest
            )
          )
        )
        .subscribe({
          next: () => {
            this.snackbar.open('Location Updated', undefined, {
              duration: environment.snackbarDurationSeconds * 1000,
            });
            this.dialogRef.close(DialogResult.RELOAD);
          },
          error: (error: any) => {
            console.error(error);
            let message: string;
            if (error.error) {
              message = error.error;
            } else {
              message = 'unexpected error';
            }

            this.snackbar.open(message, undefined, {
              duration: environment.snackbarDurationSeconds * 1000,
            });
            this.dialogRef.close(DialogResult.CANCEL);
          },
        });
    }
  }
}
