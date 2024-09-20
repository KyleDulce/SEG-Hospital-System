import { Component } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { PatientFile } from '../shared/model/patient-file.model';
import { PatientFileService } from './services/patient-file.service';

@Component({
  selector: 'app-consult-patient-file',
  templateUrl: './consult-patient-file.component.html',
  styleUrls: ['./consult-patient-file.component.css']
})
export class ConsultPatientFileComponent {
  constructor(
    private patientFileService: PatientFileService,
    private activatedRoute: ActivatedRoute
  ) {}

  patientFile!: PatientFile;

  errorMessage!: String;

  ngOnInit() {
    const patientId = this.activatedRoute.snapshot.paramMap.get('patientId');

    if (patientId) {
      this.patientFileService.getPatientFile(patientId).subscribe({
        next: (patientFile: PatientFile) => {
          this.patientFile = patientFile;
          console.log(patientFile.patientInfo);
        },
        error: (error) => {
          this.errorMessage = error;
        },
      });
    }
  }

}
