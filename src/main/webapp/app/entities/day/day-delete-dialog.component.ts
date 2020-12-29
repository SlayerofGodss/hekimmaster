import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IDay } from 'app/shared/model/day.model';
import { DayService } from './day.service';

@Component({
  templateUrl: './day-delete-dialog.component.html',
})
export class DayDeleteDialogComponent {
  day?: IDay;

  constructor(protected dayService: DayService, public activeModal: NgbActiveModal, protected eventManager: JhiEventManager) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.dayService.delete(id).subscribe(() => {
      this.eventManager.broadcast('dayListModification');
      this.activeModal.close();
    });
  }
}
