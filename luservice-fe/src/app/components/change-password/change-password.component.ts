import { Component, OnInit } from '@angular/core';
import { FormGroup, AbstractControl, FormBuilder, Validators } from '@angular/forms';
import { MyErrorStateMatcher } from 'src/app/shared/validators/error-state-matcher';
import { Router } from '@angular/router';
import { AuthService } from 'src/app/services/auth.service';

import { checkIfPasswordMatch } from 'src/app/shared/validators/validators';
import { SnackBarService } from 'src/app/services/snack-bar.service';
import { Role } from 'src/app/shared/enums/role';
import { ChangePassword } from 'src/app/shared/models/change-password';

@Component({
  selector: 'app-change-password',
  templateUrl: './change-password.component.html',
  styleUrls: ['./change-password.component.css']
})
export class ChangePasswordComponent implements OnInit {

  form: FormGroup;
  matcher = new MyErrorStateMatcher();
  roles = [];


  constructor(private router: Router, private formBuilder: FormBuilder, private authService: AuthService, private snackBarService: SnackBarService) {
    this.form = this.formBuilder.group({
      'oldPassword': ['', Validators.compose([Validators.required])],
      'newPassword': ['', Validators.compose([Validators.required])],
      'newPasswordConfirm': ['', Validators.compose([Validators.required])]
    },
      { validators: [checkIfPasswordMatch] }
    );

  }

  ngOnInit() {
  }

  get oldPassword(): AbstractControl {
    return this.form.get('oldPassword')
  }

  get newPassword(): AbstractControl {
    return this.form.get('newPassword')
  }

  get newPasswordConfirm(): AbstractControl {
    return this.form.get('newPasswordConfirm')
  }

  changePassword() {
    const changePassword = new ChangePassword(this.oldPassword.value, this.newPassword.value);
    const userId = localStorage.getItem('id');
    this.authService.changePassword(changePassword, userId).subscribe(
      data => {
        this.form.reset();
        this.snackBarService.showMessage("Password changed successfully!")
        this.roles = this.authService.getUserRoles();
        console.log(this.roles);
        let flag = false;
        if (this.roles.length > 0) {
          this.roles.forEach(element => {
            if (element.name == Role.Admin.toString()) {
              flag = true;
            }

          });

          if (flag == false) {
            this.router.navigateByUrl('dashboard');

          } else {
            this.router.navigate(['admin']);
          }

        }
      },
      error => {
        this.snackBarService.showMessage("Password could not be changed. Please try again.")
      }
    );
  }

  cancel() {
    this.form.reset();
  }

}
