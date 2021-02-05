import { Component, OnInit } from '@angular/core';
import {MatDialog, MatTableDataSource} from '@angular/material';
import {BookService} from '../../services/book.service';
import {UserService} from '../../services/user.service';
import {PlagiarismService} from '../../services/plagiarism.service';
import {BookReviewDialogComponent} from '../book-review-dialog/book-review-dialog.component';
import {EditorPlagiarisedDialogComponent} from '../editor-plagiarised-dialog/editor-plagiarised-dialog.component';
import {EditorPlagiarisedDialogReviewComponent} from '../editor-plagiarised-dialog-review/editor-plagiarised-dialog-review.component';
import {EditorPlagiarisedDialogFinalComponent} from '../editor-plagiarised-dialog-final/editor-plagiarised-dialog-final.component';

@Component({
  selector: 'app-editor-plagiarised-table',
  templateUrl: './editor-plagiarised-table.component.html',
  styleUrls: ['./editor-plagiarised-table.component.css']
})
export class EditorPlagiarisedTableComponent implements OnInit {

  displayedColumns: string[] = ['id', 'writerBook', 'plagiarisedBook', 'make', 'review', 'final'];
  dataSource = new MatTableDataSource<any>();
  count = 0;
  constructor(private bookService: BookService,
              private userService: UserService,
              private plagiarismSerivce: PlagiarismService,
              public dialog: MatDialog) { }

  ngOnInit() {
    this.getByUsername();
  }

  async getByUsername() {
    const c = await this.plagiarismSerivce.getPlagiarismForEditor(this.userService.getLoggedUser().username);
    this.dataSource = new MatTableDataSource(c);
    console.log(c);
  }

  openDialog(element) {
    const book1 = element.plagiarismComplaint.plagiarisedBook.document.fileUrl;
    const book2 = element.plagiarismComplaint.writersBook.document.fileUrl;
    this.downloadURL(book1);
    this.downloadURL(book2);
    this.dialog.open(EditorPlagiarisedDialogComponent, {width: '50%', height: '50%', data: element});
  }

  downloadURL(url) {
    const hiddenIFrameID = 'hiddenDownloader' + this.count++;
    const iframe = document.createElement('iframe');
    iframe.id = hiddenIFrameID;
    iframe.style.display = 'none';
    document.body.appendChild(iframe);
    iframe.src = url;
  }
  openDialog2(element) {
    this.dialog.open(EditorPlagiarisedDialogReviewComponent, {width: '50%', height: '50%', data: element});
  }
  openDialog3(element) {
    this.dialog.open(EditorPlagiarisedDialogFinalComponent, {width: '50%', height: '50%', data: element});
  }
}
