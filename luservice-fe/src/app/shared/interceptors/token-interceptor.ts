import {HttpEvent, HttpHandler, HttpInterceptor, HttpRequest, HTTP_INTERCEPTORS, HttpHeaders} from '@angular/common/http';
import {Injectable} from '@angular/core';

import { Observable } from 'rxjs';

const TOKEN_HEADER_KEY = 'Authorization'; 


@Injectable()
export class TokenInterceptor implements HttpInterceptor {
  constructor() { }
 
  intercept(req: HttpRequest<any>, next: HttpHandler): Observable<HttpEvent<any>> {

    const token = localStorage.getItem('token');

    let cloned = req.clone({headers: req.headers.append('Content-Type', 'application/json')});

    if(token != null){
      cloned = req.clone({headers: req.headers.append("Authorization", "Bearer " + token)});
      return next.handle(cloned);
    }
    
    return next.handle(req);
  }
}