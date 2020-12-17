import {Component, Inject, OnInit} from '@angular/core';
import {MAT_DIALOG_DATA, MatDialogRef} from '@angular/material/dialog';
import {FormBuilder, FormControl, FormGroup, Validators} from '@angular/forms';
import {UserService} from '../../services/user.service';
import {Genre} from '../../model/genre';
import {GenreService} from '../../services/genre.service';

@Component({
  selector: 'app-reader-dialog',
  templateUrl: './reader-dialog.component.html',
  styleUrls: ['./reader-dialog.component.css']
})
export class ReaderDialogComponent implements OnInit {

  readerForm: FormGroup;
  genreList: Array<Genre>;
  constructor( public dialogRef: MatDialogRef<ReaderDialogComponent>,
               @Inject(MAT_DIALOG_DATA) public data: any,
               private formBuilder: FormBuilder,
               private userService: UserService,
               private genreService: GenreService) {
    this.genreList = this.genreService.getGenres();
    console.log(this.genreList);
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
        },
        err => {
          console.log('Error occured');
        }
      );
    }
  }

}
