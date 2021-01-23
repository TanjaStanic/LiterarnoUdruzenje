export class ShippingInfo {

    recipient: string;
    address: string;
    city; string;
    country: string;
    postalCode: string;

    constructor(recipient: string, city: string, address: string, postalCode: string) {
        this.address = address;
        this.recipient = recipient;
        this.city = city;
        this.postalCode = postalCode;
    }
}