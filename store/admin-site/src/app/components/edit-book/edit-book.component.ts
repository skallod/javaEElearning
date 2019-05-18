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
    private book: Book = new Book();
    private bookUpdated: boolean;

  constructor(private uploadImageService: UploadImageService,
              private editBookService: EditBookService,
              private getBookService: GetBookService) { }

  ngOnInit() {
  }

}
