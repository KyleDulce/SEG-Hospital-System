import { Component } from '@angular/core';
import { FormControl, FormGroup, Validators } from '@angular/forms';
import { MatSnackBar } from '@angular/material/snack-bar';
import { RegisterPatientService } from './register-patient.service';
import { environment } from 'src/environments/environment';
import { Router } from '@angular/router';

@Component({
  selector: 'app-register-patient',
  templateUrl: './register-patient.component.html',
  styleUrls: ['./register-patient.component.css']
})
export class RegisterPatientComponent {
  registerPatientForm = new FormGroup({
    // Patient info
    firstName: new FormControl<string>("", {validators: [Validators.required]}),
    lastName: new FormControl<string>("", {validators: [Validators.required]}),
    gender: new FormControl<string>("", {validators: [Validators.required]}),
    dateOfBirth: new FormControl<Date | undefined>(undefined, {validators: [Validators.required]}),
    maritalStatus: new FormControl<string>("", {validators: [Validators.required]}),
    telephone: new FormControl<number | undefined>(undefined, {validators: [Validators.required, Validators.minLength(10), Validators.maxLength(10)]}),
    extDoctorID: new FormControl<string>("", {validators: [Validators.required]}),
    govInsurNum: new FormControl<number | undefined>(undefined, {validators: [Validators.required]}),

    // Patient address
    streetNumber: new FormControl<number | undefined>(undefined, {validators: [Validators.required, Validators.min(1)]}),
    unit: new FormControl<number | undefined>(undefined, {validators: [Validators.required, Validators.min(1)]}),
    streetName: new FormControl<string>("", {validators: [Validators.required]}),
    zipCode: new FormControl<string>("", {validators: [Validators.required, Validators.pattern(/^[A-Za-z]\d[A-Za-z]\d[A-Za-z]\d$/)]}),
    city: new FormControl<string>("", {validators: [Validators.required]}),
    province: new FormControl<string>("", {validators: [Validators.required]}),
    country: new FormControl<string>("", {validators: [Validators.required]}),

    // Next of kin info
    nokFirstName: new FormControl<string>("", {validators: [Validators.required]}),
    nokLastName: new FormControl<string>("", {validators: [Validators.required]}),
    nokRelationshipToPatient: new FormControl<string>("", {validators: [Validators.required]}),
    nokTelephone: new FormControl<number | undefined>(undefined, {validators: [Validators.required, Validators.minLength(10), Validators.maxLength(10)]}),
    
    // Next of kin address
    nokStreetNumber: new FormControl<number | undefined>(undefined, {validators: [Validators.required, Validators.min(1)]}),
    nokUnit: new FormControl<number | undefined>(undefined, {validators: [Validators.required, Validators.min(1)]}),
    nokStreetName: new FormControl<string>("", {validators: [Validators.required]}),
    nokZipCode: new FormControl<string>("", {validators: [Validators.required, Validators.pattern(/^[A-Za-z]\d[A-Za-z]\d[A-Za-z]\d$/)]}),
    nokCity :new FormControl<string>("", {validators: [Validators.required]}),
    nokCountry: new FormControl<string>("", {validators: [Validators.required]}),
    nokProvince: new FormControl<string>("", {validators: [Validators.required]}),
  })

  constructor(
    private registerPatientService: RegisterPatientService,
    private snackbar: MatSnackBar,
    private router: Router
  ) {}

  public onSubmit() {
    if(!this.registerPatientForm.valid) {
      this.snackbar.open("Missing/incorrect information. Please check the form.", "Close");
    }
    else {
      this.registerPatientService.register(
        {
          firstName: this.registerPatientForm.controls['firstName'].value as string,
          lastName: this.registerPatientForm.controls['lastName'].value as string,
          gender: this.registerPatientForm.controls['gender'].value as string,
          dateOfBirth: new Date(this.registerPatientForm.controls['dateOfBirth'].value!).toISOString(),
          maritalStatus: this.registerPatientForm.controls['maritalStatus'].value as string,
          telephone: this.registerPatientForm.controls['telephone'].value as unknown as number,
          extDoctorID: this.registerPatientForm.controls['extDoctorID'].value as unknown as number,
          govInsurNum: this.registerPatientForm.controls['govInsurNum'].value as unknown as number,

          address: {
            streetNum: this.registerPatientForm.controls['streetNumber'].value as unknown as number,
            streetName: this.registerPatientForm.controls['streetName'].value as string,
            aptNumber: this.registerPatientForm.controls['unit'].value as unknown as number,
            postalCode: this.registerPatientForm.controls['zipCode'].value as string,
            city: this.registerPatientForm.controls['city'].value as string,
            province: this.registerPatientForm.controls['province'].value as string,
            country: this.registerPatientForm.controls['country'].value as string,
          },
          
          nextOfKin: {
            firstName: this.registerPatientForm.controls['nokFirstName'].value as string,
            lastName: this.registerPatientForm.controls['nokLastName'].value as string,
            relationshipToPatient: this.registerPatientForm.controls['nokRelationshipToPatient'].value as string,
            telephone: this.registerPatientForm.controls['nokTelephone'].value as unknown as number,

            address: {
              streetNum: this.registerPatientForm.controls['nokStreetNumber'].value as unknown as number,
              streetName: this.registerPatientForm.controls['nokStreetName'].value as unknown as string,
              aptNumber: this.registerPatientForm.controls['nokUnit'].value as unknown as number,
              postalCode: this.registerPatientForm.controls['nokZipCode'].value as string,
              city: this.registerPatientForm.controls['nokCity'].value as string,
              province: this.registerPatientForm.controls['nokProvince'].value as string,
              country: this.registerPatientForm.controls['nokCountry'].value as string,
            }
          }
        }
        ).subscribe({
          next: (response: any) => {
            console.log('Registration successful', response);
            this.snackbar.open("Registration success", "Close", {
              duration: environment.snackbarDurationSeconds * 1000
            });
            this.router.navigate(['/'], {
              skipLocationChange: true
            }).then(() => this.router.navigate(['/register-patient']));
          },
          error: (error: any) => {
            // Registration failed, handle the error
            this.snackbar.open("Registration failed", "Close", {
              duration: environment.snackbarDurationSeconds * 1000
            });
          }
        });
    }
  }
}