import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { VisualizeDivisionComponent } from './visualizeDivision/visualize-division/visualize-division.component';
import { LoginComponent } from './login/login/login.component';
import { NotFoundComponent } from './not-found/not-found/not-found.component';
import { ConsultPatientFileComponent } from './consult-patient-file/consult-patient-file.component';
import { RegisterComponent } from './register-staff/register.component';
import { RegisterPatientComponent } from './register-patient/register-patient.component';
import { RequestListComponent } from './request-list/request-list/request-list.component';
import { EditPatientInfoComponent } from './consult-patient-file/patient-file/edit-patient-info/edit-patient-info.component';


const routes: Routes = [

  {path: "login", component: LoginComponent},
  {path: 'patient-file/:patientId', component: ConsultPatientFileComponent},
  {path: 'patient-file', component: ConsultPatientFileComponent},
  {path: "register", component: RegisterComponent},
  {path: "divisions", component: VisualizeDivisionComponent},
  {path: "register-patient", component: RegisterPatientComponent},
  {path: "requests", component: RequestListComponent},
  { path: 'edit-patient/:patientId', component: EditPatientInfoComponent },

  {path: "notFound", component: NotFoundComponent},
  {path: "", redirectTo: "login", pathMatch: "full"}, // DO NOT CHANGE ORDER
  {path: "**", redirectTo: "notFound"} // MUST BE THE LAST PATH IN ARRAY
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
