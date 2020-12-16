import { Injectable } from '@angular/core';
import {HttpClient} from '@angular/common/http';
import {Observable} from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class UserService {

  constructor(private httpClient: HttpClient) { }

  startProcess() {
    return this.httpClient.get('http://localhost:8080/registration/get') as Observable<any>;
  }

  registerUser(user, taskId) {
    return this.httpClient.post('http://localhost:8080/registration/post/', user) as Observable<any>;
  }

  betaReaderYes(yes) {
    return this.httpClient.post('http://localhost:8080/registration/betaYes/', yes) as Observable<any>;
  }

  betaReaderNo(no) {
    return this.httpClient.post('http://localhost:8080/registration/betaNo/', no) as Observable<any>;
  }
}
