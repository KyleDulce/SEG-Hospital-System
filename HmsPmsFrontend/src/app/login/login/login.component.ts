import { Component } from '@angular/core';
import { FormControl, FormGroup, Validators } from '@angular/forms';
import { MatSnackBar } from '@angular/material/snack-bar';
import { Router } from '@angular/router';
import { AuthenticationService } from 'src/app/shared/services/authentication.service';
import { environment } from 'src/environments/environment';

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.css']
})
export class LoginComponent {

  loginForm = new FormGroup({
    staffId: new FormControl('', {validators: [Validators.required]}),
    password: new FormControl('', {validators: [Validators.required]}),
  })

  constructor(
    private authenticationService: AuthenticationService,
    private snackbar: MatSnackBar,
    private router: Router
  ) {}

  public onSubmit() {
    if(!this.loginForm.valid) {
      this.loginForm.markAllAsTouched();
      return;
    }

    this.authenticationService.login({
      employeeId: this.loginForm.controls['staffId'].value as String,
      password: this.loginForm.controls['password'].value as String
    }).subscribe({
      next: response => {
        //TODO route to page
        this.router.navigate(['/patient-file']);
      },
      error: error => {
        this.snackbar.open("Failed to login: invalid username or password", "Close", {
          duration: environment.snackbarDurationSeconds * 1000
        });
      }
    });
  }
}
