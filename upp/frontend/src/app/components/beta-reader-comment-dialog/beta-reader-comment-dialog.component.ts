import {Component, Inject, OnInit, ViewChild} from '@angular/core';
import {FormBuilder, FormGroup} from '@angular/forms';
import {GenericFormComponent, GenericFormType} from '../generic-form/generic-form.component';
import {MatDialogRef} from '@angular/material';
import {MAT_DIALOG_DATA} from '@angular/material/dialog';
import {BookService} from '../../services/book.service';
import {UserService} from '../../services/user.service';

@Component({
  selector: 'app-beta-reader-comment-dialog',
  templateUrl: './beta-reader-comment-dialog.component.html',
  styleUrls: ['./beta-reader-comment-dialog.component.css']
})
export class BetaReaderCommentDialogComponent implements OnInit {

  formFields = [];
  taskId: string;
  bookReviewForm: FormGroup;
  @ViewChild('genericForm', {static: true}) genericForm: GenericFormComponent;
  fields: Array<GenericFormType> = [];
  constructor(public dialogRef: MatDialogRef<BetaReaderCommentDialogComponent>,
              @Inject(MAT_DIALOG_DATA) public data: any,
              private bookService: BookService,
              private formBuilder: FormBuilder,
              private userService: UserService) {

    const x = bookService.BetaReaderCommentsForm();
    x.subscribe(
      res => {
        console.log(res);
        this.formFields = res.formFields;
        this.taskId = res.taskId;
        this.formFields.forEach( (field) => {
          if (field.id === 'commentId') {
            this.fields.push({type: 'control', name: field.id, label: field.label, inputType: 'textarea'});
          }
          this.genericForm.initFormFields();
        });
      },
      err => {
        console.log(err);
      }
    );

  }

  ngOnInit() {
    this.bookReviewForm = this.formBuilder.group({
    });
  }

  get f() {
    return this.bookReviewForm.controls;
  }

  onSubmit() {
    this.bookReviewForm.controls = this.genericForm.myForm.controls;
    console.log(this.f.commentId.value);
    const d = new Array();
    d.push({fieldId: 'commentId', fieldValue: this.f.commentId.value});
    d.push({fieldId: 'usernameId', fieldValue: this.userService.getLoggedUser().username});
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

