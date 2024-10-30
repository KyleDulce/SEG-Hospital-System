import { Component, Inject } from '@angular/core';
import { FormControl, FormGroup, Validators } from '@angular/forms';
import { MAT_DIALOG_DATA, MatDialogRef } from '@angular/material/dialog';
import {
  DialogResult, Infection, InfectionDialogOpenOptions,
  InfectionRequest,
} from "../../shared/model/patient-file.model";
import {environment} from "../../../environments/environment";
import {MatSnackBar} from "@angular/material/snack-bar";
import {InfectionService} from "../services/infection.service";

@Component({
  selector: 'app-modify-infection-dialog',
  templateUrl: './modify-infection-dialog.component.html',
  styleUrls: ['./modify-infection-dialog.component.css'],
})
export class ModifyInfectionDialogComponent {
  constructor(
    @Inject(MAT_DIALOG_DATA) public data: InfectionDialogOpenOptions,
    private dialogRef: MatDialogRef<ModifyInfectionDialogComponent>,
    private infectionService: InfectionService,
    private snackbar: MatSnackBar
  ) {
  }

  private getInitialValueOrNull(keyName: keyof Infection): any {
    return this.data.infection != null ? this.data.infection[keyName] : null;
  }

  protected formGroup = new FormGroup({
    infectionName: new FormControl<string>(
      this.getInitialValueOrNull('name'),
      [Validators.required]
    ),
    startDate: new FormControl<Date | null>(
      this.data != null
        ? new Date(this.data.infection?.startDate ?? '')
        : null,
      [Validators.required]
    ),
    endDate: new FormControl<Date | null>(
      this.data != null
        ? new Date(this.data.infection?.endDate ?? '')
        : null,
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

    const requestData: InfectionRequest = {
      patientId: this.data.patientId,
      startDate: this.formGroup.controls['startDate'].value!.toISOString()!,
      endDate: this.formGroup.controls['endDate'].value!.toISOString()!,
      name: this.formGroup.controls['infectionName'].value!
    };

    if (this.data.infection == null) {
      this.infectionService.addInfection(requestData)
        .subscribe({
          next: () => {
            this.snackbar.open("Infection Added", undefined, {
              duration: environment.snackbarDurationSeconds * 1000
            });
            this.dialogRef.close(DialogResult.RELOAD);
          },
          error: (error: any) => {
            let message: string;
            if (error.error) {
              message = error.error;
            } else {
              message = "unexpected error";
            }

            this.snackbar.open(message, undefined, {
              duration: environment.snackbarDurationSeconds * 1000
            });
            this.dialogRef.close(DialogResult.CANCEL);
          }
        })
    } else {
      console.log(this.data);
      this.infectionService.updateInfection((this.data.infection as any).infectionId!, requestData)
        .subscribe({
          next: () => {
            this.snackbar.open("Infection Updated", undefined, {
              duration: environment.snackbarDurationSeconds * 1000
            });
            this.dialogRef.close(DialogResult.RELOAD);
          },
          error: (error: any) => {
            let message: string;
            if (error.error) {
              message = error.error;
            } else {
              message = "unexpected error";
            }

            this.snackbar.open(message, undefined, {
              duration: environment.snackbarDurationSeconds * 1000
            });
            this.dialogRef.close(DialogResult.CANCEL);
          }
        })
    }
  }
}
