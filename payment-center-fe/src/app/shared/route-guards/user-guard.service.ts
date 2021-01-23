import { Injectable } from '@angular/core';
import { AuthService } from 'src/app/services/auth.service';
import { Router } from '@angular/router';
import { Role } from '../enums/role';

@Injectable({
  providedIn: 'root'
})
export class UserGuardService {

  constructor(private authenticationService: AuthService, private router: Router) { }

  canActivate() {
    let flag = false;

    if (!this.authenticationService.isAuthenticated()) {
      this.router.navigateByUrl('');
      return false;
    }

    const roles = this.authenticationService.getUserRoles();
    if (roles.length > 0) {

      roles.forEach(element => {
        if (element === Role.Client.toString()) {
          flag = true;
        }
      });


    } else {
      alert('zasto ides ovamo');
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
