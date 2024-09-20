import { HttpClient, HttpResponse } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable, map, tap } from 'rxjs';
import { HttpOptions, LogInRequest, LogInResponse, RegisterRequest, StaffType } from '../model/authentication.model';
import { environment } from 'src/environments/environment';

@Injectable({
  providedIn: 'root'
})
export class AuthenticationService {

  private bearerToken?: String;
  private _username: String = '';
  private staffType?: StaffType;

  constructor(private httpClient: HttpClient) {}

  public get username(): String {
    return this._username;
  }

  public login(requestBody: LogInRequest): Observable<void> {
    return this.httpClient.post<LogInResponse>(environment.backendUrl + "/login", requestBody, {
      withCredentials: false,
      observe: 'response',
      responseType: 'json'
    })
    .pipe(
      tap((data: HttpResponse<LogInResponse>) => {
        if(data.status != 200) {
          throw new Error("Invalid credentials");
        }

        const responseBody = data.body;
        
        if(responseBody == null) {
          throw new Error("Unexpected format");
        }

        this.bearerToken = responseBody.token;
        this._username = responseBody.username;
        this.staffType = responseBody.userType;
      }),
      map(data => undefined)
    )
  }
  public signout(): Observable<void> {
    return this.httpClient.post<void>(environment.backendUrl + "/signout", {}, this.getAuthenticationOptions())
      .pipe(
        tap(() => {
          this.bearerToken = undefined;
          this._username = '';
          this.staffType = undefined;
        }),
        map(() => undefined)
      );
  }
  
  public getAuthenticationOptions(): HttpOptions {
    return {
      withCredentials: false,
      observe: 'response',
      responseType: 'json',
      headers: {
        Authorization: `Bearer ${this.bearerToken}`
      }
    }
  }

  public register(registerBody: RegisterRequest): Observable<void> {
    return this.httpClient.post(environment.backendUrl + "/register", registerBody, {
      withCredentials: false,
      observe: 'response',
      responseType: 'text'
    })
    .pipe(
      tap((response: HttpResponse<any>) => {
        console.log('Response:', response);
        if (response.status == 200) {
          console.log('Registration successful');
        } else {
          throw new Error('Registration failed. Response: ' + response);
        }
      }),
      map(() => undefined)
    );
  }

  public getStaffType(): StaffType | undefined {
    return this.staffType;
  }
  

}
