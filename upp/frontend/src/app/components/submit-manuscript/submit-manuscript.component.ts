import { Component, OnInit } from '@angular/core';
import {MatDialog, MatTableDataSource} from '@angular/material';
import {Book} from '../../model/book';
import {InitialBookReviewDialogComponent} from '../initial-book-review-dialog/initial-book-review-dialog.component';
import {BookService} from '../../services/book.service';
import {UserService} from '../../services/user.service';
import {SubmitManuscriptDialogComponent} from '../submit-manuscript-dialog/submit-manuscript-dialog.component';

@Component({
  selector: 'app-submit-manuscript',
  templateUrl: './submit-manuscript.component.html',
  styleUrls: ['./submit-manuscript.component.css']
})
export class SubmitManuscriptComponent implements OnInit {

  displayedColumns: string[] = ['id', 'title', 'synopsis', 'review'];
  dataSource = new MatTableDataSource<Book>();
  constructor(private bookService: BookService,
              private userService: UserService,
              public dialog: MatDialog) { }

  ngOnInit() {
    this.getByUsername();
  }

  async getByUsername() {
    const c = await this.bookService.getBooksForWriter(this.userService.getLoggedUser().username);
    this.dataSource = new MatTableDataSource(c);
    console.log(c);
  }

  openDialog(element) {
    this.dialog.open(SubmitManuscriptDialogComponent, {width: '50%', height: '50%', data: element});
  }

}
