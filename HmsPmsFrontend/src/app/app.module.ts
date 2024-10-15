import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';
import { AppRoutingModule } from './app-routing.module';

import { MatIconModule } from '@angular/material/icon';
import { MatButtonModule } from '@angular/material/button';
import { MatToolbarModule } from '@angular/material/toolbar';
import { MatInputModule } from '@angular/material/input';
import { MatFormFieldModule } from '@angular/material/form-field';
import { MatSnackBarModule } from '@angular/material/snack-bar';
import { MatTableModule } from '@angular/material/table';
import { MatRippleModule } from '@angular/material/core';
import { MatGridListModule } from '@angular/material/grid-list';
import { MatDividerModule } from '@angular/material/divider';
import { MatDatepickerModule } from '@angular/material/datepicker';
import { MatButtonToggleModule } from '@angular/material/button-toggle';
import { MatNativeDateModule } from '@angular/material/core';
import { MatSlideToggleModule, _MatSlideToggleRequiredValidatorModule,} from '@angular/material/slide-toggle';
import { MatDialogModule } from '@angular/material/dialog';
import { MatSelectModule } from '@angular/material/select';
import { MatOptionModule } from '@angular/material/core';

import { AppComponent } from './app.component';
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';
import { LoginComponent } from './login/login/login.component';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { HttpClientModule } from '@angular/common/http';

import { NotFoundComponent } from './not-found/not-found/not-found.component';
import { PatientFileComponent } from './consult-patient-file/patient-file/patient-file.component';
import { AdmissionRecordsComponent } from './consult-patient-file/patient-file/admission-records/admission-records.component';
import { PrescriptionsComponent } from './consult-patient-file/patient-file/prescriptions/prescriptions.component';
import { PatientInfoComponent } from './consult-patient-file/patient-file/patient-info/patient-info.component';
import { SearchPatientComponent } from './consult-patient-file/search-patient/search-patient.component';
import { ConsultPatientFileComponent } from './consult-patient-file/consult-patient-file.component';
import { RegisterComponent } from './register-staff/register.component';
import { VisualizeDivisionComponent } from './visualizeDivision/visualize-division/visualize-division.component';
import { VisualizeSearchComponent } from './visualizeDivision/visualize-search/visualize-search.component';
import { VisualizeBrowseComponent } from './visualizeDivision/visualize-browse/visualize-browse.component';
import { VisualizeInfoComponent } from './visualizeDivision/visualize-info/visualize-info.component';
import { AdmitPatientDialogComponent } from './admit-patient/admit-patient-dialog/admit-patient-dialog.component';
import { CommonModule } from '@angular/common';
import { RequestPatientAdmissionComponent } from './admit-patient/request-patient-admission/request-patient-admission.component';
import { PrescribeDialogComponent } from './prescribe/prescribe-dialog/prescribe-dialog.component';
import { RequestListComponent } from './request-list/request-list/request-list.component';
import { EditPatientInfoComponent } from './consult-patient-file/patient-file/edit-patient-info/edit-patient-info.component';
import { DischargeDialogComponent } from './discharge/discharge-dialog/discharge-dialog.component';
import { RegisterPatientComponent } from './register-patient/register-patient.component';
import { ModifyInfectionDialogComponent } from './consult-patient-file/modify-infection-dialog/modify-infection-dialog.component';

@NgModule({
  declarations: [
    AppComponent,
    LoginComponent,
    NotFoundComponent,
    PatientFileComponent,
    AdmissionRecordsComponent,
    PrescriptionsComponent,
    PatientInfoComponent,
    SearchPatientComponent,
    ConsultPatientFileComponent,
    RegisterComponent,
    VisualizeDivisionComponent,
    VisualizeSearchComponent,
    VisualizeBrowseComponent,
    VisualizeInfoComponent,
    AdmitPatientDialogComponent,
    RequestPatientAdmissionComponent,
    PrescribeDialogComponent,
    RequestListComponent,
    EditPatientInfoComponent,
    DischargeDialogComponent,
    LoginComponent,
    RegisterPatientComponent,
    ModifyInfectionDialogComponent
  ],
  imports: [
    BrowserModule,
    CommonModule,
    BrowserAnimationsModule,
    AppRoutingModule,
    HttpClientModule,
    FormsModule,
    ReactiveFormsModule,
    MatIconModule,
    MatToolbarModule,
    MatButtonModule,
    MatInputModule,
    MatTableModule,
    MatRippleModule,
    MatFormFieldModule,
    MatSnackBarModule,
    MatGridListModule,
    MatDividerModule,
    MatDatepickerModule,
    MatNativeDateModule,
    MatSlideToggleModule,
    MatButtonToggleModule,
    MatDialogModule,
    MatSelectModule,
    MatOptionModule,
  ],
  providers: [],
  bootstrap: [AppComponent],
})
export class AppModule {}
