import {Component, Inject, OnInit} from '@angular/core';
import {MAT_DIALOG_DATA, MatDialogRef} from '@angular/material/dialog';
import {FormBuilder, FormControl, FormGroup, Validators} from '@angular/forms';

@Component({
  selector: 'app-reader-dialog',
  templateUrl: './reader-dialog.component.html',
  styleUrls: ['./reader-dialog.component.css']
})
export class ReaderDialogComponent implements OnInit {

  readerForm: FormGroup;
  genreList: string[] = ['Horor', 'Komedija', 'Drama', 'Akcija', 'Romantika', 'Istorija'];
  constructor( public dialogRef: MatDialogRef<ReaderDialogComponent>,
               @Inject(MAT_DIALOG_DATA) public data: any,
               private formBuilder: FormBuilder) {
    console.log(data);
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
    console.log(this.f.role.value);
    console.log(this.f.genres.value);

  }

}
