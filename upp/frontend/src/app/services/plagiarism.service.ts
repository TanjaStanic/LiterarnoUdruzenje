import { Injectable } from '@angular/core';
import {HttpClient, HttpParams} from '@angular/common/http';
import {ComplainForm} from '../model/complainForm';
import {Observable} from 'rxjs';

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
  postForm(parameter, taskId) {
	    let params = new HttpParams();
	    params = params.append('taskId', taskId);
	    return this.httpClient.post('http://localhost:8080/plagiarism/postFormFields', parameter, {params}) as Observable<any>;
	  }
}
