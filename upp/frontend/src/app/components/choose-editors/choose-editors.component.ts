import {Component, OnInit, ViewChild} from '@angular/core';
import {GenericFormComponent, GenericFormType} from '../generic-form/generic-form.component';
import {FormBuilder, FormControl, FormGroup, Validators} from '@angular/forms';
import {Router} from '@angular/router';
import {UserService} from '../../services/user.service';
import {PlagiarismService} from '../../services/plagiarism.service';

@Component({
  selector: 'app-choose-editors',
  templateUrl: './choose-editors.component.html',
  styleUrls: ['./choose-editors.component.css']
})
export class ChooseEditorsComponent implements OnInit {
  @ViewChild('genericForm', {static: true}) genericForm: GenericFormComponent;
	private formFields = [];
	private enumValues = [];
	taskId: string;
	fields: Array<GenericFormType> = [];
	chooseEditorsForm: FormGroup;

  constructor(private plagiarismService: PlagiarismService,
			private formBuilder: FormBuilder,private router: Router,
              private userService: UserService) {

  	const x = plagiarismService.getEditorsForm();
  	 x.subscribe(
  	      res => {
  	        console.log(res);
  	        this.formFields = res.formFields;
  	        this.taskId = res.taskId;
  	        this.formFields.forEach( (field) => {

  	          if ( field.id === 'chosenEditorsId') {
  	            this.enumValues = Object.keys(field.type.values);
  	            console.log(this.enumValues);
  	            this.fields.push({type: 'control', name: field.id, label: field.label, inputType: 'multiple-select', lookups: field});
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
	  this.chooseEditorsForm = this.formBuilder.group({
	    });
  }

  get f() {
	    return this.chooseEditorsForm.controls;
  }

  onSubmit() {
	    this.chooseEditorsForm.controls = this.genericForm.myForm.controls;
	    console.log(this.f.chosenEditorsId.value);
	    if (this.chooseEditorsForm.invalid) {
	      return;
	    }
	    console.log(this.taskId);
	    const d = new Array();
	    d.push({fieldId: 'chosenEditorsId', fieldValue: this.f.chosenEditorsId.value.toString()});

	    const tmp = this.plagiarismService.postForm(d, this.taskId).subscribe(
	      next => {
	        this.router.navigate(['/editorHome']);
	      },
	      error => {
	        console.log(error);
	      }
	    );
	  }
}
