import { Injectable } from '@angular/core';
import {Http,Headers} from '@angular/http';

@Injectable({
  providedIn: 'root'
})
export class UploadImageService {
    
    filesToUpload: Array<File>;

  constructor() { 
      this.filesToUpload = [];
  }
    
    upload(bookId:number){
        //this.makeFileRequest
    }
}
