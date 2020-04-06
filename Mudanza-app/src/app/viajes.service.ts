import { Injectable, NgZone } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Observable, throwError } from 'rxjs';
import { environment as env } from '../environments/environment';

@Injectable({
  providedIn: 'root'
})
export class ViajesService {

  constructor(private _zone: NgZone) { }

  testFlux(recurso: string, documento: string): Observable<any> {
    return Observable.create(observer => {
      const eventSource = new EventSource(env.verificarViajesUri+'/'+recurso+'/'+documento);
      eventSource.onmessage = event => {
        this._zone.run(() => {
          observer.next(event);
        });
      }

      eventSource.onerror = error => {
        if (eventSource.readyState === 0) {
          console.log('The stream has been closed by the server.');
          eventSource.close();
          observer.complete();
        } else {
          this._zone.run(() => {
            observer.error(error)
          });
        }
      }
    })
  }

}
