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
import { BookService } from 'src/app/services/book.service';
import { CartService } from 'src/app/services/cart.service';
import { CartItem } from 'src/app/shared/models/cart-item';

@Component({
  selector: 'app-dashboard',
  templateUrl: './dashboard.component.html',
  styleUrls: ['./dashboard.component.css']
})
export class DashboardComponent implements OnInit, OnDestroy {
  books: Book[] = [];
  private subscription: Subscription;
  form: FormGroup;
  matcher = new MyErrorStateMatcher();
  roles;

  constructor(private router: Router, private formBuilder: FormBuilder, private authService: AuthService,
    private snackBarService: SnackBarService, private userService: UserService, private bookService: BookService, private cartService: CartService) {
    this.form = this.formBuilder.group({
      'quantity': ['']
    });

  }

  ngOnInit() {
    this.subscription = this.cartService.items.subscribe();
    this.resetForm();
    this.getBooks();
    const userId = localStorage.getItem("id");
    this.authService.getUserRoles();
    console.log(this.authService.getUserRoles());

  }

  ngOnDestroy() {
    this.subscription.unsubscribe();
  }

  getBooks() {
    this.bookService.getBooks().subscribe(
      data => {
        console.log(data);
        this.books = data;
      },
      (err: HttpErrorResponse) => {
        this.snackBarService.showMessage(err.message);
      }
    );
  }

  addToCart(book: Book) {
    console.log(book);
    console.log(this.quantity.value);

    const cartItem = new CartItem(uuid(), book.price * this.quantity.value, this.quantity.value, book);
    this.cartService.addItem(cartItem);
    this.snackBarService.showMessage('Added to shopping cart.');
    this.resetForm();
  }

  get quantity(): AbstractControl {
    return this.form.get('quantity')
  }

  resetForm() {
    this.form.reset();
    this.quantity.setValue(1);
  }
}

