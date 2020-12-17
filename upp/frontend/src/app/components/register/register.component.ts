import { Component, OnInit } from '@angular/core';
import {FormBuilder, FormControl, FormGroup, Validators} from '@angular/forms';
import {ReaderDialogComponent} from '../reader-dialog/reader-dialog.component';
import {MatDialog} from '@angular/material';
import {WriterDialogComponent} from '../writer-dialog/writer-dialog.component';
import {UserService} from '../../services/user.service';
import {GenreService} from '../../services/genre.service';

@Component({
  selector: 'app-register',
  templateUrl: './register.component.html',
  styleUrls: ['./register.component.css']
})
export class RegisterComponent implements OnInit {

  registerForm: FormGroup;
  private formFieldsDto = null;
  private formFields = [];
  private processInstance = '';
  private enumValues = [];
  constructor(private formBuilder: FormBuilder, public dialog: MatDialog,
              private userService: UserService,
              private genreService: GenreService) {
    this.genreService.getAllGenres();
  }

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
    	window.alert('Validation front failed !');
      return;
    }
    console.log('validna registracija');
    const d = new Array();
    d.push({fieldId : 'firstNameId', fieldValue: this.f.name.value});
    d.push({fieldId : 'lastNameId', fieldValue: this.f.surname.value});
    d.push({fieldId : 'passwordId', fieldValue: this.f.password.value});
    d.push({fieldId : 'emailId', fieldValue: this.f.email.value});
    if (this.f.role.value === 'Citalac') {
      d.push({fieldId : 'roleId', fieldValue: 'value_1'});
    } else {
      d.push({fieldId : 'roleId', fieldValue: 'value_2'});
    }
    d.push({fieldId : 'cityId', fieldValue: this.f.city.value});
    d.push({fieldId : 'countryId', fieldValue: this.f.country.value});
    d.push({fieldId : 'userNameId', fieldValue: this.f.username.value});

    if (this.f.role.value === 'Citalac') {
      const user = this.userService.registerUser(d);
      user.subscribe(
        res => {

          console.log('Successfully fist task');
        },
        err => {

          console.log('Error occured');
          window.alert('Validation backend failed !');
          window.location.href = 'http://localhost:4200/register';
        }
      );
      this.dialog.open(ReaderDialogComponent, {width: '50%', height: '50%', data: d});
    } else {
      this.dialog.open(WriterDialogComponent, {width: '50%', height: '50%'});
    }
  }
}
