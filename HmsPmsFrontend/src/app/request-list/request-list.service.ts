import { HttpClient, HttpResponse } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { AuthenticationService } from '../shared/services/authentication.service';
import { Observable, OperatorFunction, map, switchMap } from 'rxjs';
import { AdmissionRequest, GetDivisionResponse } from '../shared/model/visualize-divisions.models';
import { environment } from 'src/environments/environment';
import { ChargeNurseDivisionResponse } from '../shared/model/nurse.model';
import { AdmitRequestFull } from '../shared/model/admission.model';
import { PatientFile } from '../shared/model/patient-file.model';

@Injectable({
  providedIn: 'root'
})
export class RequestListService {

  constructor(private httpClient: HttpClient,
    private authenticationService: AuthenticationService) { }

  public getDivisionInformation(): Observable<GetDivisionResponse> {
    return this.getRequest<ChargeNurseDivisionResponse>("/chargeNurse/division")
      .pipe(
        this.mapResponse<ChargeNurseDivisionResponse>(),
        switchMap(response => this.getRequest<GetDivisionResponse>(`/division/${response.divisionId}`)),
        this.mapResponse<GetDivisionResponse>()
      )
  }

  public getPatientInfoAsCombinedAdmitRequest(admitRequest: AdmissionRequest): Observable<AdmitRequestFull> {
    return this.getRequest<PatientFile>(`/consultPatientFile/${admitRequest.patientId}`)
      .pipe(
        this.mapResponse<PatientFile>(),
        map(response => {
          const admitReq: AdmitRequestFull = {
            patientFullName: `${response.patientInfo.firstName} ${response.patientInfo.lastName}`,
            patientId: admitRequest.patientId,
            priority: admitRequest.priority,
            rationale: admitRequest.requestRationale
          }
          return admitReq;
        })
      )
  }

  private getRequest<T>(endpoint: string): Observable<HttpResponse<T>> {
    return this.httpClient.get<T>(
      environment.backendUrl + endpoint,
      this.authenticationService.getAuthenticationOptions()
    )
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
