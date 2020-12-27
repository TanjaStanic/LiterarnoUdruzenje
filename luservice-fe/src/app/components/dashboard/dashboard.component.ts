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

@Component({
  selector: 'app-dashboard',
  templateUrl: './dashboard.component.html',
  styleUrls: ['./dashboard.component.css']
})
export class DashboardComponent implements OnInit {
  books: Book[] = [];



  form: FormGroup;
  matcher = new MyErrorStateMatcher();
  roles;

  constructor(private router: Router, private formBuilder: FormBuilder, private authService: AuthService,
    private snackBarService: SnackBarService, private userService: UserService, private bookService: BookService) {
    this.form = this.formBuilder.group({
      'quantity': ['']
    });

  }

  ngOnInit() {
    this.resetForm();
    this.getBooks();
    const userId = localStorage.getItem("id");
    this.authService.getUserRoles();
    console.log(this.authService.getUserRoles());

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

