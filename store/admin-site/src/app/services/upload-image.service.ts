import { Injectable } from '@angular/core';
import {Http,Headers} from '@angular/http';
import {environment} from '../../environments/environment';

@Injectable({
  providedIn: 'root'
})
export class UploadImageService {
    
    env=environment;
    
    filesToUpload: Array<File>;

  constructor() { 
      this.filesToUpload = [];
  }
    
    upload(bookId:number){
        if(this.filesToUpload.length!==0){
            this.makeFileRequest(this.env.baseurl+"/book/add/image?id="+bookId,[],this.filesToUpload).then((result)=>{
                console.log(result);
            },(error)=>{
                console.log(error);
            });
        }else{
            console.log("empty images, not send");
        }
    }
    
    fileChangeEvent(fileInput: any){
        this.filesToUpload = <Array<File>> fileInput.target.files;
        for(var i=0; i<this.filesToUpload.length; i++){
            console.log("file change event "+this.filesToUpload[i].name);
        }
    }
    
    makeFileRequest(url:string,params:Array<string>,files:Array<File>){
        return new Promise((resolve,reject)=>{
            var formData:any = new FormData();
            var xhr = new XMLHttpRequest();
            for(var i=0; i<files.length; i++){
                formData.append("uploads[]",files[i],files[i].name);
            }
            xhr.onreadystatechange = function(){
                if(xhr.readyState==4){
                    if(xhr.status==200){
                        console.log("image upload successfully!");
                    }else{
                        reject(xhr.response);
                    }
                }
            }
            xhr.open("POST",url,true);
            xhr.setRequestHeader("x-auth-token",localStorage.getItem("xAuthToken"));
            xhr.send(formData);
        })
    }
}
