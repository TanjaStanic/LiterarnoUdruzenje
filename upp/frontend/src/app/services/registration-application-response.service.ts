import { Injectable } from '@angular/core';
import {RegistrationApplicationResponse} from '../model/registrationApplicationResponse';
import {HttpClient, HttpParams} from '@angular/common/http';
import {Observable} from 'rxjs';


@Injectable({
  providedIn: 'root'
})
export class RegistrationApplicationResponseService {

  rasList: Array<RegistrationApplicationResponse> = new Array<RegistrationApplicationResponse>();
  constructor(private httpClient: HttpClient) { }

  public async getListForReviews(username: string): Promise<RegistrationApplicationResponse[]> {
    let params = new HttpParams();
    params = params.append('username', username);
    // tslint:disable-next-line:max-line-length
    const response: any = await this.httpClient.get('http://localhost:8080/rap/getByUsername', {params}).toPromise();
    return response;
  }

  public submitReview(review, username, taskId) {
    let params = new HttpParams();
    params = params.append('username', username);
    params = params.append('taskId', taskId);
    return this.httpClient.post('http://localhost:8080/review/submit-review/', review, {params}) as Observable<any>;
  }

}
