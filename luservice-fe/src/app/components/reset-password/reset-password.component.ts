import { Component, OnInit } from '@angular/core';
import { FormGroup, FormBuilder, Validators, AbstractControl } from '@angular/forms';
import { MyErrorStateMatcher } from 'src/app/shared/validators/error-state-matcher';
import { Router, ActivatedRoute } from '@angular/router';
import { AuthService } from 'src/app/services/auth.service';
import { checkIfPasswordMatch } from 'src/app/shared/validators/validators';

import { SnackBarService } from 'src/app/services/snack-bar.service';
import { ResetPassword } from 'src/app/shared/models/reset-password';

@Component({
  selector: 'app-reset-password',
  templateUrl: './reset-password.component.html',
  styleUrls: ['./reset-password.component.css']
})
export class ResetPasswordComponent implements OnInit {

  resetForm: FormGroup;
  private token: string;
  matcher = new MyErrorStateMatcher();


  constructor(private router: Router, private formBuilder: FormBuilder, private authService: AuthService, private snackBarService: SnackBarService, private activatedRoute: ActivatedRoute) {
    this.resetForm = this.formBuilder.group({
      'newPassword': ['', Validators.compose([Validators.required])],
      'newPasswordConfirm': ['', Validators.compose([Validators.required])]
    },
      { validators: [checkIfPasswordMatch] }
    );

  }

  ngOnInit() {
    this.activatedRoute.queryParams.subscribe(params => {
      this.token = params['token'];
    })
  }

  get newPassword(): AbstractControl {
    return this.resetForm.get('newPassword')
  }

  get newPasswordConfirm(): AbstractControl {
    return this.resetForm.get('newPasswordConfirm')
  }

  resetPassword() {
    const resetEmail = localStorage.getItem('resetEmail');
    const resetPassword = new ResetPassword(resetEmail, this.newPassword.value)
    this.authService.resetPassword(resetPassword, this.token).subscribe(
      data => {
        this.snackBarService.showMessage("Password changed successfully!")
        this.router.navigate(['login'])
      },
      error => {
        this.snackBarService.showMessage("Password could not be changed. Please try again.")
      }
    );
  }

  cancel() {
    this.resetForm.reset();
  }

}
