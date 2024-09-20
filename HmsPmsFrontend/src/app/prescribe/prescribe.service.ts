import { Injectable } from '@angular/core';
import { AuthenticationService } from '../shared/services/authentication.service';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { PrescribeMedicationRequest } from '../shared/model/patient-file.model';
import { Observable, map } from 'rxjs';
import { environment } from 'src/environments/environment';

@Injectable({
  providedIn: 'root'
})
export class PrescribeService {

  constructor(
    private authenticationService: AuthenticationService,
    private httpClient: HttpClient
  ) { }

  public postPrescription(prescription :PrescribeMedicationRequest): Observable<void> {
    return this.postRequest<void>("/prescribe", prescription)
      .pipe(
        map((response: HttpResponse<void>) => {
          if(response.status != 200) {
            const responseBody = response.body;

            if(responseBody == null) {
              throw new Error("Invalid credentials");
            } else {
              throw new Error(responseBody);
            }
          }
        })
      )
  }

  private postRequest<T>(endpoint: string, body: any): Observable<HttpResponse<T>> {
    return this.httpClient.post<T>(
      environment.backendUrl + endpoint, 
      body,
      this.authenticationService.getAuthenticationOptions()
    );
  }

}
