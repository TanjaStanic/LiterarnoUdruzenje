import { Component, OnInit } from '@angular/core';
import {FormBuilder, FormControl, FormGroup, Validators} from '@angular/forms';
import {ReaderDialogComponent} from '../reader-dialog/reader-dialog.component';
import {MatDialog} from '@angular/material';
import {WriterDialogComponent} from '../writer-dialog/writer-dialog.component';
import {UserService} from '../../services/user.service';
import {GenreService} from '../../services/genre.service';
import {Router} from '@angular/router';

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
  private genreValues = [];
  constructor(private formBuilder: FormBuilder, public dialog: MatDialog,
              private userService: UserService,
              private genreService: GenreService,
              private router: Router) {
    // this.genreService.getAllGenres();
    const x = userService.getFields();

    x.subscribe(
      res => {
        console.log(res);
        this.formFields = res.formFields;
        this.formFields.forEach( (field) => {

          if ( field.id === 'roleId') {
            this.enumValues = Object.keys(field.type.values);
          } else if (field.id === 'firstGenresListId') {
            this.genreValues = Object.keys(field.type.values);
          }
        });
      },
      err => {
        console.log(err);
      }
    );
  }

  ngOnInit() {
    this.registerForm = this.formBuilder.group({
      emailId: new FormControl('', [Validators.required, Validators.email]),
      userNameId: new FormControl('', [Validators.required]),
      passwordId: new FormControl('', [Validators.required]),
      firstNameId: new FormControl('', [Validators.required]),
      lastNameId: new FormControl('', [Validators.required]),
      cityId: new FormControl('', [Validators.required]),
      countryId: new FormControl('', [Validators.required]),
      roleId: new FormControl('', [Validators.required]),
      firstGenresListId: new FormControl('', [Validators.required]),
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
    const d = new Array();
    d.push({fieldId : 'firstNameId', fieldValue: this.f.firstNameId.value});
    d.push({fieldId : 'lastNameId', fieldValue: this.f.lastNameId.value});
    d.push({fieldId : 'passwordId', fieldValue: this.f.passwordId.value});
    d.push({fieldId : 'emailId', fieldValue: this.f.emailId.value});
    d.push({fieldId : 'roleId', fieldValue: this.f.roleId.value});
    d.push({fieldId : 'cityId', fieldValue: this.f.cityId.value});
    d.push({fieldId : 'countryId', fieldValue: this.f.countryId.value});
    d.push({fieldId : 'userNameId', fieldValue: this.f.userNameId.value});
    d.push({fieldId : 'firstGenresListId', fieldValue: this.f.firstGenresListId.value.toString()});
    console.log(d);
    if (this.f.roleId.value === 'value_1') {
      const user = this.userService.registerUser(d);
      user.subscribe(
        res => {

          console.log('Successfully fist task');
          this.dialog.open(ReaderDialogComponent, {width: '50%', height: '50%', data: d});
        },
        err => {

          console.log('Error occured');
          window.alert('Validation backend failed !');
          window.location.href = 'http://localhost:4200/register';
        }
      );

    } else {
      const user = this.userService.registerUser(d);
      user.subscribe(
        res => {

          console.log('Successfully register writer');
          this.router.navigate(['/login']);
        },
        err => {

          console.log('Error occured');
          window.alert('Validation backend failed !');
          window.location.href = 'http://localhost:4200/register';
        });
      // this.dialog.open(WriterDialogComponent, {width: '50%', height: '50%'});
    }
  }
}
