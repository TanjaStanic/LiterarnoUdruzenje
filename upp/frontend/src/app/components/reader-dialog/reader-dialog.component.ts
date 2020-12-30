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
  genreList: Array<Genre>;
  private formFields = [];
  private enumValues = []

  constructor( public dialogRef: MatDialogRef<ReaderDialogComponent>,
               @Inject(MAT_DIALOG_DATA) public data: any,
               private formBuilder: FormBuilder,
               private userService: UserService,
               private genreService: GenreService,
               private router: Router) {
  }

  ngOnInit() {
    this.readerForm = this.formBuilder.group({
      role: new FormControl('', [Validators.required]),
      genres : new FormControl()
    });
  }

  get f() {
    return this.readerForm.controls;
  }

  onSubmit() {
    if (this.readerForm.invalid) {
      return;
    }
    const yes = [];
    const no = [];
    no.push({fieldId : 'betaReaderId', fieldValue: 'value_no'});
    yes.push({fieldId : 'betaReaderId', fieldValue: 'value_yes'});
    yes.push({fieldId : 'genresListId', fieldValue: this.f.genres.value.toString()});
    if (this.f.role.value === 'Da') {
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
