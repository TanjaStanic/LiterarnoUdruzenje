import {Component, ElementRef, OnInit} from '@angular/core';

@Component({
  selector: 'app-writer-dialog',
  templateUrl: './writer-dialog.component.html',
  styleUrls: ['./writer-dialog.component.css']
})
export class WriterDialogComponent implements OnInit {


  constructor() { }

  ngOnInit() {
  }

  uploadFileEvt(imgFile: any) {
    /*
    if (imgFile.target.files && imgFile.target.files[0]) {
      this.fileAttr = '';
      Array.from(imgFile.target.files).forEach((file: File) => {
        this.fileAttr += file.name + ' - ';
      });

      // HTML5 FileReader API
      let reader = new FileReader();
      reader.onload = (e: any) => {
        let image = new Image();
        image.src = e.target.result;
        image.onload = rs => {
          let imgBase64Path = e.target.result;
        };
      };
      reader.readAsDataURL(imgFile.target.files[0]);

      // Reset if duplicate image uploaded again
      this.fileInput.nativeElement.value = "";
    } else {
      this.fileAttr = 'Choose File';
    }*/
  }

}
