import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { map, Observable } from 'rxjs';
import { InfectionRequest } from 'src/app/shared/model/patient-file.model';
import { AuthenticationService } from 'src/app/shared/services/authentication.service';
import { environment } from 'src/environments/environment';

@Injectable({
  providedIn: 'root',
})
export class InfectionService {
  constructor(
    private httpClient: HttpClient,
    private authenticationService: AuthenticationService
  ) {}

  public addInfection(infectionRequest: InfectionRequest): Observable<string> {
    return this.httpClient
      .post(environment.backendUrl + '/infection', infectionRequest, {
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

  public updateInfection(
    infectionId: string,
    infectionRequest: InfectionRequest
  ): Observable<void> {
    return this.httpClient
      .put<void>(
        environment.backendUrl + '/infection/' + infectionId,
        infectionRequest,
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
