import { Book } from "./book";

export class OrderItem {
    id;
    price: number;
    date: Date;
    quantity: number;
    book: Book;

    constructor(id: any, price: number, date: Date, quantity: number, book: Book) {
        this.id = id;
        this.price = price;
        this.date = date;
        this.quantity = quantity;
        this.book = book;
    }

}