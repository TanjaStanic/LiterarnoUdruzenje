import { Component, Inject, OnInit } from '@angular/core';
import { MatDialogRef, MAT_DIALOG_DATA } from '@angular/material';
import { MatPaginator, MatSort, MatTableDataSource } from '@angular/material';
import { FormGroup, FormBuilder, Validators, FormControl, FormGroupDirective, NgForm, AbstractControl } from '@angular/forms';
@Component({
  selector: 'app-new-pm-dialog',
  templateUrl: './new-pm-dialog.component.html',
  styleUrls: ['./new-pm-dialog.component.css']
})
export class NewPmDialogComponent implements OnInit {

	dataSourceNoPaymentMethods: MatTableDataSource<any>;
	displayedColumnsPaymentMethods: string[] = ['name', 'subscriptionSupported', 'serviceId', 'actions'];

  constructor(
		  public dialogRef: MatDialogRef<NewPmDialogComponent>,
		    @Inject(MAT_DIALOG_DATA) public data: any) { }

  ngOnInit() {
	  this.dataSourceNoPaymentMethods =  this.data.dataSourceNoPaymentMethods;
	  console.log(this.dataSourceNoPaymentMethods);
	  console.log(this.data);
  }


  onNoClick(): void {
    this.dialogRef.close();
  }
}
