import { Component } from '@angular/core';
import { MatDialog } from '@angular/material/dialog';

@Component({
  selector: 'app-patient-file',
  templateUrl: './patient-file.component.html',
  styleUrls: ['./patient-file.component.css'],
})
export class PatientFileComponent {
  constructor(private matDialog: MatDialog) {}
}
