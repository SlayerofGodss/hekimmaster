import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Routes, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { flatMap } from 'rxjs/operators';

import { Authority } from 'app/shared/constants/authority.constants';
import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { IDay, Day } from 'app/shared/model/day.model';
import { DayService } from './day.service';
import { DayComponent } from './day.component';
import { DayDetailComponent } from './day-detail.component';
import { DayUpdateComponent } from './day-update.component';

@Injectable({ providedIn: 'root' })
export class DayResolve implements Resolve<IDay> {
  constructor(private service: DayService, private router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IDay> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        flatMap((day: HttpResponse<Day>) => {
          if (day.body) {
            return of(day.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new Day());
  }
}

export const dayRoute: Routes = [
  {
    path: '',
    component: DayComponent,
    data: {
      authorities: [Authority.USER],
      defaultSort: 'id,asc',
      pageTitle: 'hekimmasterApp.day.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: DayDetailComponent,
    resolve: {
      day: DayResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'hekimmasterApp.day.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: DayUpdateComponent,
    resolve: {
      day: DayResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'hekimmasterApp.day.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: DayUpdateComponent,
    resolve: {
      day: DayResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'hekimmasterApp.day.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
];
