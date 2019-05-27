import { Component, OnInit } from '@angular/core';
import {Book} from '../../models/book';
import {Router} from '@angular/router';
import {LoginService} from '../../services/login.service';
import {GetBookListService} from '../../services/get-book-list.service';
import {RemoveBookService} from '../../services/remove-book.service';

@Component({
  selector: 'app-book-list',
  templateUrl: './book-list.component.html',
  styleUrls: ['./book-list.component.css']
})
export class BookListComponent implements OnInit {
    
    private selectedBook:Book;
    private checked:boolean;
    private bookList:Book[];
    private allChecked:boolean;
    private removeBookList: Book[] = new Array();

  constructor(private getBookListService:GetBookListService
               ,private router:Router, private removeBookService:RemoveBookService) { }
    
    onSelect(book:Book){
        this.selectedBook = book;
        this.router.navigate(['viewBook',this.selectedBook.id]);
    }

  ngOnInit() {
      this.getBookListService.getBookList().subscribe(
      res=>{
          console.log(res.json());
          this.bookList=res.json();
      },
      error=>{
          console.log(error);
      }
      );
  }
    
    removeBook(book:Book){
        //todo invoke modal dialog 
        console.log("remove book");
        this.removeBookService.sendBook(book.id).subscribe(
            res=>{
                console.log(res);
                location.reload();
            },error=>console.log(error)
        );
    }

}
