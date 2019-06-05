import { Component, OnInit } from '@angular/core';
import {UploadImageService } from '../../services/upload-image.service';
import {EditBookService } from '../../services/edit-book.service';
import {GetBookService } from '../../services/get-book.service';
import {Book} from '../../models/book';
import { Params, ActivatedRoute, Router} from '@angular/router'; 

@Component({
  selector: 'app-edit-book',
  templateUrl: './edit-book.component.html',
  styleUrls: ['./edit-book.component.css']
})
export class EditBookComponent implements OnInit {
    
    private bookId: number;
    book: Book = new Book();
    bookUpdated: boolean;

  constructor(public uploadImageService: UploadImageService,
              private editBookService: EditBookService,
              private getBookService: GetBookService,
              private route: ActivatedRoute,
              private router: Router) { }
    
    onSubmit(){
        this.editBookService.sendBook(this.book).subscribe(
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
                this.bookUpdated = true;
            },
            error=>console.log(error)
        );
    }

  ngOnInit() {
      this.bookUpdated=false;
      this.route.params.forEach((params:Params)=>{
         this.bookId = Number.parseInt(params['id']); 
      });
      this.getBookService.getBook(this.bookId).subscribe(
          res=>{
              this.book=res.json();
          },
          error=>console.log(error)
      );
  }

}
