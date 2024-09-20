import { HttpClient, HttpErrorResponse, HttpHeaders, HttpResponse } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable, catchError, map, tap, throwError } from 'rxjs';
import { environment } from '../../../environments/environment';
import { PatientFile, UpdatePatientInfoRequest } from '../../shared/model/patient-file.model';
import { AuthenticationService } from '../../shared/services/authentication.service';

@Injectable({
  providedIn: 'root',
})
export class PatientFileService {
  constructor(
    private httpClient: HttpClient,
    private authenticationService: AuthenticationService
  ) { }

  errorMsg!: String;

  public getPatientFile(patientId: String): Observable<PatientFile> {

    return this.httpClient
      .get<PatientFile>(
        environment.backendUrl + '/consultPatientFile/' + patientId,
        this.authenticationService.getAuthenticationOptions()
      )
      .pipe(
        map((response) => {
          if (response.status != 200) {
            throw new Error('Invalid credentials');
          }

          const responseBody = response.body;

          if (responseBody == null) {
            throw new Error('Unexpected format');
          }

          return responseBody;
        })
      );
  }

   public updatePatient(patientInfo: UpdatePatientInfoRequest): Observable<HttpResponse<string>>{
    return this.httpClient.put(
      environment.backendUrl + '/consultPatientFile/' + patientInfo.patientId,
      patientInfo, {...this.authenticationService.getAuthenticationOptions(),  observe: 'response',responseType: 'text'}
    );
  }
}
