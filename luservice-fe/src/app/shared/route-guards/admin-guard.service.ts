import { Injectable } from '@angular/core';
import { CanActivate, Router, ActivatedRouteSnapshot } from '@angular/router';
import { AuthService } from 'src/app/services/auth.service';
import { Role } from '../enums/role';

@Injectable({
  providedIn: 'root'
})
export class AdminGuardService implements CanActivate {

  constructor(
    private router: Router,
    private authenticationService: AuthService
  ) { }

  canActivate() {
    let flag;

    if (!this.authenticationService.isAuthenticated()) {
      this.router.navigateByUrl('');
      return false;
    }
    const roles = this.authenticationService.getUserRoles();
    if (roles.length > 0) {
      console.log(roles);
      roles.forEach(element => {
        if (element == Role.Admin.toString()) {

          flag = true;
        }
      });

    } else {
      console.log('zasto ides ovamo');
      this.router.navigateByUrl('');
      return false;
    }

    if (flag) {
      return true;
    } else {
      return false;
    }


  }


}
