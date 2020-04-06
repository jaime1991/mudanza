import { Injectable } from '@angular/core';
import {
  HttpClient,
  HttpHeaders,
  HttpErrorResponse
} from '@angular/common/http';
import { map, catchError, tap } from 'rxjs/operators';
import { Observable } from 'rxjs';
import { environment as env } from '../environments/environment';

@Injectable({
  providedIn: 'root'
})
export class ArchivosService {

  constructor(private client: HttpClient) { }

  postFile(fileToUpload: File): Observable<any> {
    const formData: FormData = new FormData();
    formData.append('file', fileToUpload, fileToUpload.name);
      return this.client
        .post(env.cargaArchivoUri, formData, {
          headers: {
            Authorization: 'Bearer eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9'
          }
        });
    
  }
}
