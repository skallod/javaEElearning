import { Injectable } from '@angular/core';
import {Http,Headers} from '@angular/http';
import {environment} from '../../environments/environment';

@Injectable({
  providedIn: 'root'
})
export class GetBookListService {
    
    env=environment;

  constructor(private http:Http) { }
    
    getBookList(){
        let url = this.env.baseurl+"/book/list";
        let headers = new Headers({
            'x-auth-token':localStorage.getItem('xAuthToken'),
            'content-type':'application/json'
        });
        return this.http.get(url,{headers:headers});
    }
}
