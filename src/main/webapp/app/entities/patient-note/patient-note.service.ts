import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared/util/request-util';
import { IPatientNote } from 'app/shared/model/patient-note.model';

type EntityResponseType = HttpResponse<IPatientNote>;
type EntityArrayResponseType = HttpResponse<IPatientNote[]>;

@Injectable({ providedIn: 'root' })
export class PatientNoteService {
  public resourceUrl = SERVER_API_URL + 'api/patient-notes';

  constructor(protected http: HttpClient) {}

  create(patientNote: IPatientNote): Observable<EntityResponseType> {
    return this.http.post<IPatientNote>(this.resourceUrl, patientNote, { observe: 'response' });
  }

  update(patientNote: IPatientNote): Observable<EntityResponseType> {
    return this.http.put<IPatientNote>(this.resourceUrl, patientNote, { observe: 'response' });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<IPatientNote>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IPatientNote[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }
}
