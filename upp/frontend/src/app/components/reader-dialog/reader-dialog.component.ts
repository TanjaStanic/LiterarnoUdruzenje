import {Component, Inject, OnInit} from '@angular/core';
import {MAT_DIALOG_DATA, MatDialogRef} from '@angular/material/dialog';
import {FormBuilder, FormControl, FormGroup, Validators} from '@angular/forms';
import {UserService} from '../../services/user.service';
import {Genre} from '../../model/genre';
import {GenreService} from '../../services/genre.service';
import {Router} from '@angular/router';

@Component({
  selector: 'app-reader-dialog',
  templateUrl: './reader-dialog.component.html',
  styleUrls: ['./reader-dialog.component.css']
})
export class ReaderDialogComponent implements OnInit {

  readerForm: FormGroup;
  private formFields = [];
  private enumValues = []
  private genreValues = [];

  constructor( public dialogRef: MatDialogRef<ReaderDialogComponent>,
               @Inject(MAT_DIALOG_DATA) public data: any,
               private formBuilder: FormBuilder,
               private userService: UserService,
               private genreService: GenreService,
               private router: Router) {

    const x = genreService.getFieldsGenres();

    x.subscribe(
      res => {
        console.log(res);
        this.formFields = res.formFields;
        this.formFields.forEach( (field) => {

          if ( field.id === 'betaReaderId') {
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
    this.readerForm = this.formBuilder.group({
      betaReaderId: new FormControl('', [Validators.required]),
      firstGenresListId : new FormControl()
    });
  }

  get f() {
    return this.readerForm.controls;
  }

  onSubmit() {
    console.log(this.f.betaReaderId.value);
    console.log(this.f.firstGenresListId.value);
    if (this.readerForm.invalid) {
      return;
    }
    const yes = [];
    const no = [];
    no.push({fieldId : 'betaReaderId', fieldValue: 'value_no'});
    yes.push({fieldId : 'betaReaderId', fieldValue: 'value_yes'});
    yes.push({fieldId : 'genresListId', fieldValue: this.f.firstGenresListId.value.toString()});
    if (this.f.betaReaderId.value === 'value_yes') {
      const user = this.userService.betaReaderYes(yes);
      user.subscribe(
        res => {
          console.log('Successfully second task');
          this.router.navigate(['/login']);
          this.dialogRef.close();
        },
        err => {
          console.log('Error occured');
        }
      );

    } else {
      const user = this.userService.betaReaderNo(no);
      user.subscribe(
        res => {
          console.log('Successfully second task');
          this.router.navigate(['/login']);
          this.dialogRef.close();
        },
        err => {
          console.log('Error occured');
        }
      );
    }
  }

}
