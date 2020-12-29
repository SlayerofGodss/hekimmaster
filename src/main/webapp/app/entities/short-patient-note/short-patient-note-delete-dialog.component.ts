import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IShortPatientNote } from 'app/shared/model/short-patient-note.model';
import { ShortPatientNoteService } from './short-patient-note.service';

@Component({
  templateUrl: './short-patient-note-delete-dialog.component.html',
})
export class ShortPatientNoteDeleteDialogComponent {
  shortPatientNote?: IShortPatientNote;

  constructor(
    protected shortPatientNoteService: ShortPatientNoteService,
    public activeModal: NgbActiveModal,
    protected eventManager: JhiEventManager
  ) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.shortPatientNoteService.delete(id).subscribe(() => {
      this.eventManager.broadcast('shortPatientNoteListModification');
      this.activeModal.close();
    });
  }
}
