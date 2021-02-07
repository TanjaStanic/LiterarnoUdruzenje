import {Component, OnInit, ViewChild} from '@angular/core';
import {GenericFormComponent, GenericFormType} from '../generic-form/generic-form.component';
import {BookService} from '../../services/book.service';
import {FormBuilder, FormControl, FormGroup, Validators} from '@angular/forms';
import {Router} from '@angular/router';
import {UserService} from '../../services/user.service';

@Component({
  selector: 'app-publish-book',
  templateUrl: './publish-book.component.html',
  styleUrls: ['./publish-book.component.css']
})
export class PublishBookComponent implements OnInit {

  @ViewChild('genericForm', {static: true}) genericForm: GenericFormComponent;
  private formFields = [];
  private enumValues = [];
  taskId: string;
  fields: Array<GenericFormType> = [];
  bookForm: FormGroup;
  constructor(private bookService: BookService,
              private formBuilder: FormBuilder,
              private router: Router,
              private userService: UserService) {
    const x = bookService.getBookDetailForm();

    x.subscribe(
      res => {
        console.log(res);
        this.formFields = res.formFields;
        this.taskId = res.taskId;
        this.formFields.forEach( (field) => {

          if ( field.id === 'genresListId') {
            this.enumValues = Object.keys(field.type.values);
            console.log(this.enumValues);
            this.fields.push({type: 'control', name: field.id, label: field.label, inputType: 'select', lookups: field});
          } else if (field.id === 'workingTitleId') {
            this.fields.push({type: 'control', name: field.id, label: field.label, inputType: 'text', placeHolder: ''});
          } else if (field.id === 'synopsisId') {
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
    this.bookForm = this.formBuilder.group({
    });
  }

  get f() {
    return this.bookForm.controls;
  }

  onSubmit() {
    this.bookForm.controls = this.genericForm.myForm.controls;
    console.log(this.bookForm);
    if (this.bookForm.invalid) {
      return;
    }
    console.log(this.taskId);
    const d = new Array();
    d.push({fieldId: 'workingTitleId', fieldValue: this.f.workingTitleId.value});
    d.push({fieldId: 'genresListId', fieldValue: this.f.genresListId.value.toString()});
    d.push({fieldId: 'synopsisId', fieldValue: this.f.synopsisId.value});
    d.push({fieldId: 'writerUsernameId', fieldValue: this.userService.getLoggedUser().username});
    const tmp = this.bookService.postForm(d, this.taskId).subscribe(
      next => {
        this.router.navigate(['/writerHome']);
      },
      error => {
        console.log(error);
      }
    );
  }
}
