import { HttpClient, HttpResponse } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable, map } from 'rxjs';
import { environment } from 'src/environments/environment';
import { AuthenticationService } from '../shared/services/authentication.service';
import { GetAllDivisionResponse, GetDivisionResponse } from '../shared/model/visualize-divisions.models';

@Injectable({
  providedIn: 'root'
})
export class VisualizeDivisionService {

  constructor(
    private httpClient: HttpClient,
    private authenticationService: AuthenticationService  
  ) { }

  public getAllDivisions(): Observable<GetAllDivisionResponse> {
    return this.getRequest<GetAllDivisionResponse>(undefined)
      .pipe(
        map(response => {
          if(response.status != 200) {
            throw new Error("Invalid credentials");
          }
  
          const responseBody = response.body;
          
          if(responseBody == null) {
            throw new Error("Unexpected format");
          }

          return responseBody;
        })
      )
  }

  public getDivisionById(divisionId: string): Observable<GetDivisionResponse> {
    return this.getRequest<GetDivisionResponse>(divisionId)
      .pipe(
        map(response => {
          if(response.status != 200) {
            throw new Error("Invalid credentials");
          }
  
          const responseBody = response.body;
          
          if(responseBody == null) {
            throw new Error("Unexpected format");
          }

          return responseBody;
        })
      )
  }

  private getRequest<T>(endpoint: string | undefined): Observable<HttpResponse<T>> {
    return this.httpClient.get<T>(
      environment.backendUrl + "/division" + (endpoint !== undefined? '/' + endpoint : ""),
      this.authenticationService.getAuthenticationOptions()
    )
  }
}
