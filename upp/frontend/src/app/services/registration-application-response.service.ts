import { Injectable } from '@angular/core';
import {RegistrationApplicationResponse} from '../model/registrationApplicationResponse';
import {HttpClient, HttpParams} from '@angular/common/http';


@Injectable({
  providedIn: 'root'
})
export class RegistrationApplicationResponseService {

  rasList: Array<RegistrationApplicationResponse> = new Array<RegistrationApplicationResponse>();
  constructor(private httpClient: HttpClient) { }

  public async getListForReviews(username: string): Promise<RegistrationApplicationResponse> {
    let params = new HttpParams();
    params = params.append('username', username);
    // tslint:disable-next-line:max-line-length
    const response: any = await this.httpClient.get('http://localhost:8080/rap/getByUsername', {params}).toPromise();
    return response;
  }

}
