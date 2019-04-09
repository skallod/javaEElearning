import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';
import {LoginComponent} from './components/login/login.component';
import {AddNewBookComponent} from './components/add-new-book/add-new-book.component';
import {BookListComponent} from './components/book-list/book-list.component';
import {ViewBookComponent} from './components/view-book/view-book.component';

const routes: Routes = [
     { path: '', redirectTo: '/login', pathMatch: 'full' },
    {path: 'login', component: LoginComponent},
    {path: 'newbook', component: AddNewBookComponent},
    {path: 'bookList', component: BookListComponent},
    {path: 'viewBook/:id', component: ViewBookComponent}
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
