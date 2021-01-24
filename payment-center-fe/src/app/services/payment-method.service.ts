import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { environment } from 'src/environments/environment';
import { PaymentMethod } from '../shared/models/payment-method';

@Injectable({
  providedIn: 'root'
})
export class PaymentMethodService {

  constructor(private httpClient: HttpClient) { }

  getPaymentMethods(): Observable<any> {
    return this.httpClient.get(`${environment.baseUrl}payment-methods`);
  }

  delete(id: number): Observable<any> {
    return this.httpClient.delete(`${environment.baseUrl}payment-methods/${id}`);
  }

  create(paymentMethod: PaymentMethod): Observable<any> {
    return this.httpClient.post(`${environment.baseUrl}payment-methods`, paymentMethod);
  }

}
