import { Component, OnInit } from '@angular/core';
import { Book } from '../../models/book';
import { AddBookService } from '../../services/add-book.service'; 
import {UploadImageService} from '../../services/upload-image.service';
import {Router} from '@angular/router';

@Component({
  selector: 'app-add-new-book',
  templateUrl: './add-new-book.component.html',
  styleUrls: ['./add-new-book.component.css']
})
export class AddNewBookComponent implements OnInit {
    
    newBook:Book = new Book();
    bookAdded: boolean;

  constructor(private addBookService:AddBookService, public uploadImageService:UploadImageService,private router:Router) { }
    
    onSubmit(){
        this.addBookService.sendBook(this.newBook).subscribe(
            res=>{
                let t1 = JSON.stringify(res);
                console.log("t1 "+t1);
                let t2 = JSON.parse(t1);
                console.log("t2 "+t2);
                let t3 = t2._body;
                console.log("t3 "+t3);
                let t4 = JSON.parse(t3);
                console.log("t4 "+t4);
                let t5 = t4.id;
                console.log("t5 "+t5);
                this.uploadImageService.upload(t5);
                this.bookAdded = true;
                this.newBook = new Book();//todo route to view-book
            },
            error=>{
                console.log(error);
                let e1 = JSON.stringify(error);
                console.log("e1 "+e1);
                let e2 = JSON.parse(e1);
                console.log("e2 "+e2);
                if(e2.status==401){
                   this.router.navigate(['/']);
               }
            }
        );
    }

  ngOnInit() {
    console.log("new book on init");
    this.bookAdded = false;
  }

}
