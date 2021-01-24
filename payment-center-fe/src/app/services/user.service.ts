import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { environment } from 'src/environments/environment';

@Injectable({
  providedIn: 'root'
})
export class UserService {

  constructor(private httpClient: HttpClient) { }

  getClients(): Observable<any> {
    return this.httpClient.get(`${environment.baseUrl}clients`);
  }

  subscribe(): Observable<any> {
    return this.httpClient.get(`${environment.baseUrl}paypal/subscribe`, { responseType: 'text' });
  }

  getUserSubscription(id) {
    return this.httpClient.get(`${environment.baseUrl}auth/subscriptions/${id}`);
  }

  paypalPaymnetTest(): Observable<any> {
    return this.httpClient.get(`${environment.baseUrl}paypal`, { responseType: 'text' });
  }

}
