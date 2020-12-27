import { Component, OnInit } from '@angular/core';
import { FormGroup, FormBuilder, Validators, AbstractControl } from '@angular/forms';
import { MyErrorStateMatcher } from 'src/app/shared/validators/error-state-matcher';
import { Router } from '@angular/router';
import { AuthService } from 'src/app/services/auth.service';
import { SnackBarService } from 'src/app/services/snack-bar.service';

@Component({
  selector: 'app-forgot-password',
  templateUrl: './forgot-password.component.html',
  styleUrls: ['./forgot-password.component.css']
})
export class ForgotPasswordComponent implements OnInit {

  emailForm: FormGroup;
  matcher = new MyErrorStateMatcher();


  constructor(private router: Router, private formBuilder: FormBuilder, private authService: AuthService, private snackBarService: SnackBarService) {
    this.emailForm = this.formBuilder.group({
      'email': ['', Validators.compose([Validators.required, Validators.email])]
    });

  }

  ngOnInit() {
  }


  get email(): AbstractControl {
    return this.emailForm.get('email')
  }


  cancel() {
    this.emailForm.reset();
    this.router.navigate(['/login'])
  }

}
