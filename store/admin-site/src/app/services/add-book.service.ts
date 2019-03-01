import { Injectable } from '@angular/core';
import {Http,Headers} from '@angular/http';
import {Book} from '../models/book';

@Injectable({
  providedIn: 'root'
})
export class AddBookService {

  constructor(private http:Http) { }
    
    sendBook(book:Book){
        let url = "http://localhost:8181/book/add";
        let headers = new Headers({
            'x-auth-token':localStorage.getItem('xAuthToken'),
            'content-type':'application/json'
        });
        return this.http.post(url,JSON.stringify(book),{headers:headers});
    }
}
