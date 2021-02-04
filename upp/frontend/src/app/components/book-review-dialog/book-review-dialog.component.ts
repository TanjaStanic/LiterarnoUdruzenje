import {Component, Inject, OnInit, ViewChild} from '@angular/core';
import {FormBuilder, FormGroup} from '@angular/forms';
import {GenericFormComponent, GenericFormType} from '../generic-form/generic-form.component';
import {MatDialogRef} from '@angular/material';
import {MAT_DIALOG_DATA} from '@angular/material/dialog';
import {BookService} from '../../services/book.service';

@Component({
  selector: 'app-book-review-dialog',
  templateUrl: './book-review-dialog.component.html',
  styleUrls: ['./book-review-dialog.component.css']
})
export class BookReviewDialogComponent implements OnInit {

  formFields = [];
  taskId: string;
  bookReviewForm: FormGroup;
  @ViewChild('genericForm', {static: true}) genericForm: GenericFormComponent;
  fields: Array<GenericFormType> = [];
  constructor(public dialogRef: MatDialogRef<BookReviewDialogComponent>,
              @Inject(MAT_DIALOG_DATA) public data: any,
              private bookService: BookService,
              private formBuilder: FormBuilder) {
    const x = bookService.acceptManuscriptAndsendToBetaReadersForm();
    x.subscribe(
      res => {
        console.log(res);
        this.formFields = res.formFields;
        this.taskId = res.taskId;
        this.formFields.forEach( (field) => {
          this.fields.push({type: 'control', name: field.id, label: field.label, inputType: 'radio', lookups: [{ value: '0', label: 'Da'}, { value: '1', label: 'Ne'}]});
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
    console.log(this.f.betaReadingId.value);
    console.log(this.f.acceptManuScriptId.value);
    const d = new Array();
    d.push({fieldId: 'betaReadingId', fieldValue: this.f.betaReadingId.value});
    d.push({fieldId: 'acceptManuScriptId', fieldValue: this.f.acceptManuScriptId.value});
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
