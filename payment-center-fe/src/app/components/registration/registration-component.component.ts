import { Component, OnInit } from '@angular/core';
import { FormGroup, FormBuilder, Validators, FormControl, FormGroupDirective, NgForm, AbstractControl } from '@angular/forms';
import { ActivatedRoute, Router } from '@angular/router';
import { AuthService } from 'src/app/services/auth.service';
import { MyErrorStateMatcher } from 'src/app/shared/validators/error-state-matcher';
import { SnackBarService } from 'src/app/services/snack-bar.service';
import { HttpErrorResponse } from '@angular/common/http';
import { RegisterUserDTO } from 'src/app/shared/models/register-user-dto';
import { checkIfPasswordMatch } from 'src/app/shared/validators/validators';

@Component({
  selector: 'app-registration-component',
  templateUrl: './registration-component.component.html',
  styleUrls: ['./registration-component.component.css']
})
export class RegistrationComponentComponent implements OnInit {

  matcher = new MyErrorStateMatcher();


  form: FormGroup;

  constructor(
    private snackBarService: SnackBarService,
    private formBuilder: FormBuilder,
    private route: ActivatedRoute,
    private router: Router,
    private authService: AuthService
  ) {
    this.form = this.formBuilder.group({
      'email': ['', Validators.compose([Validators.required])],
      'password': ['', Validators.compose([Validators.required])],
      'taxIdentificationNumber': ['', Validators.compose([Validators.required])],
      'companyRegistrationNumber': ['', Validators.compose([Validators.required])],
      'name': ['', Validators.compose([Validators.required])],
      'passwordConfirm': ['', Validators.compose([Validators.required])]
    }, { validator: checkIfPasswordMatch });
  }

  ngOnInit() {

  }


  register() {
    const userDto: RegisterUserDTO = this.form.value;
    this.authService.register(userDto).subscribe(
      data => {
        window.location.href = data;
      },
      (err: HttpErrorResponse) => {
        console.log(err)
        this.snackBarService.showMessage(err.error);
      }
    );
  }

  get email(): AbstractControl {
    return this.form.get('email');
  }
  get name(): AbstractControl {
    return this.form.get('name');
  }
  get companyRegistrationNumber(): AbstractControl {
    return this.form.get('companyRegistrationNumber');
  }

  get taxIdentificationNumber(): AbstractControl {
    return this.form.get('taxIdentificationNumber');
  }

  get password(): AbstractControl {
    return this.form.get('password');
  }

  get passwordConfirm(): AbstractControl {
    return this.form.get('passwordConfirm');
  }
}
