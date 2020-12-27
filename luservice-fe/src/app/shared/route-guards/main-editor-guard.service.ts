import { Injectable } from '@angular/core';
import { Router } from '@angular/router';
import { AuthService } from 'src/app/services/auth.service';
import { Role } from '../enums/role';

@Injectable({
  providedIn: 'root'
})
export class MainEditorGuardService {

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
        if (element.name == Role.MainEditor.toString()) {
          flag = true;
        }
      });

      if (flag == true) {
        return true;
      } else {
        return false;
      }


    }
    console.log('zasto ides ovamo');
    this.router.navigateByUrl('');
    return false;
  }
}
