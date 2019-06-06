import { Injectable } from '@angular/core';
import {Book} from '../models/book';
import {Http,Headers} from '@angular/http';
import {environment} from '../../environments/environment';

@Injectable({
  providedIn: 'root'
})
export class RemoveBookService {

  env=environment;

  constructor(private http: Http) { }
    
  sendBook(bookId:number){
        let url = this.env.baseurl+"/book/delete";
        let headers = new Headers({
            'x-auth-token':localStorage.getItem('xAuthToken'),
            'content-type':'application/json'
        });
        return this.http.post(url,bookId,{headers:headers});
    }
}
