import { Component, ViewEncapsulation } from '@angular/core';
import { NavigationEnd, Router } from '@angular/router';
import { filter, map, tap } from 'rxjs';
import { StaffType } from './shared/model/authentication.model';
import { AuthenticationService } from './shared/services/authentication.service';

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.css'],
  encapsulation: ViewEncapsulation.None
})
export class AppComponent {
  title = 'HmsPmsFrontend';
  
  showHeader: Boolean = true;
  staffType?: StaffType;

  constructor(
    private router: Router,
    private authenticationService: AuthenticationService
  ) {
    router.events.pipe(
      // Must be a navigation event 
      filter(routerEvent => routerEvent instanceof NavigationEnd),
      // Cast for compiler
      map(routerEvent => routerEvent as NavigationEnd),
      // Dont show on login
      tap(routeEvent => {
        this.showHeader = routeEvent.urlAfterRedirects !== "/login" 
          && routeEvent.urlAfterRedirects !== "/register"
          && routeEvent.urlAfterRedirects !== "/notFound";
      }),
      tap(() => {
        this.staffType = authenticationService.getStaffType();
      })
    ).subscribe()
  }

  public signout(){
    this.authenticationService.signout()
     .subscribe({
      next: () => {
        this.router.navigate(["/"]);
      },
      error:() => {
        this.router.navigate(["/"]);
      }
     })
  }

  
}
