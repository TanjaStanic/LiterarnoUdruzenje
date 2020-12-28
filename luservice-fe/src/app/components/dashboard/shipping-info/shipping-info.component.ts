import { Component, OnInit } from '@angular/core';
import { AbstractControl, FormBuilder, FormGroup, Validators } from '@angular/forms';
import { MatDialogRef } from '@angular/material';
import { ShippingInfo } from 'src/app/shared/models/shipping-info';
import { MyErrorStateMatcher } from 'src/app/shared/validators/error-state-matcher';

@Component({
  selector: 'app-shipping-info',
  templateUrl: './shipping-info.component.html',
  styleUrls: ['./shipping-info.component.css']
})
export class ShippingInfoComponent implements OnInit {

  form: FormGroup;
  matcher = new MyErrorStateMatcher();
  constructor(public dialogRef: MatDialogRef<ShippingInfoComponent>, private formBuilder: FormBuilder) {
    this.form = this.formBuilder.group({
      'recepient': ['', Validators.compose([Validators.required])],
      'city': ['', Validators.compose([Validators.required])],
      'country': ['', Validators.compose([Validators.required])],
      'address': ['', Validators.compose([Validators.required])],
      'postalCode': ['', Validators.compose([Validators.required])]
    });

    dialogRef.beforeClosed().subscribe(() => {
      const shippingInfo: ShippingInfo = this.form.value;
      dialogRef.close(shippingInfo);
    });
  }

  ngOnInit() {

  }
  get recepient(): AbstractControl {
    return this.form.get('recepient')
  }
  get city(): AbstractControl {
    return this.form.get('city')
  }
  get country(): AbstractControl {
    return this.form.get('country')
  }
  get address(): AbstractControl {
    return this.form.get('address')
  }
  get postalCode(): AbstractControl {
    return this.form.get('postalCode')
  }

  cancel() {
    this.dialogRef.close(false);
  }


}
