import { Injectable } from '@angular/core';
import { Http,Headers } from '@angular/http';
import {Book} from '../models/book';

@Injectable({
  providedIn: 'root'
})
export class GetBookService {

    constructor(private http:Http) { }
    
    getBook(id:number){
        let url = "http://localhost:8181/book/"+id;
        let headers = new Headers({
            'x-auth-token':localStorage.getItem('xAuthToken'),
            'content-type':'application/json'
        });
        return this.http.get(url,{headers:headers});
    }

}
