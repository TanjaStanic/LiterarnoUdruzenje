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

  edit(paymentMethod: PaymentMethod): Observable<any> {
    console.log(paymentMethod.id);
    return this.httpClient.put(`${environment.baseUrl}payment-methods/${paymentMethod.id}`, paymentMethod);
  }
 
  getClientsPaymentMethods(id:number): Observable<any> {
	return this.httpClient.get(`${environment.baseUrl}payment-methods/clientsMethods/${id}`);
  }

  getNoClientsPaymentMethods(id:number): Observable<any> {
	return this.httpClient.get(`${environment.baseUrl}payment-methods/noClientsMethods/${id}`);
  }
  
  deleteFromClient(userId: number, pmId: number): Observable<any> {
	return this.httpClient.get(`${environment.baseUrl}payment-methods/delete/${userId}/${pmId}`);
  }

  add(userId: number, pmId: number): Observable<any> {
	return this.httpClient.get(`${environment.baseUrl}payment-methods/add/${userId}/${pmId}`);
  }

}
