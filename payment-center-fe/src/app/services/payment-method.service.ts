import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { environment } from 'src/environments/environment';

@Injectable({
  providedIn: 'root'
})
export class PaymentMethodService {

  constructor(private httpClient: HttpClient) { }

  getPaymentMethods(): Observable<any> {
    return this.httpClient.get(`${environment.baseUrl}payment-methods`);
  }
}
