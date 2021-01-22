import {Injectable} from '@angular/core';
import { HttpClient } from '@angular/common/http';
import {Observable} from 'rxjs/internal/Observable';
import { map } from 'rxjs/operators';
import {MyEvent} from "./event";

@Injectable({ providedIn: 'root' })
export class EventService {

    constructor(private http: HttpClient) {
    }

    getEvents(): Observable<any> {
        return this.http.get('home/patient.json')
            .pipe(map((response) => response as MyEvent[]));
    }
}
