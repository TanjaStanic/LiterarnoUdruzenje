import {Component, Inject, OnInit, ViewChild} from '@angular/core';
import {FormBuilder, FormGroup} from '@angular/forms';
import {GenericFormComponent, GenericFormType} from '../generic-form/generic-form.component';
import {MatDialogRef} from '@angular/material';
import {MAT_DIALOG_DATA} from '@angular/material/dialog';
import {BookService} from '../../services/book.service';

@Component({
  selector: 'app-need-more-work-dialog',
  templateUrl: './need-more-work-dialog.component.html',
  styleUrls: ['./need-more-work-dialog.component.css']
})
export class NeedMoreWorkDialogComponent implements OnInit {

  formFields = [];
  taskId: string;
  bookReviewForm: FormGroup;
  @ViewChild('genericForm', {static: true}) genericForm: GenericFormComponent;
  fields: Array<GenericFormType> = [];
  constructor(public dialogRef: MatDialogRef<NeedMoreWorkDialogComponent>,
              @Inject(MAT_DIALOG_DATA) public data: any,
              private bookService: BookService,
              private formBuilder: FormBuilder) {
    const x = bookService.needMoreWorkForm();
    x.subscribe(
      res => {
        console.log(res);
        this.formFields = res.formFields;
        this.taskId = res.taskId;
        this.formFields.forEach( (field) => {
          if (field.id === 'moreWorkId') {
            this.fields.push({type: 'control', name: field.id, label: field.label, inputType: 'radio', lookups: [{ value: '0', label: 'Da'}, { value: '1', label: 'Ne'}]});
          } else if (field.id === 'commentId') {
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
    console.log(this.f.moreWorkId.value);
    console.log(this.f.commentId.value);
    const d = new Array();
    d.push({fieldId: 'moreWorkId', fieldValue: this.f.moreWorkId.value});
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
