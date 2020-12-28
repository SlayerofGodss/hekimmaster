import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Routes, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { flatMap } from 'rxjs/operators';

import { Authority } from 'app/shared/constants/authority.constants';
import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { IRandevu, Randevu } from 'app/shared/model/randevu.model';
import { RandevuService } from './randevu.service';
import { RandevuComponent } from './randevu.component';
import { RandevuDetailComponent } from './randevu-detail.component';
import { RandevuUpdateComponent } from './randevu-update.component';

@Injectable({ providedIn: 'root' })
export class RandevuResolve implements Resolve<IRandevu> {
  constructor(private service: RandevuService, private router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IRandevu> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        flatMap((randevu: HttpResponse<Randevu>) => {
          if (randevu.body) {
            return of(randevu.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new Randevu());
  }
}

export const randevuRoute: Routes = [
  {
    path: '',
    component: RandevuComponent,
    data: {
      authorities: [Authority.USER],
      defaultSort: 'id,asc',
      pageTitle: 'hekimmasterApp.randevu.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: RandevuDetailComponent,
    resolve: {
      randevu: RandevuResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'hekimmasterApp.randevu.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: RandevuUpdateComponent,
    resolve: {
      randevu: RandevuResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'hekimmasterApp.randevu.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: RandevuUpdateComponent,
    resolve: {
      randevu: RandevuResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'hekimmasterApp.randevu.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
];
