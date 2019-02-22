import { BrowserModule } from '@angular/platform-browser';
import { NgModule } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { HttpModule } from '@angular/http';

import { AppRoutingModule } from './app-routing.module';
import {NoopAnimationsModule} from '@angular/platform-browser/animations';

import { AppComponent } from './app.component';
import { NavBarComponent } from './components/nav-bar/nav-bar.component';
import { LoginComponent } from './components/login/login.component';

import {LoginService} from './services/login.service';
import { AddNewBookComponent } from './components/add-new-book/add-new-book.component';
import { AddBookService } from './services/add-book.service';
//import {MatButtonModule} from '@angular/material/button';
//import {MatToolbarModule} from '@angular/material/toolbar';

export class PizzaPartyAppModule { }

@NgModule({
  declarations: [
    AppComponent,
    NavBarComponent,
    LoginComponent,
    AddNewBookComponent
  ],
  imports: [
    BrowserModule,
    AppRoutingModule,
    NoopAnimationsModule,
      HttpModule,
//    MatToolbarModule,
//      MatButtonModule,
      FormsModule
  ],
  providers: [
     LoginService,
      AddBookService
  ],
  bootstrap: [AppComponent]
})
export class AppModule { }
