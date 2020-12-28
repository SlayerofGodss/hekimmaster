import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared/util/request-util';
import { IRandevu } from 'app/shared/model/randevu.model';

type EntityResponseType = HttpResponse<IRandevu>;
type EntityArrayResponseType = HttpResponse<IRandevu[]>;

@Injectable({ providedIn: 'root' })
export class RandevuService {
  public resourceUrl = SERVER_API_URL + 'api/randevus';

  constructor(protected http: HttpClient) {}

  create(randevu: IRandevu): Observable<EntityResponseType> {
    return this.http.post<IRandevu>(this.resourceUrl, randevu, { observe: 'response' });
  }

  update(randevu: IRandevu): Observable<EntityResponseType> {
    return this.http.put<IRandevu>(this.resourceUrl, randevu, { observe: 'response' });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<IRandevu>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IRandevu[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }
}
