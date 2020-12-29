import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';
import * as moment from 'moment';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared/util/request-util';
import { IDay } from 'app/shared/model/day.model';

type EntityResponseType = HttpResponse<IDay>;
type EntityArrayResponseType = HttpResponse<IDay[]>;

@Injectable({ providedIn: 'root' })
export class DayService {
  public resourceUrl = SERVER_API_URL + 'api/days';

  constructor(protected http: HttpClient) {}

  create(day: IDay): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(day);
    return this.http
      .post<IDay>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  update(day: IDay): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(day);
    return this.http
      .put<IDay>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http
      .get<IDay>(`${this.resourceUrl}/${id}`, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<IDay[]>(this.resourceUrl, { params: options, observe: 'response' })
      .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  protected convertDateFromClient(day: IDay): IDay {
    const copy: IDay = Object.assign({}, day, {
      randevuDate: day.randevuDate && day.randevuDate.isValid() ? day.randevuDate.toJSON() : undefined,
    });
    return copy;
  }

  protected convertDateFromServer(res: EntityResponseType): EntityResponseType {
    if (res.body) {
      res.body.randevuDate = res.body.randevuDate ? moment(res.body.randevuDate) : undefined;
    }
    return res;
  }

  protected convertDateArrayFromServer(res: EntityArrayResponseType): EntityArrayResponseType {
    if (res.body) {
      res.body.forEach((day: IDay) => {
        day.randevuDate = day.randevuDate ? moment(day.randevuDate) : undefined;
      });
    }
    return res;
  }
}
