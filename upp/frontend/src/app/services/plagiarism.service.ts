import { Injectable } from '@angular/core';
import {HttpClient, HttpParams} from '@angular/common/http';
import {ComplainForm} from '../model/complainForm';
import {Observable} from 'rxjs';
import {Book} from '../model/book';

@Injectable({
  providedIn: 'root'
})
export class PlagiarismService {

  constructor(private httpClient: HttpClient) { }

  getComplainForm() {
	    return this.httpClient.get('http://localhost:8080/plagiarism/getComplainForm') as Observable<any>;
  }
  getEditorsForm() {
	    return this.httpClient.get('http://localhost:8080/plagiarism/getEditorsForm') as Observable<any>;
  }
  getNotesForm() {
    return this.httpClient.get('http://localhost:8080/plagiarism/makeNotes') as Observable<any>;
  }
  getReviewForm() {
    return this.httpClient.get('http://localhost:8080/plagiarism/reviewNotes') as Observable<any>;
  }
  getFinalForm() {
    return this.httpClient.get('http://localhost:8080/plagiarism/decideIfPlagiarised') as Observable<any>;
  }
  getAllComments(id) {
    let params = new HttpParams();
    params = params.append('id', id);
    return this.httpClient.get('http://localhost:8080/plagiarism/getComplaintsResponsesComments', {params}) as Observable<any>;
  }
  postForm(parameter, taskId) {
	    let params = new HttpParams();
	    params = params.append('taskId', taskId);
	    return this.httpClient.post('http://localhost:8080/plagiarism/postFormFields', parameter, {params}) as Observable<any>;
  }

  public async getPlagiarismForEditor(username: string): Promise<any[]> {
    let params = new HttpParams();
    params = params.append('username', username);
    const response: any = await this.httpClient.get('http://localhost:8080/plagiarism/getPlagiarismForEditor', {params}).toPromise();
    return response;
  }
}
