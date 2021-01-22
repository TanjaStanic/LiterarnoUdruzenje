import { Component, OnInit } from '@angular/core';
import {UserService} from '../../services/user.service';
import {Form, FormBuilder, FormControl, FormGroup, Validators} from '@angular/forms';

@Component({
  selector: 'app-writer-payment',
  templateUrl: './writer-payment.component.html',
  styleUrls: ['./writer-payment.component.css']
})
export class WriterPaymentComponent implements OnInit {

  private formFields = [];
  private enumValues = [];
  paymentForm: FormGroup;
  taskId: string;
  constructor(private userService: UserService,
              private formBuilder: FormBuilder) {
    const x = this.userService.getPaymentFields(this.userService.getLoggedUser().username);

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
    this.paymentForm = this.formBuilder.group({
      card_holder_id: new FormControl('', [Validators.required]),
      card_number_id: new FormControl('', [Validators.required]),
      cvv_id: new FormControl('', [Validators.required]),
      expiration_date_id: new FormControl('', [Validators.required]),
    });
  }

  get f() {
    return this.paymentForm.controls;
  }
  onSubmit() {
    if (this.paymentForm.invalid) {
      return;
    }
    const d = new Array();
    d.push({fieldId : 'card_holder_id', fieldValue: this.f.card_holder_id.value});
    d.push({fieldId : 'card_number_id', fieldValue: this.f.card_number_id.value});
    d.push({fieldId : 'cvv_id', fieldValue: this.f.cvv_id.value});
    d.push({fieldId : 'expiration_date_id', fieldValue: this.f.expiration_date_id.value});
    console.log(d);
    const x = this.userService.postPayment(d).subscribe(
      res => {
        alert('Uspesno ste izvrsili placanje');
        this.userService.logOut();
        },
      error => {console.log(error); }
    );

  }

}
