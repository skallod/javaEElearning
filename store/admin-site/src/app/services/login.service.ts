import { Injectable } from '@angular/core';
//import { HttpClientModule } from '@angular/common/http';
import {Http,Headers} from '@angular/http';
import {Observable} from 'rxjs';
import {environment} from '../../environments/environment';

@Injectable({
  providedIn: 'root'
})
export class LoginService {
    
    env=environment;

  constructor(private http:Http) {
    console.log('construcor login service');
  }

  sendCredential (username:string, password:string){
      let url=this.env.baseurl+"/token";
      let encodedCredentials=btoa(username+":"+password);
      let basicHeader="Basic "+encodedCredentials;
      let headers=new Headers ({
          'Content-Type':'application/x-www-form-urlencoded',
          'X-Requested-With':'XMLHttpRequest',//spring boot not response WWW-Authenticate: Basic, which initiate browser popup
          'Authorization':basicHeader
      });
          return this.http.get(url,{headers:headers});
  }
    
    checkSession(){
        let url=this.env.baseurl+"/checkSession";
      let headers=new Headers ({
          'X-Requested-With':'XMLHttpRequest',
          "x-auth-token":localStorage.getItem('xAuthToken')
      });
          return this.http.get(url,{headers:headers});
    }
    
    logout(){
        let url=this.env.baseurl+"/user/logout";
      let headers=new Headers ({
          'X-Requested-With':'XMLHttpRequest',
          "x-auth-token":localStorage.getItem('xAuthToken')
      });
          return this.http.post(url,'',{headers:headers});
    }

}
