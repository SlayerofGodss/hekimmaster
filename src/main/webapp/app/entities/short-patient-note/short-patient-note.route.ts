import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Routes, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { flatMap } from 'rxjs/operators';

import { Authority } from 'app/shared/constants/authority.constants';
import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { IShortPatientNote, ShortPatientNote } from 'app/shared/model/short-patient-note.model';
import { ShortPatientNoteService } from './short-patient-note.service';
import { ShortPatientNoteComponent } from './short-patient-note.component';
import { ShortPatientNoteDetailComponent } from './short-patient-note-detail.component';
import { ShortPatientNoteUpdateComponent } from './short-patient-note-update.component';

@Injectable({ providedIn: 'root' })
export class ShortPatientNoteResolve implements Resolve<IShortPatientNote> {
  constructor(private service: ShortPatientNoteService, private router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IShortPatientNote> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        flatMap((shortPatientNote: HttpResponse<ShortPatientNote>) => {
          if (shortPatientNote.body) {
            return of(shortPatientNote.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new ShortPatientNote());
  }
}

export const shortPatientNoteRoute: Routes = [
  {
    path: '',
    component: ShortPatientNoteComponent,
    data: {
      authorities: [Authority.USER],
      defaultSort: 'id,asc',
      pageTitle: 'hekimmasterApp.shortPatientNote.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: ShortPatientNoteDetailComponent,
    resolve: {
      shortPatientNote: ShortPatientNoteResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'hekimmasterApp.shortPatientNote.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: ShortPatientNoteUpdateComponent,
    resolve: {
      shortPatientNote: ShortPatientNoteResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'hekimmasterApp.shortPatientNote.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: ShortPatientNoteUpdateComponent,
    resolve: {
      shortPatientNote: ShortPatientNoteResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'hekimmasterApp.shortPatientNote.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
];
