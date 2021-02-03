import { Component, Inject, OnInit } from '@angular/core';
import { MatDialogRef, MAT_DIALOG_DATA } from '@angular/material';
import { MatPaginator, MatSort, MatTableDataSource } from '@angular/material';
import { MyErrorStateMatcher } from 'src/app/shared/validators/error-state-matcher';
import { FormGroup, FormBuilder, Validators, FormControl, FormGroupDirective, NgForm, AbstractControl } from '@angular/forms';
@Component({
  selector: 'app-new-pm-dialog',
  templateUrl: './new-pm-dialog.component.html',
  styleUrls: ['./new-pm-dialog.component.css']
})
export class NewPmDialogComponent implements OnInit {

	form: FormGroup;
	matcher = new MyErrorStateMatcher();
	dataSourceNoPaymentMethods: MatTableDataSource<any>;
	displayedColumnsPaymentMethods: string[] = ['name', 'subscriptionSupported', 'serviceId', 'actions'];

  constructor(
		  public dialogRef: MatDialogRef<NewPmDialogComponent>,
		    @Inject(MAT_DIALOG_DATA) public data: any, private formBuilder: FormBuilder) { 
	  		
 		this.form = this.formBuilder.group({
	      'name': ['', Validators.compose([Validators.required])],
	      'applicationName': ['', Validators.compose([Validators.required])],
	      'id': [],
	    });
  }
  ngOnInit() {
	  this.dataSourceNoPaymentMethods =  this.data.dataSourceNoPaymentMethods;
	////  console.log(this.dataSourceNoPaymentMethods);
	 // console.log(this.data);
  }

  get name(): AbstractControl {
	    return this.form.get('name')
	  }
	  get applicationName(): AbstractControl {
	    return this.form.get('applicationName')
	  }
	  get id(): AbstractControl {
	    return this.form.get('id')
	  }

  onNoClick(): void {
    this.dialogRef.close();
  }
  showDiv(paymentMethod) {
	var divname = "div" + paymentMethod.id;
	
	if (document.getElementById(divname).getAttribute("hidden")){
		  document.getElementById(divname).removeAttribute('hidden');
	  }
	  else {
		  document.getElementById(divname).setAttribute("hidden", "true");
	  }
	
  }
}
