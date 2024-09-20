import { Component } from '@angular/core';
import { PatientFileService } from '../services/patient-file.service';
import { Router } from '@angular/router';
import { PatientFile } from '../../shared/model/patient-file.model';

@Component({
  selector: 'app-search-patient',
  templateUrl: './search-patient.component.html',
  styleUrls: ['./search-patient.component.css'],
})
export class SearchPatientComponent {

  constructor(
    private patientFileService: PatientFileService,
    private router: Router
  ) {}

  patientId!: String;
  patientFile!: PatientFile;
  errorMessage!: String;

  submit(): void {
    this.router.navigate(['/'], {
      skipLocationChange: true
    }).then(() => this.router.navigate(['/patient-file/' +  this.patientId]));
  }

}
