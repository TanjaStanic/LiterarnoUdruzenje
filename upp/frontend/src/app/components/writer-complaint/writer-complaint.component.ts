import {Component, OnInit, ViewChild} from '@angular/core';
import {GenericFormComponent, GenericFormType} from '../generic-form/generic-form.component';
import {FormBuilder, FormControl, FormGroup, Validators} from '@angular/forms';
import {Router} from '@angular/router';
import {UserService} from '../../services/user.service';
import {PlagiarismService} from '../../services/plagiarism.service';

@Component({
  selector: 'app-writer-complaint',
  templateUrl: './writer-complaint.component.html',
  styleUrls: ['./writer-complaint.component.css']
})
export class WriterComplaintComponent implements OnInit {
   
	@ViewChild('genericForm', {static: true}) genericForm: GenericFormComponent;
	private formFields = [];
	private enumValues = [];
	taskId: string;
	fields: Array<GenericFormType> = [];
    complainForm : FormGroup;
	
	
	constructor(private plagiarismService: PlagiarismService,
			private formBuilder: FormBuilder,
            private router: Router,
            private userService: UserService) {

	const x = plagiarismService.getComplainForm();
	 x.subscribe(
		res => {
		        console.log(res);
		        this.formFields = res.formFields;
		        this.taskId = res.taskId;
		        this.formFields.forEach( (field) => {

		         if (field.id === 'bookTitleId') {
		            this.fields.push({type: 'control', name: field.id, label: field.label, inputType: 'text', placeHolder: ''});
		          } else if (field.id === 'bookAuthorId') {
		            this.fields.push({type: 'control', name: field.id, label: field.label, inputType: 'text', placeHolder: ''});
		          } else if (field.id === 'writerBookTitleId') {
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
	
	
	ngOnInit() {
	  this.complainForm = this.formBuilder.group({
	    });
    }
  
	get f() {
	    return this.complainForm.controls;
	  }
	
	 onSubmit() {
		    this.complainForm.controls = this.genericForm.myForm.controls;
		    console.log(this.complainForm);
		    if (this.complainForm.invalid) {
		      return;
		    }
		    console.log(this.taskId);
		    const d = new Array();
		    d.push({fieldId: 'bookTitleId', fieldValue: this.f.bookTitleId.value});
		    d.push({fieldId: 'bookAuthorId', fieldValue: this.f.bookAuthorId.value});
		    d.push({fieldId: 'writerBookTitleId', fieldValue: this.f.writerBookTitleId.value});
		    d.push({fieldId: 'writerUsernameId', fieldValue: this.userService.getLoggedUser().username});
		    const tmp = this.plagiarismService.postForm(d, this.taskId).subscribe(
		      next => {
		        this.router.navigate(['/writerHome']);
		      },
		      error => {
		        console.log(error);
		      }
		    );
		  }

}
