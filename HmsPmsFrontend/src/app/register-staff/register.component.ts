import { Component } from '@angular/core';
import { AbstractControl, FormBuilder, FormControl, FormGroup, Validators } from '@angular/forms';
import { MatSlideToggleChange } from '@angular/material/slide-toggle';
import { MatSnackBar } from '@angular/material/snack-bar';
import { Router } from '@angular/router';
import { AuthenticationService } from 'src/app/shared/services/authentication.service';
import { environment } from 'src/environments/environment';

@Component({
  selector: 'app-register',
  templateUrl: './register.component.html',
  styleUrls: ['./register.component.css']
})
export class RegisterComponent {
  isChecked = true;
  
  registerForm = new FormGroup({
    firstName: new FormControl('', {validators: [Validators.required, Validators.pattern(/^[a-zA-Z]+$/)]}),
    lastName: new FormControl('', {validators: [Validators.required, Validators.pattern(/^[a-zA-Z]+$/)]}),
    dateOfBirth: new FormControl('', {validators: [Validators.required]}),
    phoneNumber:new FormControl('', {validators: [Validators.required, Validators.pattern('^[1-9][0-9]{2}[1-9][0-9]{6}$'),Validators.minLength(10), Validators.maxLength(10)]}),
    email: new FormControl('', {validators: [Validators.email]}),
    streetNumber: new FormControl('', {validators: [Validators.required, Validators.min(1), Validators.pattern('^[1-9][0-9]*$')]}),
    unit: new FormControl('', {validators: [Validators.required, Validators.min(1), Validators.pattern('^[1-9][0-9]*$')]}),
    streetName: new FormControl('', {validators: [Validators.required, Validators.pattern(/^[a-zA-Z]+$/)]}),
    zipCode: new FormControl('', {validators: [Validators.required, Validators.pattern(/^[A-Za-z]\d[A-Za-z]\d[A-Za-z]\d$/)]}),
    city :new FormControl('', {validators: [Validators.required, Validators.pattern(/^[a-zA-Z]+$/)]}),
    country: new FormControl('', {validators: [Validators.required, Validators.pattern(/^[a-zA-Z]+$/)]}),
    province:new FormControl('', {validators: [Validators.required, Validators.pattern(/^[a-zA-Z]+$/)]}),
    staffId: new FormControl('', {validators: [Validators.required]}),
    password: new FormControl('', {validators: [Validators.required]}),
    retypedPass: new FormControl('', {validators: [Validators.required]}),
    teleExt: new FormControl('', {validators: [Validators.required]}),
    modPerm: new FormControl(false),
    staffType: new FormControl('', {validators: [Validators.required]}),
    bipperExtension: new FormControl(),
    divName: new FormControl(''),
  })

  staffTypeChosen(): void {
    const bipperExtensionControl = this.registerForm.controls['bipperExtension'];
    const divNameControl = this.registerForm.controls['divName'];
    if (this.nurseSelected() && !this.doctorSelected()) {
      bipperExtensionControl.setValidators([Validators.required, Validators.pattern(/^[0-9][0-9]*$/)]);
      divNameControl.clearValidators();
    } else if (!this.nurseSelected() && this.doctorSelected()) {
      divNameControl.setValidators([Validators.required, Validators.pattern(/^[a-zA-Z]+$/)]);
      bipperExtensionControl.clearValidators();
    }
    else {
      bipperExtensionControl.clearValidators();
      divNameControl.clearValidators();
    }
  }

  nurseSelected(): boolean {
    return this.registerForm.controls['staffType'].value === 'nurse';
  }

  doctorSelected(): boolean {
    return this.registerForm.controls['staffType'].value === 'doctor';
  }

 

  constructor(
    private authenticationService: AuthenticationService,
    private snackbar: MatSnackBar,
    private router: Router
  ) {}

 
  
  public onSubmit() {
    if(!this.registerForm.valid) {
      this.snackbar.open('Missing/incorrect information. Please check the form.', 'Close', {
        duration: environment.snackbarDurationSeconds * 1000
      });
    }
    else {
   
  this.authenticationService.register({
    firstName: this.registerForm.controls['firstName'].value as String,
    lastName:this.registerForm.controls['lastName'].value as String,
    dateOfBirth: new Date(this.registerForm.controls['dateOfBirth'].value!).toISOString(),
    phoneNumber: this.registerForm.controls['phoneNumber'].value as unknown as Number,
    email: this.registerForm.controls['email'].value as String,
    streetNumber: this.registerForm.controls['streetNumber'].value as unknown as Number,
    unit: this.registerForm.controls['unit'].value as unknown as Number,
    streetName: this.registerForm.controls['streetName'].value as String,
    zipCode: this.registerForm.controls['zipCode'].value as String,
    city :this.registerForm.controls['city'].value as String,
    country: this.registerForm.controls['country'].value as String,
    province:this.registerForm.controls['province'].value as String,
    staffId: this.registerForm.controls['staffId'].value as String,
    password: this.registerForm.controls['password'].value as String,
    retypedPass: this.registerForm.controls['retypedPass'].value as String,
    teleExt: this.registerForm.controls['teleExt'].value as unknown as Number,
    modPerm: this.registerForm.controls['modPerm'].value as Boolean,
    staffType:  this.registerForm.controls['staffType'].value as String,
    bipperExtension: this.registerForm.controls['bipperExtension'].value as Number,
    divName: this.registerForm.controls['divName'].value as String

  
  }
    
  ).subscribe({
    next: response => {
      this.router.navigate(['/']);
      console.log('Registration successful', response);
    },
    error: error => {
      // Registration failed, handle the error
      this.snackbar.open("Registration failed", "Close", {
        duration: environment.snackbarDurationSeconds * 1000
      });
    }
  });
    }
  }
  onToggleChange(event: MatSlideToggleChange) {
    const toggleValue = event.checked;
    this.registerForm.get('modPerm')?.setValue(toggleValue);
  }
}
