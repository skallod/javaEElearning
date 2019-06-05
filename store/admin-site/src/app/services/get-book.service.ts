import { Injectable } from '@angular/core';
import { Http,Headers } from '@angular/http';
import {Book} from '../models/book';
import {environment} from '../../environments/environment';

@Injectable({
  providedIn: 'root'
})
export class GetBookService {
    
    env=environment;

    constructor(private http:Http) { }
    
    getBook(id:number){
        let url = this.env.baseurl+"/book/"+id;
        let headers = new Headers({
            'x-auth-token':localStorage.getItem('xAuthToken'),
            'X-Requested-With':'XMLHttpRequest',
            'content-type':'application/json'
        });
        return this.http.get(url,{headers:headers});
    }

}
