import { Injectable } from '@angular/core';
import {HttpClient} from '@angular/common/http';
import {Genre} from '../model/genre';
import {Observable} from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class GenreService {

  genres: Array<Genre> = new Array<Genre>();
  constructor(private httpClient: HttpClient) { }

  public getAllGenres(): Array<Genre> {
    this.genres = new Array<Genre>();
    this.httpClient.get('http://localhost:8080/genre/all').subscribe((data: Genre[]) => {
          this.genres = data;
          console.log(data);
      },
      error => {
        console.log(error);
      }
    );
    console.log(this.genres);
    return this.genres;
  }

  public getGenres(): Array<Genre> {
    return this.genres;
  }

  getFieldsGenres() {
    return this.httpClient.get('http://localhost:8080/registration/getGenresForm') as Observable<any>;
  }

}
