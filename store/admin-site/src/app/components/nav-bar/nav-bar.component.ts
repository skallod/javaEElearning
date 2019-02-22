import { Component, OnInit } from '@angular/core';
import {LoginService} from '../../services/login.service';
import {Router} from '@angular/router';

@Component({
  selector: 'app-nav-bar',
  templateUrl: './nav-bar.component.html',
  styleUrls: ['./nav-bar.component.css']
})
export class NavBarComponent implements OnInit {
    
    private loggedIn = false;

  constructor(private loginService:LoginService,private router:Router) { }
    
  logout(){
      console.log("nav logout func");
      this.loginService.logout().subscribe(
          res=>{
              location.reload();
          },
          error=>{
              console.log(error);
          }
      );
      this.router.navigate(['/']);
  }
    
  ngOnInit() {
      console.log("nav component on init");
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
