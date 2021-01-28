import { Injectable } from '@angular/core';
import { Observable, BehaviorSubject } from 'rxjs';
import { HttpClient } from '@angular/common/http';
import { environment } from 'src/environments/environment';

import { JwtHelperService } from "@auth0/angular-jwt";
import { UserService } from './user.service';
import { ChangePassword } from '../shared/models/change-password';
import { ResetPassword } from '../shared/models/reset-password';
import { UserCredentials } from '../shared/models/user-credentials';
import { RegisterUserDTO } from '../shared/models/register-user-dto';
import { Role } from '../shared/enums/role';

const rolesLS: Role[] = JSON.parse(localStorage.getItem('roles'))
@Injectable({
  providedIn: 'root'
})
export class AuthService {
  userRole = '';

  roles = [];

  constructor(private http: HttpClient, private userService: UserService) { }

  isAuthenticated(): boolean {
    const jwtHelper = new JwtHelperService();
    const token = localStorage.getItem('token');
    if (!token) {
      return false;
    }
    return !jwtHelper.isTokenExpired(token);
  }


  getUserRoles() {
    const jwtHelper = new JwtHelperService();
    const tokenPayload = jwtHelper.decodeToken(localStorage.getItem('token'));
    if (tokenPayload == null) {
      this.roles = [];
      return [];
    }

    this.roles = tokenPayload['roles'];
    return this.roles;
  }

  public getUserId(): number {
    const userId = +localStorage.getItem('id');
    return userId;
  }

  logout() {
    this.roles = [];
    localStorage.clear();
  }

  register(userCredentials: RegisterUserDTO): Observable<any> {
    return this.http.post(`${environment.baseUrl}auth/register`, userCredentials, { responseType: 'text' });
  }

  login(userCredentials: UserCredentials): Observable<any> {
    return this.http.post(`${environment.baseUrl}auth/authenticate`, userCredentials);
  }
  confirmAccount(token: string): Observable<any> {
    return this.http.get(`${environment.baseUrl}auth/verify-email/${token}`);
  }
  changePassword(changePassword: ChangePassword, userId: string): Observable<any> {
    return this.http.put(`${environment.baseUrl}${userId}/change-password`, changePassword);
  }

  resetPassword(resetPassword: ResetPassword, token: string): Observable<any> {
    return this.http.put(`${environment.baseUrl}auth/resetPassword?token=` + token, resetPassword)
  }

  deactivateAccount(userId: number): Observable<any> {
    return this.http.put(`${environment.baseUrl}accounts/${userId}/deactivate`, null);
  }

}
