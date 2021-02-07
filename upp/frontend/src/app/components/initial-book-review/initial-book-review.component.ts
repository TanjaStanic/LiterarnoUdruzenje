import {Component, OnInit, ViewChild} from '@angular/core';
import {BookService} from '../../services/book.service';
import {MatDialog, MatTableDataSource} from '@angular/material';
import {Book} from '../../model/book';
import {UserService} from '../../services/user.service';
import {InitialBookReviewDialogComponent} from '../initial-book-review-dialog/initial-book-review-dialog.component';


@Component({
  selector: 'app-initial-book-review',
  templateUrl: './initial-book-review.component.html',
  styleUrls: ['./initial-book-review.component.css']
})
export class InitialBookReviewComponent implements OnInit {

  displayedColumns: string[] = ['id', 'title', 'synopsis', 'review'];
  dataSource = new MatTableDataSource<Book>();
  constructor(private bookService: BookService,
              private userService: UserService,
              public dialog: MatDialog) {

  }

  ngOnInit() {
    this.getByUsername();
  }

  async getByUsername() {
    const c = await this.bookService.getBooksForInitialReview(this.userService.getLoggedUser().username);
    this.dataSource = new MatTableDataSource(c);
    console.log(c);
  }

  openDialog(element) {
    this.dialog.open(InitialBookReviewDialogComponent, {width: '50%', height: '50%', data: element});
  }
}
