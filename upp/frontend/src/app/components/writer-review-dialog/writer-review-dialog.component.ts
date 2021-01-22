import { Component, OnInit } from '@angular/core';
import {UserService} from '../../services/user.service';
import {MatDialogRef} from '@angular/material';
import {FormBuilder, FormControl, FormGroup, Validators} from '@angular/forms';
import {RegistrationApplicationResponseService} from '../../services/registration-application-response.service';

@Component({
  selector: 'app-writer-review-dialog',
  templateUrl: './writer-review-dialog.component.html',
  styleUrls: ['./writer-review-dialog.component.css']
})
export class WriterReviewDialogComponent implements OnInit {

  private formFields = [];
  private enumValues = [];
  taskId: string;
  reviewForm: FormGroup;
  constructor(private userService: UserService,
              public dialogRef: MatDialogRef<WriterReviewDialogComponent>,
              private formBuilder: FormBuilder,
              private rasService: RegistrationApplicationResponseService) {
    const x = this.userService.getReviewFields();

    x.subscribe(
      res => {
        console.log(res);
        this.taskId = res.taskId;
        this.formFields = res.formFields;
        this.formFields.forEach( (field) => {
          if ( field.id === 'review_id') {
            this.enumValues = Object.keys(field.type.values);
          }
        });
      },
      err => {
        console.log(err);
      }
    );
  }
  ngOnInit() {
    this.reviewForm = this.formBuilder.group({
      review_id: new FormControl('', [Validators.required]),
      comment_id: new FormControl('', [Validators.required]),
    });
  }

  get f() {
    return this.reviewForm.controls;
  }
  onSubmit() {
    if (this.reviewForm.invalid) {
      return;
    }
    console.log(this.f.review_id.value);
    console.log(this.f.comment_id.value);
    const d = new Array();
    d.push({fieldId : 'review_id', fieldValue: this.f.review_id.value});
    d.push({fieldId : 'comment_id', fieldValue: this.f.comment_id.value});
    const c = this.rasService.submitReview(d, this.userService.getLoggedUser().username, this.taskId).subscribe(
      res => {this.dialogRef.close(); },
      error => {console.log(error); });
  }

}
