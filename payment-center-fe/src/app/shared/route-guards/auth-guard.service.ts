import { Injectable } from '@angular/core';
import { Router, ActivatedRouteSnapshot, CanActivate, RouterStateSnapshot } from '@angular/router';
import { AuthService } from 'src/app/services/auth.service';

@Injectable({
  providedIn: 'root'
})
export class AuthGuardService implements CanActivate {

  constructor(
    private router: Router,
    private authenticationService: AuthService
  ) { }

  canActivate() {
    if (!this.authenticationService.isAuthenticated()) {
      alert("ne valja");
      this.router.navigateByUrl('');
      return false;
    }
    return true;
  }
}
