import {Component, Inject, OnInit, ViewChild} from '@angular/core';
import {FormBuilder, FormControl, FormGroup, Validators} from '@angular/forms';
import {GenericFormComponent, GenericFormType} from '../generic-form/generic-form.component';
import {MatDialogRef} from '@angular/material';
import {MAT_DIALOG_DATA} from '@angular/material/dialog';
import {BookService} from '../../services/book.service';
import {PlagiarismService} from '../../services/plagiarism.service';

@Component({
  selector: 'app-editor-plagiarised-dialog-review',
  templateUrl: './editor-plagiarised-dialog-review.component.html',
  styleUrls: ['./editor-plagiarised-dialog-review.component.css']
})
export class EditorPlagiarisedDialogReviewComponent implements OnInit {

  formFields = [];
  taskId: string;
  bookReviewForm: FormGroup;
  @ViewChild('genericForm', {static: true}) genericForm: GenericFormComponent;
  fields: Array<GenericFormType> = [];
  listResponses: Array<any> = new Array<any>();
  comments = '';
  constructor(public dialogRef: MatDialogRef<EditorPlagiarisedDialogReviewComponent>,
              @Inject(MAT_DIALOG_DATA) public data: any,
              private plagiarsimService: PlagiarismService,
              private formBuilder: FormBuilder) {
    const x = plagiarsimService.getReviewForm();
    x.subscribe(
      res => {
        console.log(res);
        this.formFields = res.formFields;
        this.taskId = res.taskId;
        this.formFields.forEach( (field) => {
          this.fields.push({type: 'control', name: field.id, label: field.label, inputType: 'radio', lookups: [{ value: '0', label: 'Da'}]});
          this.genericForm.initFormFields();
        });
      },
      err => {
        console.log(err);
      }
    );

    // console.log(data);
    this.plagiarsimService.getAllComments(data.plagiarismComplaint.id.toString()).subscribe(
      data1 => {
        this.listResponses = data1;
        for (const c of data1) {
          this.comments += c.editor.username + ': ' + c.comment + '\n\n';
        }

        this.bookReviewForm = this.formBuilder.group({
          commentId: new FormControl(this.comments)
        });
      }, error => {
        console.log(error);
      });


  }
  ngOnInit() {
    this.bookReviewForm = this.formBuilder.group({
      commentId: new FormControl(this.comments)
    });
  }

  get f() {
    return this.bookReviewForm.controls;
  }

  onSubmit() {
    this.bookReviewForm.controls = this.genericForm.myForm.controls;
    const d = new Array();
    d.push({fieldId: 'approveId', fieldValue: this.f.approveId.value});
    const tmp = this.plagiarsimService.postForm(d, this.taskId).subscribe(
      next => {
        this.dialogRef.close();
      },
      error => {
        console.log(error);
      }
    );
  }

}
