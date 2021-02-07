import { Component, OnInit } from '@angular/core';
import {MatDialog, MatTableDataSource} from '@angular/material';
import {Book} from '../../model/book';
import {BookService} from '../../services/book.service';
import {UserService} from '../../services/user.service';
import {BookReviewDialogComponent} from '../book-review-dialog/book-review-dialog.component';
import {BookReviewDialogBetaReadersComponent} from '../book-review-dialog-beta-readers/book-review-dialog-beta-readers.component';
import {NeedMoreWorkDialogComponent} from '../need-more-work-dialog/need-more-work-dialog.component';
import {EditorHaveSuggestionDialogComponent} from '../editor-have-suggestion-dialog/editor-have-suggestion-dialog.component';


@Component({
  selector: 'app-book-review',
  templateUrl: './book-review.component.html',
  styleUrls: ['./book-review.component.css']
})
export class BookReviewComponent implements OnInit {

  displayedColumns: string[] = ['id', 'title', 'synopsis', 'isbn', 'pages', 'review', 'betaReaders', '2ndreview', 'suggestions'];
  dataSource = new MatTableDataSource<Book>();
  constructor(private bookService: BookService,
              private userService: UserService,
              public dialog: MatDialog) { }

  ngOnInit() {
    this.getByUsername();
  }

  async getByUsername() {
    const c = await this.bookService.getBooksForReview(this.userService.getLoggedUser().username);
    this.dataSource = new MatTableDataSource(c);
    console.log(c);
  }

  openDialog(element) {
    window.location.href = element.document.fileUrl;
    this.dialog.open(BookReviewDialogComponent, {width: '50%', height: '50%', data: element});
  }
  openDialog2(element) {
    this.dialog.open(BookReviewDialogBetaReadersComponent, {width: '50%', height: '50%', data: element});
  }
  openDialog3(element) {
    window.location.href = element.document.fileUrl;
    this.dialog.open(NeedMoreWorkDialogComponent, {width: '50%', height: '50%', data: element});
  }
  openDialog4(element) {
    window.location.href = element.document.fileUrl;
    this.dialog.open(EditorHaveSuggestionDialogComponent, {width: '50%', height: '50%', data: element});
  }
}
