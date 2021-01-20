import {Component, ElementRef, OnInit} from '@angular/core';
import {FormBuilder, FormControl, FormGroup} from '@angular/forms';
import {UserService} from '../../services/user.service';

@Component({
  selector: 'app-writer-dialog',
  templateUrl: './writer-dialog.component.html',
  styleUrls: ['./writer-dialog.component.css']
})
export class WriterDialogComponent implements OnInit {

  uploadForm: FormGroup;
  counter = 0;
  files: [];
  private formFields = [];
  constructor(private formBuilder: FormBuilder,
              private userService: UserService) {
    const x = userService.getFilesFileds();

    x.subscribe(
      res => {
        console.log(res);
        this.formFields = res.formFields;
      },
      err => {
        console.log(err);
      }
    );
  }

  ngOnInit() {
    this.uploadForm = this.formBuilder.group({
    });
  }
  get f() {
    return this.uploadForm.controls;
  }
  uploadFileEvt(event) {
    if (event.target.files.length > 1) {
        this.files = event.target.files;
      }
    }


  onSubmit() {
    if (this.files !== undefined) {
      this.userService.uploadFiles(this.files).subscribe(
        (res) => {
          alert('Successfully uploaded files');
          // this.userService.logOut();
        },
        (err) => console.log(err));
    } else {
      console.log('error');
    }

    let str = '';
    for (const f of this.files) {
      // @ts-ignore
      str = str + f.name + ',';
    }
    const ret = str.substr(0, str.length - 1);
    const d = new Array();
    d.push({fieldId : 'filesNamesId', fieldValue: ret});
    this.userService.files(d).subscribe(
      (res) => {console.log('uspesno'); },
       error => {console.log(error); }
    );

  }

}
