import { Injectable } from '@angular/core';
import { MatSnackBar } from '@angular/material';

@Injectable({
  providedIn: 'root'
})
export class SnackBarService {

  constructor(private _snackBar: MatSnackBar) { }

  showMessage(message: string) {
    this._snackBar.open(message, "Ok", {
      duration: 7000,
    });
  }
}
