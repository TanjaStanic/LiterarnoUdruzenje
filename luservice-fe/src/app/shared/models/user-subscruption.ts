export class UserSubscription {
    id: number;
    expirationDate: Date;
    subscriptionStatus: String;
    paymentAmount: number;
    merchantOrderId: number;
    currency: String;

    constructor(id, expirationDate, subscriptionStatus, paymentAmount, merchantOrderId, currency) {
        this.id = id;
        this.expirationDate = expirationDate;
        this.subscriptionStatus = subscriptionStatus;
        this.paymentAmount = paymentAmount;
        this.merchantOrderId = merchantOrderId;
        this.currency = currency;
    }

}