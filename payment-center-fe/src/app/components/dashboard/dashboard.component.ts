import { Component, OnInit, OnDestroy } from '@angular/core';
import { SnackBarService } from 'src/app/services/snack-bar.service';
import { HttpErrorResponse } from '@angular/common/http';
import { AuthService } from 'src/app/services/auth.service';
import { FormGroup, FormBuilder, AbstractControl, Validators } from '@angular/forms';
import { Router, ActivatedRoute } from '@angular/router';
import { Subscription } from 'rxjs';
import uuid from 'uuidv4';
import { UserService } from 'src/app/services/user.service';
import { MyErrorStateMatcher } from 'src/app/shared/validators/error-state-matcher';
import { Book } from 'src/app/shared/models/book';
import { CartService } from 'src/app/services/cart.service';
import { CartItem } from 'src/app/shared/models/cart-item';

@Component({
  selector: 'app-dashboard',
  templateUrl: './dashboard.component.html',
  styleUrls: ['./dashboard.component.css']
})
export class DashboardComponent implements OnInit {

  roles;
  authenticated = false;

  constructor(private router: Router, private formBuilder: FormBuilder, private authService: AuthService,
    private snackBarService: SnackBarService, private userService: UserService, private cartService: CartService) {


  }

  ngOnInit() {

    this.roles = this.authService.getUserRoles();
    const userId = localStorage.getItem("id");

  }



}

