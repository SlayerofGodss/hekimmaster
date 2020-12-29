import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared/util/request-util';
import { IShortPatientNote } from 'app/shared/model/short-patient-note.model';

type EntityResponseType = HttpResponse<IShortPatientNote>;
type EntityArrayResponseType = HttpResponse<IShortPatientNote[]>;

@Injectable({ providedIn: 'root' })
export class ShortPatientNoteService {
  public resourceUrl = SERVER_API_URL + 'api/short-patient-notes';

  constructor(protected http: HttpClient) {}

  create(shortPatientNote: IShortPatientNote): Observable<EntityResponseType> {
    return this.http.post<IShortPatientNote>(this.resourceUrl, shortPatientNote, { observe: 'response' });
  }

  update(shortPatientNote: IShortPatientNote): Observable<EntityResponseType> {
    return this.http.put<IShortPatientNote>(this.resourceUrl, shortPatientNote, { observe: 'response' });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<IShortPatientNote>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IShortPatientNote[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }
}
