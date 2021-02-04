import { Component, Inject, OnInit } from '@angular/core';
import { MatDialogRef, MAT_DIALOG_DATA } from '@angular/material';
import { FormGroup, FormBuilder, Validators, FormControl, FormGroupDirective, NgForm, AbstractControl } from '@angular/forms';
import { MyErrorStateMatcher } from 'src/app/shared/validators/error-state-matcher';
import { PaymentMethod } from 'src/app/shared/models/payment-method';
@Component({
  selector: 'app-payment-method-dialog',
  templateUrl: './payment-method-dialog.component.html',
  styleUrls: ['./payment-method-dialog.component.css']
})
export class PaymentMethodDialogComponent implements OnInit {

  form: FormGroup;
  matcher = new MyErrorStateMatcher();
  paymentMethod: PaymentMethod;

  constructor(
    public dialogRef: MatDialogRef<PaymentMethodDialogComponent>,
    @Inject(MAT_DIALOG_DATA) public data: any, private formBuilder: FormBuilder) {
    this.form = this.formBuilder.group({
      'name': ['', Validators.compose([Validators.required])],
      'applicationName': ['', Validators.compose([Validators.required])],
      'id': [],
      'subscriptionSupported': []
    });
  }

  ngOnInit() {
    this.form.setValue({ name: this.data.name, applicationName: this.data.applicationName, id: this.data.id, subscriptionSupported: this.data.subscriptionSupported });
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

  get subscriptionSupported(): AbstractControl {
    return this.form.get('subscriptionSupported')
  }

  onNoClick(): void {
    this.dialogRef.close();
  }

  submit() {
    this.paymentMethod = this.form.value;
    this.dialogRef.close(this.paymentMethod);
  }
  submitBank() {

	 this.dialogRef.close(this.paymentMethod);
  }



}
