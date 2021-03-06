import { Injectable } from '@angular/core';
import {HttpClient, HttpParams} from '@angular/common/http';
import {Router} from '@angular/router';
import {Observable} from 'rxjs';
import {RegistrationApplicationResponse} from '../model/registrationApplicationResponse';
import {Book} from '../model/book';

@Injectable({
  providedIn: 'root'
})
export class BookService {

  constructor(private httpClient: HttpClient,
              private router: Router) { }

 getBookDetailForm() {
   return this.httpClient.get('http://localhost:8080/book/getBookDetailsForm') as Observable<any>;
 }
  getInitialBookReviewForm() {
    return this.httpClient.get('http://localhost:8080/book/initialBookReviewForm') as Observable<any>;
  }
  getSubmitManuscriptForm() {
    return this.httpClient.get('http://localhost:8080/book/submitManuscriptForm') as Observable<any>;
  }
  acceptManuscriptAndsendToBetaReadersForm() {
    return this.httpClient.get('http://localhost:8080/book/acceptManuscriptAndsendToBetaReadersForm') as Observable<any>;
  }
  betaReadersForm() {
    return this.httpClient.get('http://localhost:8080/book/betaReadersForm') as Observable<any>;
  }
  BetaReaderCommentsForm() {
    return this.httpClient.get('http://localhost:8080/book/BetaReaderCommentsForm') as Observable<any>;
  }
  improveManusciprtForm() {
    return this.httpClient.get('http://localhost:8080/book/improveManuscriptForm') as Observable<any>;
  }
  needMoreWorkForm() {
    return this.httpClient.get('http://localhost:8080/book/needMoreWorkForm') as Observable<any>;
  }
  lecturerNotesTypos() {
    return this.httpClient.get('http://localhost:8080/book/lecturerNotesTypos') as Observable<any>;
  }
  editorHasSuggestionsForm() {
    return this.httpClient.get('http://localhost:8080/book/editorHasSuggestions') as Observable<any>;
  }

  public async getBooksForInitialReview(username: string): Promise<Book[]> {
    let params = new HttpParams();
    params = params.append('username', username);
    const response: any = await this.httpClient.get('http://localhost:8080/book/booksForInitialReviews', {params}).toPromise();
    return response;
  }
  public async getBooksForWriter(username: string): Promise<Book[]> {
    let params = new HttpParams();
    params = params.append('username', username);
    const response: any = await this.httpClient.get('http://localhost:8080/book/getBooksForWriter', {params}).toPromise();
    return response;
  }
  public async getBooksForReview(username: string): Promise<Book[]> {
    let params = new HttpParams();
    params = params.append('username', username);
    const response: any = await this.httpClient.get('http://localhost:8080/book/getBooksForReview', {params}).toPromise();
    return response;
  }
  public async getBooksForBetaReader(username: string): Promise<Book[]> {
    let params = new HttpParams();
    params = params.append('username', username);
    const response: any = await this.httpClient.get('http://localhost:8080/book/getBooksForBetaReader', {params}).toPromise();
    return response;
  }
  public async getBooksForLecturer(username: string): Promise<Book[]> {
    let params = new HttpParams();
    params = params.append('username', username);
    const response: any = await this.httpClient.get('http://localhost:8080/book/getBooksForLecturer', {params}).toPromise();
    return response;
  }
  public uploadFile(file) {
      const formData = new FormData();
      formData.append('file', file);
      return this.httpClient.post('http://localhost:8080/files/upload-document', formData);
  }

  postForm(parameter, taskId) {
    let params = new HttpParams();
    params = params.append('taskId', taskId);
    return this.httpClient.post('http://localhost:8080/book/postFormFields', parameter, {params}) as Observable<any>;
  }

}
