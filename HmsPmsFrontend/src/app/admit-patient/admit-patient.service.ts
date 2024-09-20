import { HttpClient, HttpResponse } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable, OperatorFunction, map, switchMap, take } from 'rxjs';
import { environment } from 'src/environments/environment';
import { AuthenticationService } from '../shared/services/authentication.service';
import { GetDivisionResponse } from '../shared/model/visualize-divisions.models';
import { ChargeNurseDivisionResponse } from '../shared/model/nurse.model';
import { RequestAdmissionRequest } from '../shared/model/admission.model';
import { AdmitPatientRequest, RejectPatientAdmissionRequest } from '../shared/model/admission.model';

@Injectable({
  providedIn: 'root'
})
export class AdmitPatientService {

  constructor(private httpClient: HttpClient,
    private authenticationService: AuthenticationService) { }

  public getDivisionInformation(): Observable<GetDivisionResponse> {
    return this.getRequest<ChargeNurseDivisionResponse>("/chargeNurse/division")
    .pipe(
      take(1),
      this.mapResponse<ChargeNurseDivisionResponse>(),
      map(response => response.divisionId),
      switchMap(divisionId => this.getRequest<GetDivisionResponse>(`/division/${divisionId}`)),
      this.mapResponse<GetDivisionResponse>(),
    )
  }

  public postAdmitPatient(admitPatientRequest: AdmitPatientRequest): Observable<void> {
    return this.postRequest<void>('/admitPatient', admitPatientRequest)
    .pipe(
      map((response: HttpResponse<void>) => {
        if(response.status != 200) {
          throw new Error("Invalid request");
        }
      })
    )
  }

  public postAdmitFromRequestList(admitPatientRequest: AdmitPatientRequest): Observable<void> {
    return this.postRequest<void>('/requestList/admitPatient', admitPatientRequest)
    .pipe(
      map((response: HttpResponse<void>) => {
        if(response.status != 200) {
          throw new Error("Invalid request");
        }
      })
    )
  }

  public postRejectFromRequestList(rejectRequest: RejectPatientAdmissionRequest): Observable<void> {
    return this.postRequest<void>('/requestList/rejectPatient', rejectRequest)
    .pipe(
      map((response: HttpResponse<void>) => {
        if(response.status != 200) {
          throw new Error("Invalid request");
        }
      })
    )
  }
  public requestPatientAdmission(admissionRequest: RequestAdmissionRequest): Observable<void> {
    return this.postRequest<void>('/requestAdmission', admissionRequest) 
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
