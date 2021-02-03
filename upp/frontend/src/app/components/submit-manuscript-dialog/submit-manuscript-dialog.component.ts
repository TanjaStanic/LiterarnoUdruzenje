import {Component, Inject, OnInit, ViewChild} from '@angular/core';
import {MatDialogRef} from '@angular/material';
import {MAT_DIALOG_DATA} from '@angular/material/dialog';
import {BookService} from '../../services/book.service';
import {FormBuilder, FormGroup} from '@angular/forms';
import {GenericFormComponent, GenericFormType} from '../generic-form/generic-form.component';
import {UserService} from '../../services/user.service';

@Component({
  selector: 'app-submit-manuscript-dialog',
  templateUrl: './submit-manuscript-dialog.component.html',
  styleUrls: ['./submit-manuscript-dialog.component.css']
})
export class SubmitManuscriptDialogComponent implements OnInit {

  private formFields = [];
  taskId: string;
  manuscriptForm: FormGroup;
  @ViewChild('genericForm', {static: true}) genericForm: GenericFormComponent;
  fields: Array<GenericFormType> = [];
  files;
  file;

  constructor(public dialogRef: MatDialogRef<SubmitManuscriptDialogComponent>,
              @Inject(MAT_DIALOG_DATA) public data: any,
              private bookService: BookService,
              private formBuilder: FormBuilder,
              private userService: UserService) {
    const x = bookService.getSubmitManuscriptForm();
    x.subscribe(
      res => {
        console.log(res);
        this.formFields = res.formFields;
        this.taskId = res.taskId;
        this.formFields.forEach( (field) => {
          if (field.id === 'ManuscriptLinkId') {
            // this.fields.push({type: 'control', name: field.id, label: field.label, inputType: 'file', placeHolder: ''});
          } else {
            this.fields.push({type: 'control', name: field.id, label: field.label, inputType: 'text', placeHolder: ''});
          }
          this.genericForm.initFormFields();
        });
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
      console.log(this.file);
  }

  onSubmit() {
    this.manuscriptForm.controls = this.genericForm.myForm.controls;
    console.log(this.files);
    console.log(this.f.isbnId.value);
    console.log(this.f.keyTermsId.value);
    console.log(this.f.publisherId.value);
    console.log(this.f.pagesId.value);

    if (this.files !== undefined) {
      console.log(this.files);
      this.bookService.uploadFile(this.file).subscribe(
        (res) => {
          alert('Successfully uploaded files');
        },
        (err) => console.log(err));
    } else {
      console.log('error');
    }
    const d = new Array();
    d.push({fieldId : 'ManuscriptLinkId', fieldValue: this.file.name});
    d.push({fieldId : 'isbnId', fieldValue: this.f.isbnId.value});
    d.push({fieldId : 'keyTermsId', fieldValue: this.f.keyTermsId.value});
    d.push({fieldId : 'publisherId', fieldValue: this.f.publisherId.value});
    d.push({fieldId : 'pagesId', fieldValue: this.f.pagesId.value});
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
