import { Component, OnInit } from '@angular/core';
import {MatDialog, MatTableDataSource} from '@angular/material';
import {Book} from '../../model/book';
import {BookService} from '../../services/book.service';
import {UserService} from '../../services/user.service';
import {BetaReaderCommentDialogComponent} from '../beta-reader-comment-dialog/beta-reader-comment-dialog.component';

@Component({
  selector: 'app-beta-reader-comment',
  templateUrl: './beta-reader-comment.component.html',
  styleUrls: ['./beta-reader-comment.component.css']
})
export class BetaReaderCommentComponent implements OnInit {

  displayedColumns: string[] = ['id', 'title', 'synopsis', 'isbn', 'pages', 'review'];
  dataSource = new MatTableDataSource<Book>();
  constructor(private bookService: BookService,
              private userService: UserService,
              public dialog: MatDialog) { }

  ngOnInit() {
    this.getByUsername();
  }

  async getByUsername() {
    const c = await this.bookService.getBooksForBetaReader(this.userService.getLoggedUser().username);
    this.dataSource = new MatTableDataSource(c);
    console.log(c);
  }

  openDialog(element) {
    window.location.href = element.document.fileUrl;
    this.dialog.open(BetaReaderCommentDialogComponent, {width: '50%', height: '50%', data: element});
  }
}
