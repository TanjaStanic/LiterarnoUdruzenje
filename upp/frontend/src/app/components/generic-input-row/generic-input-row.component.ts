import {Component, Input, OnInit} from '@angular/core';


@Component({
  selector: 'app-generic-input-row',
  templateUrl: './generic-input-row.component.html',
  styleUrls: ['./generic-input-row.component.css']
})
export class GenericInputRowComponent implements OnInit {

  @Input('field') field;
  @Input('formGroup') formGroup;
  files: [];
  enumValues = [];
  constructor() {
    this.files = [];
  }

  ngOnInit() {
  }

  getKeys(lookups) {
    this.enumValues = Object.keys(lookups.type.values);
  }

  uploadFileEvt(event) {
      this.files = event.target.files;
  }

  public getFiles() {
    return this.files;
  }

}
