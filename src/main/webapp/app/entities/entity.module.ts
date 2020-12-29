import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

@NgModule({
  imports: [
    RouterModule.forChild([
      {
        path: 'patient',
        loadChildren: () => import('./patient/patient.module').then(m => m.HekimmasterPatientModule),
      },
      {
        path: 'randevu',
        loadChildren: () => import('./randevu/randevu.module').then(m => m.HekimmasterRandevuModule),
      },
      {
        path: 'day',
        loadChildren: () => import('./day/day.module').then(m => m.HekimmasterDayModule),
      },
      {
        path: 'patient-note',
        loadChildren: () => import('./patient-note/patient-note.module').then(m => m.HekimmasterPatientNoteModule),
      },
      {
        path: 'short-patient-note',
        loadChildren: () => import('./short-patient-note/short-patient-note.module').then(m => m.HekimmasterShortPatientNoteModule),
      },
      /* jhipster-needle-add-entity-route - JHipster will add entity modules routes here */
    ]),
  ],
})
export class HekimmasterEntityModule {}
