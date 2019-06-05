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
    bookList:Book[];
    private allChecked:boolean;
    private removeBookList: Book[] = new Array();

  constructor(private getBookListService:GetBookListService
               ,private router:Router, private removeBookService:RemoveBookService) { }
    
    onSelect(book:Book){
        this.selectedBook = book;
        this.router.navigate(['viewBook',this.selectedBook.id]);
    }
    
    getBookList(){
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

  ngOnInit() {
      this.getBookList();
  }
    
    removeBook(book:Book){
        //todo invoke modal dialog 
        console.log("remove book");
        this.removeBookService.sendBook(book.id).subscribe(
            res=>{
                console.log(res);
                this.getBookList();
            },error=>console.log(error)
        );
    }
    
    updateRemoveBookList(checked:boolean, book:Book){
        if(checked){
            this.removeBookList.push(book);
        }else{
            this.removeBookList.splice(this.removeBookList.indexOf(book),1);
        }
    }
    
    updateSelected(checked: boolean){
        if(checked){
            this.allChecked = true;
            this.removeBookList= this.bookList.slice();
        }else{
            this.allChecked = false;
            this.removeBookList =[];
        }
    }
    
    removeSelectedBooks(){
        console.log("remove selected");
        for(let book of this.removeBookList){
            this.removeBookService.sendBook(book.id).subscribe(
                res=>{
                    console.log(res);
                    this.getBookList();
                },error=>console.log(error)
            );
        }
        //this.getBookList();//warn invokes parallel
    }

}
