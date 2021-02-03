import {User} from './user';

export class Book {
   id: number;
   title: string;
   isbn: string;
   writers: Array<User> = new Array<User>();
   keyTerms: string;
   publisher: string;
   yearPublished: string;
   placePublished: string;
   pages: number;
   synopsis: string;
   editor: User;
   lecturer: User;

   constructor() {
   }
}
