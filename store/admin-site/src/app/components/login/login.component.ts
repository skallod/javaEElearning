import { Component, OnInit } from '@angular/core';
import {LoginService} from '../../services/login.service';

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.css']
})
export class LoginComponent implements OnInit {

    credential = {'username':'','password':''};
    loggedIn = false;

constructor(private loginService: LoginService) {
  console.log('login constructor');
}

onSubmit(){
    this.loginService.sendCredential(this.credential.username,this.credential.password).subscribe(
        res=>{
            console.log("success");
            console.log(res);
            localStorage.setItem("xAuthToken",res.json().token);
            this.loggedIn = true;
            location.reload();
        },
        error=>{
            console.log(error);
        }
    );
}

  ngOnInit() {
      console.log("login component on init");
      this.loginService.checkSession().subscribe(
        res=>{
          console.log("check session true");
          this.loggedIn = true;  
        },
        error=>{
          console.log("check session false");
          this.loggedIn = false;
        }
      );
  }

}
