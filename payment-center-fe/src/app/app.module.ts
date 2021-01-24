import { BrowserModule } from "@angular/platform-browser";
import { NgModule } from "@angular/core";
import { AppRoutingModule } from "./app-routing.module";
import { AppComponent } from "./app.component";
import { BrowserAnimationsModule } from "@angular/platform-browser/animations";
import { RegistrationComponentComponent } from "./components/registration/registration-component.component";
import { AngularMaterialModule } from "./angular-material/angular-material.module";
import { FormsModule, ReactiveFormsModule } from "@angular/forms";
import { HttpClientModule, HTTP_INTERCEPTORS } from "@angular/common/http";
import { FlexLayoutModule } from "@angular/flex-layout";
import { ChangePasswordComponent } from "./components/change-password/change-password.component";
import { TokenInterceptor } from "./shared/interceptors/token-interceptor";

import { LoginComponent } from "./components/login/login.component";
import {
  ErrorStateMatcher,
  ShowOnDirtyErrorStateMatcher
} from "@angular/material";

import { WelcomeComponent } from "./components/welcome/welcome.component";
import { NavbarComponent } from "./components/navbar/navbar.component";
import { ForgotPasswordComponent } from "./components/forgot-password/forgot-password.component";
import { ResetPasswordComponent } from "./components/reset-password/reset-password.component";
import { AdminPanelComponent } from "./components/admin-panel/admin-panel.component";

import { DashboardComponent } from "./components/dashboard/dashboard.component";
import { ConfirmRegistrationComponent } from "./components/registration/confirm-registration/confirm-registration.component";

import { AuthGuardService } from './shared/route-guards/auth-guard.service';
import { UserGuardService } from './shared/route-guards/user-guard.service';
import { ShoppingCartComponent } from './components/shopping-cart/shopping-cart.component';
import { ProfileComponent } from './components/profile/profile.component';
import { ErrorComponent } from './components/error/error.component';
import { PaymentMethodDialogComponent } from './components/admin-panel/payment-method-dialog/payment-method-dialog.component';



@NgModule({
  declarations: [
    AppComponent,
    RegistrationComponentComponent,
    LoginComponent,
    ChangePasswordComponent,
    WelcomeComponent,
    NavbarComponent,
    ForgotPasswordComponent,
    ResetPasswordComponent,
    AdminPanelComponent,
    DashboardComponent,
    ConfirmRegistrationComponent,
    ShoppingCartComponent,
    ProfileComponent,
    ErrorComponent,
    PaymentMethodDialogComponent,
  ],
  imports: [
    BrowserModule,
    AppRoutingModule,
    BrowserAnimationsModule,
    AngularMaterialModule,
    FormsModule,
    HttpClientModule,
    ReactiveFormsModule,
    FlexLayoutModule
  ],
  providers: [
    { provide: ErrorStateMatcher, useClass: ShowOnDirtyErrorStateMatcher },
    {
      provide: HTTP_INTERCEPTORS,
      useClass: TokenInterceptor,
      multi: true
    },
    AuthGuardService,
    UserGuardService,
    UserGuardService

  ],
  bootstrap: [AppComponent],
  entryComponents: [
    PaymentMethodDialogComponent
  ]
})
export class AppModule { }
