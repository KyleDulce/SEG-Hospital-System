import { Component,  Input, Output, EventEmitter, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { Address, NextOfKin, Patient } from 'src/app/shared/model/patient-file.model';
import { PatientFileService } from '../../services/patient-file.service';
import { AuthenticationService } from 'src/app/shared/services/authentication.service';
import { MatSnackBar } from '@angular/material/snack-bar';
import { environment } from 'src/environments/environment';
import { Router } from '@angular/router';
import { ChangeDetectorRef } from '@angular/core';


@Component({
  selector: 'app-edit-patient-info',
  templateUrl: './edit-patient-info.component.html',
  styleUrls: ['./edit-patient-info.component.css']
})
export class EditPatientInfoComponent implements OnInit {
  @Input() patientData!: Patient;
  @Input() address!: Address;
  @Input() nextOfKin!: NextOfKin;
  @Input() patientId!: String;
  @Output() cancelEdit = new EventEmitter<void>(); 

  editForm!: FormGroup;
  employeeId = this.authenticationService.username;
  

  constructor(private formBuilder: FormBuilder,
    private patientFileService: PatientFileService,
    private authenticationService: AuthenticationService,
    private snackbar: MatSnackBar,
    private router: Router,
    private changeDetectorRef: ChangeDetectorRef, 
    ) {}

  ngOnInit() {
    this.editForm = this.formBuilder.group({
      firstName: [this.patientData.firstName, [Validators.required, Validators.pattern(/^[a-zA-Z]+$/)]],
      lastName: [this.patientData.lastName, [Validators.required, Validators.pattern(/^[a-zA-Z]+$/)]],
      gender: [this.patientData.gender, [Validators.required, Validators.pattern(/^[a-zA-Z]+$/)]],
      dateOfBirth: [this.patientData.dateOfBirth, [Validators.required, Validators.pattern(/^\d{4}-\d{2}-\d{2}$/)]],
      martialStatus: [this.patientData.martialStatus, [Validators.required, Validators.pattern(/^[a-zA-Z]+$/)]],
      telephone: [this.patientData.telephone, [Validators.required, Validators.pattern('^[1-9][0-9]{2}[1-9][0-9]{6}$'),Validators.minLength(10), Validators.maxLength(10)]],
      govInsuranceNum: [this.patientData.govInsuranceNum, Validators.required],
      extDoctorId: [this.patientData.extDoctorId, Validators.required],
      patientStreetNum: [this.address.streetNum,[Validators.required, Validators.min(1), Validators.pattern('^[0-9][0-9]*$')]],
      patientAptNum: [this.address.aptNumber, [Validators.required, Validators.min(1), Validators.pattern('^[0-9][0-9]*$')]],
      patientStreetName: [this.address.streetName, [Validators.required, Validators.pattern(/^[a-zA-Z]+$/)]],
      patientCity: [this.address.city, [Validators.required, Validators.pattern(/^[a-zA-Z]+$/)]],
      patientPostalCode: [this.address.postalCode, [Validators.required, Validators.pattern(/^[A-Za-z]\d[A-Za-z]\d[A-Za-z]\d$/)]],
      patientProvince: [this.address.province, [Validators.required, Validators.pattern(/^[a-zA-Z]+$/)]],
      patientCountry: [this.address.country, [Validators.required, Validators.pattern(/^[a-zA-Z]+$/)]],
      nextOfKinFirstName: [this.nextOfKin.firstName, [Validators.required, Validators.pattern(/^[a-zA-Z]+$/)]],
      nextOfKinLastName: [this.nextOfKin.lastName, [Validators.required, Validators.pattern(/^[a-zA-Z]+$/)]],
      nextOfKinTelephone: [this.nextOfKin.telephone, [Validators.required, Validators.pattern('^[1-9][0-9]{2}[1-9][0-9]{6}$'),Validators.minLength(10), Validators.maxLength(10)]],
      relationship: [this.nextOfKin.relationshipToPatient,[Validators.required, Validators.pattern(/^[a-zA-Z]+$/)]],
      nOKStreetNum: [this.nextOfKin.address.streetNum, [Validators.required, Validators.min(1), Validators.pattern('^[0-9][0-9]*$')]],
      nOKAptNum: [this.nextOfKin.address.aptNumber, [Validators.required, Validators.min(1), Validators.pattern('^[0-9][0-9]*$')]],
      nOKStreetName: [this.nextOfKin.address.streetName, [Validators.required, Validators.pattern(/^[a-zA-Z]+$/)]],
      nOKCity: [this.nextOfKin.address.city, [Validators.required, Validators.pattern(/^[a-zA-Z]+$/)]],
      nOKPostalCode: [this.nextOfKin.address.postalCode, [Validators.required, Validators.pattern(/^[A-Za-z]\d[A-Za-z]\d[A-Za-z]\d$/)]],
      nOKProvince: [this.nextOfKin.address.province, [Validators.required, Validators.pattern(/^[a-zA-Z]+$/)]],
      nOKCountry: [this.nextOfKin.address.country, [Validators.required, Validators.pattern(/^[a-zA-Z]+$/)]],
    });

  this.editForm.patchValue({
    firstName:this.editForm.controls['firstName'].value,
    lastName: this.editForm.controls['lastName'].value,
    gender:this.editForm.controls['gender'].value,
    dateOfBirth: this.editForm.controls['dateOfBirth'].value,
    martialStatus: this.editForm.controls['martialStatus'].value,
    telephone:this.editForm.controls['telephone'].value,
    govInsuranceNum: this.editForm.controls['govInsuranceNum'].value,
    extDoctorId: this.editForm.controls['extDoctorId'].value,
    patientStreetNum: this.editForm.controls['patientStreetNum'].value,
    patientAptNum: this.editForm.controls['patientAptNum'].value,
    patientStreetName: this.editForm.controls['patientStreetName'].value,
    patientCity: this.editForm.controls['patientCity'].value,
    patientPostalCode: this.editForm.controls['patientPostalCode'].value,
    patientProvince: this.editForm.controls['patientProvince'].value,
    patientCountry: this.editForm.controls['patientCountry'].value,
    nextOfKinFirstName: this.editForm.controls['nextOfKinFirstName'].value,
    nextOfKinLastName: this.editForm.controls['nextOfKinLastName'].value,
    nextOfKinTelephone: this.editForm.controls['nextOfKinTelephone'].value,
    relationship: this.editForm.controls['relationship'].value,
    nOKStreetNum: this.editForm.controls['nOKStreetNum'].value,
    nOKAptNum: this.editForm.controls['nOKAptNum'].value,
    nOKStreetName: this.editForm.controls['nOKStreetName'].value,
    nOKCity: this.editForm.controls['nOKCity'].value,
    nOKPostalCode: this.editForm.controls['nOKPostalCode'].value,
    nOKProvince: this.editForm.controls['nOKProvince'].value,
    nOKCountry: this.editForm.controls['nOKCountry'].value,
  });
  }

  onSubmit() {
    
    if (this.editForm.valid) {
      this.patientFileService.updatePatient({
        employeeId: this.employeeId,
        patientId: this.patientId,
        firstName: this.editForm.controls['firstName'].value,
        lastName: this.editForm.controls['lastName'].value,
        gender:this.editForm.controls['gender'].value,
        dateOfBirth: new Date(this.editForm.controls['dateOfBirth'].value).toISOString(),
        martialStatus: this.editForm.controls['martialStatus'].value,
        telephone:this.editForm.controls['telephone'].value,
        govInsuranceNum: this.editForm.controls['govInsuranceNum'].value,
        extDoctorId: this.editForm.controls['extDoctorId'].value,
        patientStreetNum: this.editForm.controls['patientStreetNum'].value,
        patientAptNum: this.editForm.controls['patientAptNum'].value,
        patientStreetName: this.editForm.controls['patientStreetName'].value,
        patientCity: this.editForm.controls['patientCity'].value,
        patientPostalCode: this.editForm.controls['patientPostalCode'].value,
        patientProvince: this.editForm.controls['patientProvince'].value,
        patientCountry: this.editForm.controls['patientCountry'].value,
        nextOfKinFirstName: this.editForm.controls['nextOfKinFirstName'].value,
        nextOfKinLastName: this.editForm.controls['nextOfKinLastName'].value,
        nextOfKinTelephone: this.editForm.controls['nextOfKinTelephone'].value,
        relationship: this.editForm.controls['relationship'].value,
        nOKStreetNum: this.editForm.controls['nOKStreetNum'].value,
        nOKAptNum: this.editForm.controls['nOKAptNum'].value,
        nOKStreetName: this.editForm.controls['nOKStreetName'].value,
        nOKCity: this.editForm.controls['nOKCity'].value,
        nOKPostalCode: this.editForm.controls['nOKPostalCode'].value,
        nOKProvince: this.editForm.controls['nOKProvince'].value,
        nOKCountry: this.editForm.controls['nOKCountry'].value,

      }).subscribe({
        next: response => {
          console.log('Patient data updated successfully:', response);
          this.router.navigate(['/'], {
            skipLocationChange: true
          }).then(() => this.router.navigate(['/patient-file/' +  this.patientId]));
          this.changeDetectorRef.detectChanges();
        },
        error: (error) => {
          console.error('Error updating patient data:', error);
          this.snackbar.open('Update failed', 'Close', {
            duration: environment.snackbarDurationSeconds * 1000,
          });
        },
      });
      this.cancelEdit.emit();
    }
    else {
      this.snackbar.open('Missing/incorrect information. Please check the form.', 'Close', {
        duration: environment.snackbarDurationSeconds * 1000
      });
    }
  }
}
