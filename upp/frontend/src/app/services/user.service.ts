import { Injectable } from '@angular/core';
import {HttpClient, HttpParams} from '@angular/common/http';
import {Observable} from 'rxjs';
import {User} from '../model/user';
import {Role} from '../model/role';
import {Router} from '@angular/router';

export const TOKEN = 'LoggedInUser';

@Injectable({
  providedIn: 'root'
})
export class UserService {
  user: User = new User('', '', Role.NONE);
  constructor(private httpClient: HttpClient,
              private router: Router) {
    localStorage.setItem(TOKEN, JSON.stringify(this.user));
  }


  getFields() {
    return this.httpClient.get('http://localhost:8080/registration/get') as Observable<any>;
  }

  registerUser(user) {
    return this.httpClient.post('http://localhost:8080/registration/post/', user) as Observable<any>;
  }

  betaReaderYes(yes) {
    return this.httpClient.post('http://localhost:8080/registration/betaYes/', yes) as Observable<any>;
  }

  betaReaderNo(no) {
    return this.httpClient.post('http://localhost:8080/registration/betaNo/', no) as Observable<any>;
  }

  login(user) {
    return this.httpClient.post('http://localhost:8080/auth/login', user, {responseType: 'text'});
  }

  uploadFiles(files) {
    console.log(files);
    const formData = new FormData();
    for (const f of files) {
      formData.append('files', f);
    }
    return this.httpClient.post('http://localhost:8080/files/multi-upload', formData);
  }

  getFilesFileds() {
    return this.httpClient.get('http://localhost:8080/registration/getFilesField') as Observable<any>;
  }

  files(f) {
    return this.httpClient.post('http://localhost:8080/files/filesFields/', f) as Observable<any>;
  }

  public async getUser(username: string): Promise<User> {
    let params = new HttpParams();
    params = params.append('username', username);
    // tslint:disable-next-line:max-line-length
    const response: any = await this.httpClient.get('http://localhost:8080/auth/getUserByName', {params}).toPromise();
    return response;
  }

  public isLoggedIn() {
    if (localStorage.getItem(TOKEN) !== null) {
      return localStorage.getItem(TOKEN);
    } else {
      return null;
    }

  }
  public logOut() {
    this.router.navigate(['']);
    this.user =  new User('', '', Role.NONE);
    localStorage.removeItem(TOKEN);
    localStorage.setItem(TOKEN, JSON.stringify(this.user));
  }

  public isNone() {
    if (this.isLoggedIn()) {
      return this.user.role === Role.NONE;
    }
  }
  public isReader() {
    if (this.isLoggedIn()) {
      return this.user.role === Role.READER;
    }
  }

  public isBReader() {
    if (this.isLoggedIn()) {
      return this.user.role === Role.BETA_READER;
    }
  }

  public isWriter() {
    if (this.isLoggedIn()) {
      return this.user.role === Role.WRITER;
    }
  }

  public isLecturer() {
    if (this.isLoggedIn()) {
      return this.user.role === Role.LECTURER;
    }
  }
  public isEditor() {
    if (this.isLoggedIn()) {
      return this.user.role === Role.EDITOR;
    }
  }

  public isAdmin() {
    if (this.isLoggedIn()) {
      return this.user.role === Role.ADMIN;
    }
  }

  public isWriterFiles() {
    if (this.isLoggedIn()) {
      return this.user.role === Role.WRITER_FILES;
    }
  }

  public setToken(user) {
    localStorage.setItem(TOKEN, JSON.stringify(user));
    this.user = user;
  }

  public getLoggedUser() {
    return this.user;
  }
}
