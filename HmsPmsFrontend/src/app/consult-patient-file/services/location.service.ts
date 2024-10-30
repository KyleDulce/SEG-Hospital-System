import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { map, Observable } from 'rxjs';
import {
  LocationRequest,
  LocationTrackingRequest,
} from 'src/app/shared/model/location.model';
import { AuthenticationService } from 'src/app/shared/services/authentication.service';
import { environment } from 'src/environments/environment';

@Injectable({
  providedIn: 'root',
})
export class LocationService {
  constructor(
    private httpClient: HttpClient,
    private authenticationService: AuthenticationService
  ) {}

  public addLocation(locationRequest: LocationRequest): Observable<string> {
    return this.httpClient
      .post(environment.backendUrl + '/location', locationRequest, {
        ...this.authenticationService.getAuthenticationOptions(),
        observe: 'response',
        responseType: 'text',
      })
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

  public updateLocation(
    locationId: string,
    locationRequest: LocationRequest
  ): Observable<void> {
    return this.httpClient
      .put<void>(
        environment.backendUrl + '/location/' + locationId,
        locationRequest,
        this.authenticationService.getAuthenticationOptions()
      )
      .pipe(
        map((response) => {
          if (response.status != 200) {
            throw new Error('Invalid credentials');
          }
        })
      );
  }

  public updatePatientLocation(
    locationId: string,
    patientId: string,
    trackingRequest: LocationTrackingRequest
  ): Observable<void> {
    return this.httpClient
      .post<void>(
        `${environment.backendUrl}/location/${locationId}/${patientId}`,
        trackingRequest,
        this.authenticationService.getAuthenticationOptions()
      )
      .pipe(
        map((response) => {
          if (response.status != 200) {
            throw new Error('Invalid credentials');
          }
        })
      );
  }
}
