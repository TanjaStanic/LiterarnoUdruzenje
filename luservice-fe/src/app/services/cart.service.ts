import { Injectable } from '@angular/core';
import { BehaviorSubject, Observable } from 'rxjs';
import { HttpClient } from '@angular/common/http';
import { environment } from 'src/environments/environment';
import { CartItem } from '../shared/models/cart-item';
import { Order } from '../shared/models/order';
import { OrderItem } from '../shared/models/order-item';
import { ShippingInfo } from '../shared/models/shipping-info';

const itemsLS: CartItem[] = JSON.parse(localStorage.getItem('items'))
@Injectable({
  providedIn: 'root'
})
export class CartService {

  private $items = new BehaviorSubject<CartItem[]>(itemsLS || []);

  constructor(private httpClient: HttpClient) { }

  get items(): BehaviorSubject<CartItem[]> {
    return this.$items;
  }

  addItem(cartItem: CartItem) {
    let itemExists = false;
    const items = this.items.getValue();
    console.log(cartItem);
    for (let item of items) {
      if (cartItem.isTheSameBook(item)) {
        item.quantity += cartItem.quantity;
        item.price += cartItem.price;
        itemExists = true;
        break;
      }
    }

    if (!itemExists) {
      this.items.getValue().push(cartItem);
    }

    localStorage.setItem('items', JSON.stringify(this.items.getValue()));
    this.$items.next(this.items.getValue());

  }

  removeItem(id: number) {

    const filteredItems = this.items.getValue().filter(item => item.id != id);
    this.$items.next(filteredItems);
    localStorage.setItem('items', JSON.stringify(filteredItems));

  }
  empty() {
    localStorage.removeItem('items');
    this.$items.next([]);
  }

  order(shippingInfo: ShippingInfo): Observable<any> {
    let order: Order = new Order();
    order.orderItems = [];
    order.shippingInfo = shippingInfo;
    order.amount = 0.0;
    for (let cartItem of this.items.getValue()) {
      const orderItem = new OrderItem(null, cartItem.price, new Date(), cartItem.quantity, cartItem.book);
      order.orderItems.push(orderItem);
      order.amount += cartItem.price;
    }
    console.log(order);
    return this.httpClient.post(`${environment.baseUrl}auth/orders`, order, { responseType: 'text' });
  }
}
