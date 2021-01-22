import {Component, Inject, OnInit} from '@angular/core';
import {UserService} from '../../services/user.service';
import {RegistrationApplicationResponseService} from '../../services/registration-application-response.service';
import {MatDialog, MatTableDataSource} from '@angular/material';
import {RegistrationApplicationResponse} from '../../model/registrationApplicationResponse';
import {Router} from '@angular/router';
import {DOCUMENT} from '@angular/common';
import {ReaderDialogComponent} from '../reader-dialog/reader-dialog.component';
import {WriterReviewDialogComponent} from '../writer-review-dialog/writer-review-dialog.component';

@Component({
  selector: 'app-writer-review',
  templateUrl: './writer-review.component.html',
  styleUrls: ['./writer-review.component.css']
})
export class WriterReviewComponent implements OnInit {


  count = 0;
  displayedColumns: string[] = ['id', 'date', 'review'];
  dataSource = new MatTableDataSource<RegistrationApplicationResponse>();
  constructor(private userService: UserService,
              private rapService: RegistrationApplicationResponseService,
              public dialog: MatDialog) { }

  ngOnInit() {
    this.getByUsername();
  }

  async getByUsername() {
    const c = await this.rapService.getListForReviews(this.userService.getLoggedUser().username);
    this.dataSource = new MatTableDataSource(c);
    console.log(c);
  }

  openDialog(element) {
    for (const c of element.registrationApplication.documents) {
      this.downloadURL(c.fileUrl);
    }
    this.dialog.open(WriterReviewDialogComponent, {width: '50%', height: '50%'});
  }

 downloadURL(url) {
    const hiddenIFrameID = 'hiddenDownloader' + this.count++;
    const iframe = document.createElement('iframe');
    iframe.id = hiddenIFrameID;
    iframe.style.display = 'none';
    document.body.appendChild(iframe);
    iframe.src = url;
  }

}
