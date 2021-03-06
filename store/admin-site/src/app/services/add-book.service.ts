import { Injectable } from '@angular/core';
import {Http,Headers} from '@angular/http';
import {Book} from '../models/book';
import {environment} from '../../environments/environment';

@Injectable({
  providedIn: 'root'
})
export class AddBookService {
    
    env=environment;

  constructor(private http:Http) { }
    
    sendBook(book:Book){
        let url = this.env.baseurl+"/book/add";
        let headers = new Headers({
            'x-auth-token':localStorage.getItem('xAuthToken'),
            'content-type':'application/json'
        });
        return this.http.post(url,JSON.stringify(book),{headers:headers});
    }
}
