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
  flag = false;
  private formFields = [];
  taskId: string;
  constructor(private formBuilder: FormBuilder,
              private userService: UserService) {
    this.getFlag();
  }

  async getFlag() {
    this.flag = await this.userService.getFlag(this.userService.getLoggedUser().username);
    console.log(this.flag);
    if (this.flag === false) {
      const x = this.userService.getFilesFileds();

      x.subscribe(
        res => {
          console.log(res);
          this.taskId = res.taskId;
          this.formFields = res.formFields;
        },
        err => {
          console.log(err);
        }
      );
    } else {
      const x = this.userService.getMoreDocumentsFields();

      x.subscribe(
        res => {
          console.log(res);
          this.taskId = res.taskId;
          this.formFields = res.formFields;
        },
        err => {
          console.log(err);
        }
      );
    }
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
    d.push({fieldId : 'writerUsernameId', fieldValue: this.userService.getLoggedUser().username});
    console.log('OVO JE ID KOJI MI TREBA ' + this.taskId);
    this.userService.files(d, this.taskId).subscribe(
       (res) => { this.userService.logOut(); },
       error => {console.log(error); }
    );

  }

}
