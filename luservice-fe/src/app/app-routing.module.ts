import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';

import { LoginComponent } from './components/login/login.component';
import { ChangePasswordComponent } from './components/change-password/change-password.component';
import { WelcomeComponent } from './components/welcome/welcome.component';
import { RegistrationComponentComponent } from './components/registration/registration-component.component';
import { ResetPasswordComponent } from './components/reset-password/reset-password.component';
import { ForgotPasswordComponent } from './components/forgot-password/forgot-password.component';
import { AdminPanelComponent } from './components/admin-panel/admin-panel.component';
import { AuthGuardService } from './shared/route-guards/auth-guard.service';
import { AdminGuardService } from './shared/route-guards/admin-guard.service';
import { DashboardComponent } from './components/dashboard/dashboard.component';
import { UserGuardService } from './shared/route-guards/user-guard.service';
import { ConfirmRegistrationComponent } from './components/registration/confirm-registration/confirm-registration.component';
import { ShoppingCartComponent } from './components/shopping-cart/shopping-cart.component';
import { ProfileComponent } from './components/profile/profile.component';
import { SuccessComponent } from './components/success/success.component';
import { ErrorComponent } from './components/error/error.component';
import { FailComponent } from './components/fail/fail.component';
import { CancelComponent } from './components/cancel/cancel.component';


const routes: Routes = [
  { path: '', component: WelcomeComponent, pathMatch: 'full' },
  { path: 'register', component: RegistrationComponentComponent, pathMatch: 'full' },
  { path: 'login', component: LoginComponent, pathMatch: 'full' },
  { path: 'reset-password', component: ResetPasswordComponent, pathMatch: 'full' },
  { path: 'forgot-password', component: ForgotPasswordComponent, pathMatch: 'full' },
  { path: 'change-password', component: ChangePasswordComponent, pathMatch: 'full' },
  { path: 'activate-account/:token', component: ConfirmRegistrationComponent, pathMatch: 'full' },
  { path: 'admin', component: AdminPanelComponent, pathMatch: 'full', canActivate: [AdminGuardService] },
  { path: 'dashboard', component: DashboardComponent, pathMatch: 'full' },
  { path: 'cart', component: ShoppingCartComponent, pathMatch: 'full' },
  { path: 'profile', component: ProfileComponent, pathMatch: 'full', canActivate: [AuthGuardService] },
  { path: 'success', component: SuccessComponent, pathMatch: 'full' },
  { path: 'error', component: ErrorComponent, pathMatch: 'full' },
  { path: 'fail', component: FailComponent, pathMatch: 'full' },
  { path: 'cancel', component: CancelComponent, pathMatch: 'full' }

];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
