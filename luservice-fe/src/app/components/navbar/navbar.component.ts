import { Component, OnInit, Injectable, OnDestroy } from '@angular/core';
import { AuthService } from 'src/app/services/auth.service';
import { Router, NavigationStart } from '@angular/router';
import { BehaviorSubject, Observable, Subscription } from 'rxjs';
import { Role } from 'src/app/shared/enums/role';
import { CartService } from 'src/app/services/cart.service';

@Component({
  selector: 'app-navbar',
  templateUrl: './navbar.component.html',
  styleUrls: ['./navbar.component.css']
})

export class NavbarComponent implements OnInit {

  roleSubscription: Subscription;
  userRole: string;
  isEditor = false;
  isAdmin = false;
  roles = [];

  constructor(private authService: AuthService, private router: Router, private cartService: CartService) {
  }

  ngOnInit() {
    this.roles = this.authService.getUserRoles();
    if (this.roles.length > 0) {
      this.roles.forEach(element => {
        if (element.name == Role.Editor.toString()) {
          this.isEditor = true;
        }

        if (element.name == Role.Admin.toString()) {
          this.isAdmin = true;
        }

      });
    }


  }


  logout() {
    this.authService.logout();
    this.cartService.empty();
    this.router.navigate(['']);
  }

}
