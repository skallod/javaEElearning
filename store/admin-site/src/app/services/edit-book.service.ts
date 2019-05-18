import { Injectable } from '@angular/core';
import {Book} from '../models/book';
import {Http,Headers} from '@angular/http';
import {environment} from '../../environments/environment';

@Injectable({
  providedIn: 'root'
})
export class EditBookService {
    
    env=environment;

  constructor(private http: Http) { }
    
    sendBook(book:Book){
        let url = this.env.baseurl+"/book/update";
        let headers = new Headers({
            'x-auth-token':localStorage.getItem('xAuthToken'),
            'content-type':'application/json'
        });
        return this.http.post(url,JSON.stringify(book),{headers:headers});
    }
}
