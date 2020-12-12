import { Component, OnInit } from '@angular/core';
import {FormBuilder, FormControl, FormGroup, Validators} from '@angular/forms';
import {ReaderDialogComponent} from '../reader-dialog/reader-dialog.component';
import {MatDialog} from '@angular/material';
import {WriterDialogComponent} from '../writer-dialog/writer-dialog.component';

@Component({
  selector: 'app-register',
  templateUrl: './register.component.html',
  styleUrls: ['./register.component.css']
})
export class RegisterComponent implements OnInit {

  registerForm: FormGroup;
  constructor(private formBuilder: FormBuilder, public dialog: MatDialog) { }

  ngOnInit() {
    this.registerForm = this.formBuilder.group({
      email: new FormControl('', [Validators.required, Validators.email]),
      username: new FormControl('', [Validators.required]),
      password: new FormControl('', [Validators.required, Validators.minLength(8),
        Validators.pattern('^(?=.*?[A-Z])(?=.*?[a-z])(?=.*?[0-9]).{8,}$')]),
      name: new FormControl('', [Validators.required]),
      surname: new FormControl('', [Validators.required]),
      city: new FormControl('', [Validators.required]),
      country: new FormControl('', [Validators.required]),
      role: new FormControl('', [Validators.required])
    });
  }

  get f() {
    return this.registerForm.controls;
  }

  onSubmit() {
    if (this.registerForm.invalid) {
      return;
    }
    console.log('validna registracija');
    if (this.f.role.value === 'citalac') {
      this.dialog.open(ReaderDialogComponent, {width: '50%', height: '50%', data: 'test'});
     // this.dialog.open(ReaderDialogComponent, {
      //   width: '60%', disableClose: true, data: this.data
     //  });
    } else {
      this.dialog.open(WriterDialogComponent, {width: '50%', height: '50%'});
    }
  }
}
