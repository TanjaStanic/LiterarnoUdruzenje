export class Book {
    id: number;
    name: string;
    description: string;
    authors: string;
    genres: string;
    price: number;

    constructor(id: number, name: string, description: string, genres: string, authors: string, price: number) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.genres = genres;
        this.authors = authors;
        this.price = price;
    }
}