import {Component, Inject, OnInit, ViewChild} from '@angular/core';
import {FormBuilder, FormGroup} from '@angular/forms';
import {GenericFormComponent, GenericFormType} from '../generic-form/generic-form.component';
import {MatDialogRef} from '@angular/material';
import {MAT_DIALOG_DATA} from '@angular/material/dialog';
import {BookService} from '../../services/book.service';

@Component({
  selector: 'app-lecturer-notes-typos-dialog',
  templateUrl: './lecturer-notes-typos-dialog.component.html',
  styleUrls: ['./lecturer-notes-typos-dialog.component.css']
})
export class LecturerNotesTyposDialogComponent implements OnInit {

  formFields = [];
  taskId: string;
  bookReviewForm: FormGroup;
  @ViewChild('genericForm', {static: true}) genericForm: GenericFormComponent;
  fields: Array<GenericFormType> = [];
  constructor(public dialogRef: MatDialogRef<LecturerNotesTyposDialogComponent>,
              @Inject(MAT_DIALOG_DATA) public data: any,
              private bookService: BookService,
              private formBuilder: FormBuilder) {
    const x = bookService.lecturerNotesTypos();
    x.subscribe(
      res => {
        console.log(res);
        this.formFields = res.formFields;
        this.taskId = res.taskId;
        this.formFields.forEach( (field) => {
          if (field.id === 'commentId') {
            this.fields.push({type: 'control', name: field.id, label: field.label, inputType: 'textarea'});
            this.genericForm.initFormFields();
          }
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
