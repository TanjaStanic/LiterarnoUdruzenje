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
    private authService: AuthService,
  ) {
    this.form = this.formBuilder.group({
      'email': ['', Validators.compose([Validators.required])],
      'password': ['', Validators.compose([Validators.required])],
      'city': ['', Validators.compose([Validators.required])],
      'country': ['', Validators.compose([Validators.required])],
      'fullname': ['', Validators.compose([Validators.required])],
      'passwordConfirm': ['', Validators.compose([Validators.required])]
    }, { validator: checkIfPasswordMatch });
  }

  ngOnInit() {

  }


  register() {
    const userDto: RegisterUserDTO = this.form.value;
    this.authService.register(userDto).subscribe(
      data => {
        this.snackBarService.showMessage("We sent you an email to confirm your email address. Please check your email.");
        this.router.navigateByUrl('');
      },
      (err: HttpErrorResponse) => {
        this.snackBarService.showMessage(err.message);
      }
    );
  }

  get email(): AbstractControl {
    return this.form.get('email');
  }
  get fullname(): AbstractControl {
    return this.form.get('fullname');
  }
  get city(): AbstractControl {
    return this.form.get('city');
  }

  get country(): AbstractControl {
    return this.form.get('country');
  }

  get password(): AbstractControl {
    return this.form.get('password');
  }

  get passwordConfirm(): AbstractControl {
    return this.form.get('passwordConfirm');
  }
}
