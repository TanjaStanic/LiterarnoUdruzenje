import { Component, OnInit } from '@angular/core';
import { FormBuilder } from '@angular/forms';
import { Router } from '@angular/router';
import { AuthService } from 'src/app/services/auth.service';
import { SnackBarService } from 'src/app/services/snack-bar.service';
import { UserService } from 'src/app/services/user.service';
import { UserSubscription } from 'src/app/shared/models/user-subscruption';

@Component({
  selector: 'app-profile',
  templateUrl: './profile.component.html',
  styleUrls: ['./profile.component.css']
})
export class ProfileComponent implements OnInit {
  roles = [];
  subscriptionActiveOrInitiated = false;
  activeSubscription: any;

  constructor(private router: Router, private formBuilder: FormBuilder, private authService: AuthService,
    private snackBarService: SnackBarService, private userService: UserService) { }

  ngOnInit() {
    const userId = localStorage.getItem("id");
    this.roles = this.authService.getUserRoles();
    this.userService.getUserSubscription(userId).subscribe(
      data => {
        this.activeSubscription = data;
        this.subscriptionActiveOrInitiated = true;

      },
      err => {

      }
    );
  }

}
