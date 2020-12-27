import { Component, OnInit } from '@angular/core';
import { AuthService } from 'src/app/services/auth.service';
import { Router, ActivatedRoute } from '@angular/router';
import { SnackBarService } from 'src/app/services/snack-bar.service';
import { HttpErrorResponse } from '@angular/common/http';

@Component({
  selector: 'app-confirm-registration',
  templateUrl: './confirm-registration.component.html',
  styleUrls: ['./confirm-registration.component.css']
})
export class ConfirmRegistrationComponent implements OnInit {

  confirmed: boolean;
  constructor(private router: Router, private authService: AuthService,
    private activatedRoute: ActivatedRoute, private snackBarService: SnackBarService) { }

  ngOnInit() {
    this.confirmed = false;
  }

  confirm() {

    const token = this.activatedRoute.snapshot.paramMap.get('token');
    console.log(token);
    this.authService.confirmAccount(token).subscribe(
      (res: any) => {
        this.confirmed = true;
        this.snackBarService.showMessage("Account confirmed, you can now sign in.");
        this.router.navigateByUrl('login');

      },
      (error: HttpErrorResponse) => {
        this.snackBarService.showMessage("Error occurred while trying to confirm your account.");
      }
    );
  }


}
