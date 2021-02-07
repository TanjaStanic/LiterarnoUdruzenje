import {Component, Inject, OnInit, ViewChild} from '@angular/core';
import {FormBuilder, FormGroup} from '@angular/forms';
import {GenericFormComponent, GenericFormType} from '../generic-form/generic-form.component';
import {MatDialogRef} from '@angular/material';
import {MAT_DIALOG_DATA} from '@angular/material/dialog';
import {BookService} from '../../services/book.service';
import {UserService} from '../../services/user.service';

@Component({
  selector: 'app-improve-manuscript-dialog',
  templateUrl: './improve-manuscript-dialog.component.html',
  styleUrls: ['./improve-manuscript-dialog.component.css']
})
export class ImproveManuscriptDialogComponent implements OnInit {

  private formFields = [];
  taskId: string;
  manuscriptForm: FormGroup;
  files;
  file;

  constructor(public dialogRef: MatDialogRef<ImproveManuscriptDialogComponent>,
              @Inject(MAT_DIALOG_DATA) public data: any,
              private bookService: BookService,
              private formBuilder: FormBuilder,
              private userService: UserService) {
    const x = bookService.improveManusciprtForm();
    x.subscribe(
      res => {
        console.log(res);
        this.formFields = res.formFields;
        this.taskId = res.taskId;
      },
      err => {
        console.log(err);
      }
    );
  }
  get f() {
    return this.manuscriptForm.controls;
  }

  ngOnInit() {
    this.manuscriptForm = this.formBuilder.group({
    });
  }
  uploadFileEvt(event) {
    this.files = event.target.files;
    this.file = this.files[0];
  }

  onSubmit() {
    if (this.formFields.length === 0) {
      console.log(0);
    } else {
      if (this.files !== undefined) {
        this.bookService.uploadFile(this.file).subscribe(
          (res) => {
            alert('Successfully uploaded files');
          },
          (err) => console.log(err));
      } else {
        console.log('error');
      }
      const d = new Array();
      d.push({fieldId : 'NewManuscriptId', fieldValue: this.file.name});
      const tmp = this.bookService.postForm(d, this.taskId).subscribe(
        next => {
          this.dialogRef.close();
        },
        error => {
          console.log(error);
        }
      );
    }
  }


}
