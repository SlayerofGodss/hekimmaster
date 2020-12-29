import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { HekimmasterSharedModule } from 'app/shared/shared.module';
import { ShortPatientNoteComponent } from './short-patient-note.component';
import { ShortPatientNoteDetailComponent } from './short-patient-note-detail.component';
import { ShortPatientNoteUpdateComponent } from './short-patient-note-update.component';
import { ShortPatientNoteDeleteDialogComponent } from './short-patient-note-delete-dialog.component';
import { shortPatientNoteRoute } from './short-patient-note.route';

@NgModule({
  imports: [HekimmasterSharedModule, RouterModule.forChild(shortPatientNoteRoute)],
  declarations: [
    ShortPatientNoteComponent,
    ShortPatientNoteDetailComponent,
    ShortPatientNoteUpdateComponent,
    ShortPatientNoteDeleteDialogComponent,
  ],
  entryComponents: [ShortPatientNoteDeleteDialogComponent],
})
export class HekimmasterShortPatientNoteModule {}
