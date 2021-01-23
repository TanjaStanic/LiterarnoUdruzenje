import { OrderItem } from "./order-item";
import { ShippingInfo } from "./shipping-info";

export class Order {
    orderItems: OrderItem[];
    shippingInfo: ShippingInfo;
    amount: number;
}