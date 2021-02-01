import { Component, OnInit, Input } from '@angular/core';
import { Router } from '@angular/router';
import { FormGroup, FormControl, FormBuilder, Validators, FormGroupDirective, NgForm, Form, AbstractControl } from '@angular/forms';
import { AuthService } from 'src/app/services/auth.service';
import { MyErrorStateMatcher } from 'src/app/shared/validators/error-state-matcher';
import { HttpErrorResponse } from '@angular/common/http';
import { SnackBarService } from 'src/app/services/snack-bar.service';
import { Role } from 'src/app/shared/enums/role';
import { UserCredentials } from 'src/app/shared/models/user-credentials';



@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.css']
})
export class LoginComponent implements OnInit {
  form: FormGroup;
  matcher = new MyErrorStateMatcher();
  roles;

  constructor(private router: Router, private formBuilder: FormBuilder, private authService: AuthService, private snackBarService: SnackBarService) {
    this.form = this.formBuilder.group({
      'username': ['', Validators.compose([Validators.required])],
      'password': ['', Validators.compose([Validators.required])],
    });

  }

  ngOnInit() {
  }

  get username(): AbstractControl {
    return this.form.get('username')
  }

  get password(): AbstractControl {
    return this.form.get('password')
  }

  logIn() {
    const userCredentials: UserCredentials = this.form.value;

    this.authService.login(userCredentials).subscribe(
      data => {
        localStorage.setItem('token', data.accessToken);
        localStorage.setItem('id', data.userId.toString());
        this.roles = this.authService.getUserRoles();

        let flag = false;
        if (this.roles != '') {
          this.roles.forEach(element => {
            if (element === Role.Admin) {
              console.log("admin");
              flag = true;
            }

          });

          if (flag == false) {
            this.router.navigateByUrl('client');

          } else {
            this.router.navigateByUrl('admin');
          }

        }
      },
      (err: HttpErrorResponse) => {
        console.log(err);
        this.snackBarService.showMessage(err.error);
      }
    );
  }

  forgotPassword() {
    this.router.navigate(['forgot-password'])
  }

}


