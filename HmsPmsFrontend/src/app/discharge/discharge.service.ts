import { HttpClient, HttpResponse } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { AuthenticationService } from '../shared/services/authentication.service';
import { Observable, OperatorFunction, map } from 'rxjs';
import { environment } from 'src/environments/environment';
import { AdmissionInfoResponse, DischargeRequest } from '../shared/model/admission.model';

@Injectable({
  providedIn: 'root'
})
export class DischargeService {

  constructor(private httpClient: HttpClient,
    private authenticationService: AuthenticationService) { }

    public getAdmissionInfo(patientId: string): Observable<AdmissionInfoResponse> {
      return this.getRequest<AdmissionInfoResponse>(`/admission/info/${patientId}`)
        .pipe(
          this.mapResponse<AdmissionInfoResponse>()
        )
    }

    public postDischarge(dischargeRequest: DischargeRequest): Observable<void> {
      return this.postRequest<void>("/admission/discharge", dischargeRequest)
        .pipe(
          map((response: HttpResponse<void>) => {
            if(response.status != 200) {
              throw new Error("Invalid credentials");
            }
          })
        )
    }

    private getRequest<T>(endpoint: string): Observable<HttpResponse<T>> {
      return this.httpClient.get<T>(
        environment.backendUrl + endpoint,
        this.authenticationService.getAuthenticationOptions()
      )
    }
  
    private postRequest<T>(endpoint: string, body: any): Observable<HttpResponse<T>> {
      return this.httpClient.post<T>(
        environment.backendUrl + endpoint, 
        body,
        this.authenticationService.getAuthenticationOptions()
      );
    }
  
    private mapResponse<Out>(): OperatorFunction<HttpResponse<Out>, Out> {
      return map((response: HttpResponse<Out>) => {
        if(response.status != 200) {
          throw new Error("Invalid credentials");
        }
  
        const responseBody = response.body;
        
        if(responseBody == null) {
          throw new Error("Unexpected format");
        }
  
        return responseBody;
      })
    }
}
