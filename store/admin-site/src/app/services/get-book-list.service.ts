import { Injectable } from '@angular/core';
import {Http,Headers} from '@angular/http';

@Injectable({
  providedIn: 'root'
})
export class GetBookListService {

  constructor(private http:Http) { }
    
    getBookList(){
        let url = "http://localhost:8181/book/list";
        let headers = new Headers({
            'x-auth-token':localStorage.getItem('xAuthToken'),
            'content-type':'application/json'
        });
        return this.http.get(url,{headers:headers});
    }
}
