import { Component, OnInit } from '@angular/core';
import {
  Address,
  NextOfKin,
  PatientFile,
  Patient,
  PatientInfectionStatus,
} from '../../../shared/model/patient-file.model';
import { PatientFileService } from '../../services/patient-file.service';
import { ActivatedRoute, Router } from '@angular/router';

@Component({
  selector: 'app-patient-info',
  templateUrl: './patient-info.component.html',
  styleUrls: ['./patient-info.component.css'],
})
export class PatientInfoComponent implements OnInit {
  constructor(
    private patientFileService: PatientFileService,
    private router: Router,
    private activatedRoute: ActivatedRoute
  ) {}

  patient!: Patient;
  address!: Address;
  nextOfKin!: NextOfKin;
  editingPatient = false;
  infectionStatus!: String;

  errorMessage!: String;

  ngOnInit() {
    const patientId = this.activatedRoute.snapshot.paramMap.get('patientId');

    if (patientId) {
      this.patientFileService.getPatientFile(patientId).subscribe({
        next: (patientFile: PatientFile) => {
          this.patient = patientFile.patientInfo;
          this.address = this.patient.address;
          this.nextOfKin = this.patient.nextOfKin;
          this.infectionStatus = patientFile.infectionStatus.toString().replace(/_/g, ' ');;
        },
        error: (error) => {
          this.errorMessage = "Patient not found";
        },
      });
    }
  }

  editPatient() {
    this.editingPatient = true;
  }

  cancelEdit() {
    this.editingPatient = false;
  }
 
}
