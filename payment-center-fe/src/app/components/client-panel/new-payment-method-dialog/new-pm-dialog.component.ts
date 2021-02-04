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

  dataSourceNoPaymentMethods: MatTableDataSource<any>;
  displayedColumnsPaymentMethods: string[] = ['name', 'subscriptionSupported', 'actions'];

  constructor(
    public dialogRef: MatDialogRef<NewPmDialogComponent>,
    @Inject(MAT_DIALOG_DATA) public data: any, private formBuilder: FormBuilder) {

  }
  ngOnInit() {
    this.dataSourceNoPaymentMethods = this.data.dataSourceNoPaymentMethods;

  }

  onNoClick(): void {
    this.dialogRef.close();
  }
}
