import { HttpClient, HttpResponse } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable, OperatorFunction, map } from 'rxjs';
import { environment } from 'src/environments/environment';
import { AuthenticationService } from '../shared/services/authentication.service';
import { RegisterPatientRequest } from '../shared/model/patient-file.model';

@Injectable({
  providedIn: 'root'
})
export class RegisterPatientService {

  constructor(private httpClient: HttpClient,
    private authenticationService: AuthenticationService) { }

  public register(registerPatientRequest: RegisterPatientRequest) {
    return this.postRequest<void>('/patient/register', registerPatientRequest).pipe(this.mapResponse());
  }

  private postRequest<T>(endpoint: string, body: any): Observable<HttpResponse<T>> {
    return this.httpClient.post<T>(
      environment.backendUrl + endpoint, 
      body,
      this.authenticationService.getAuthenticationOptions()
    );
  }

  private mapResponse(): OperatorFunction<HttpResponse<void>, void> {
    return map((response: HttpResponse<void>) => {
      if(response.status != 200) {
        throw new Error("Invalid credentials");
      }
    })
  }
}
