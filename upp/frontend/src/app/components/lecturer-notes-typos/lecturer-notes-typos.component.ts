import { Component, OnInit } from '@angular/core';
import {MatDialog, MatTableDataSource} from '@angular/material';
import {Book} from '../../model/book';
import {BookService} from '../../services/book.service';
import {UserService} from '../../services/user.service';
import {LecturerNotesTyposDialogComponent} from '../lecturer-notes-typos-dialog/lecturer-notes-typos-dialog.component';

@Component({
  selector: 'app-lecturer-notes-typos',
  templateUrl: './lecturer-notes-typos.component.html',
  styleUrls: ['./lecturer-notes-typos.component.css']
})
export class LecturerNotesTyposComponent implements OnInit {

  displayedColumns: string[] = ['id', 'title', 'synopsis', 'isbn', 'pages', 'review'];
  dataSource = new MatTableDataSource<Book>();
  constructor(private bookService: BookService,
              private userService: UserService,
              public dialog: MatDialog) { }

  ngOnInit() {
    this.getByUsername();
  }

  async getByUsername() {
    const c = await this.bookService.getBooksForLecturer(this.userService.getLoggedUser().username);
    this.dataSource = new MatTableDataSource(c);
    console.log(c);
  }

  openDialog(element) {
    window.location.href = element.document.fileUrl;
    this.dialog.open(LecturerNotesTyposDialogComponent, {width: '50%', height: '50%', data: element});
  }

}
