import { Book } from "./book";

export class CartItem {
    id;
    price: number;
    quantity: number;
    book: Book;

    constructor(id: any, price: number, quantity: number, book: Book) {
        this.id = id;
        this.price = price;
        this.quantity = quantity;
        this.book = book;

    }

    isTheSameBook(newCartItem: CartItem) {
        return this.book.id == newCartItem.book.id;
    }

}