import {Component, Inject, OnInit, ViewChild} from '@angular/core';
import {MatDialogRef} from '@angular/material';
import {MAT_DIALOG_DATA} from '@angular/material/dialog';
import {BookService} from '../../services/book.service';
import {FormBuilder, FormGroup} from '@angular/forms';
import {GenericFormComponent, GenericFormType} from '../generic-form/generic-form.component';

@Component({
  selector: 'app-initial-book-review-dialog',
  templateUrl: './initial-book-review-dialog.component.html',
  styleUrls: ['./initial-book-review-dialog.component.css']
})
export class InitialBookReviewDialogComponent implements OnInit {

  private formFields = [];
  taskId: string;
  bookReviewForm: FormGroup;
  @ViewChild('genericForm', {static: true}) genericForm: GenericFormComponent;
  fields: Array<GenericFormType> = [];

  constructor(public dialogRef: MatDialogRef<InitialBookReviewDialogComponent>,
              @Inject(MAT_DIALOG_DATA) public data: any,
              private bookService: BookService,
              private formBuilder: FormBuilder) {

    const x = bookService.getInitialBookReviewForm();
    x.subscribe(
      res => {
        console.log(res);
        this.formFields = res.formFields;
        this.taskId = res.taskId;
        this.formFields.forEach( (field) => {

          if ( field.id === 'ReadManuscriptId') {
            this.fields.push({type: 'control', name: field.id, label: field.label, inputType: 'radio', lookups: [{ value: '0', label: 'Da'}, { value: '1', label: 'Ne'}]});
          }  else if (field.id === 'explanation') {
            this.fields.push({type: 'control', name: field.id, label: field.label, inputType: 'textarea', placeHolder: ''});
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
    console.log(this.f.ReadManuscriptId.value);
    console.log(this.f.explanation.value);
    const d = new Array();
    d.push({fieldId: 'ReadManuscriptId', fieldValue: this.f.ReadManuscriptId.value});
    d.push({fieldId: 'explanation', fieldValue: this.f.explanation.value});
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
