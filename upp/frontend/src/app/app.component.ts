import { Component } from '@angular/core';
import {Observable} from 'rxjs';
import {BreakpointObserver, Breakpoints} from '@angular/cdk/layout';
import {map, shareReplay} from 'rxjs/operators';
import {UserService} from './services/user.service';

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.css']
})
export class AppComponent {
  isHandset$: Observable<boolean> = this.breakpointObserver.observe(Breakpoints.Handset)
    .pipe(
      map(result => result.matches),
      shareReplay()
    );

  constructor(private breakpointObserver: BreakpointObserver,
              private userService: UserService) {

  }

  // Kada budemo imali roles na frontu mora postojati i none user odnosno user koji nije ulogovan
  // I mora se promeniti ova metoda
  public isNone() {
     return this.userService.isNone();
  }
  public isReader() {
    return this.userService.isReader();
  }
  public isBReader() {
    return this.userService.isBReader();
  }
  public isWriter() {
    return this.userService.isWriter();
  }
  public isWriterFiles() {
    return this.userService.isWriterFiles();
  }
  public isLecturer() {
    return this.userService.isLecturer();
  }
  public isEditor() {
    return this.userService.isEditor();
  }
  public isAdmin() {
    return this.userService.isAdmin();
  }

  public onLogout() {
    this.userService.logOut();
  }
}
