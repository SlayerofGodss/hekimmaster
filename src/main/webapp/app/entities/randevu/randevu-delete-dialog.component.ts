import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IRandevu } from 'app/shared/model/randevu.model';
import { RandevuService } from './randevu.service';

@Component({
  templateUrl: './randevu-delete-dialog.component.html',
})
export class RandevuDeleteDialogComponent {
  randevu?: IRandevu;

  constructor(protected randevuService: RandevuService, public activeModal: NgbActiveModal, protected eventManager: JhiEventManager) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.randevuService.delete(id).subscribe(() => {
      this.eventManager.broadcast('randevuListModification');
      this.activeModal.close();
    });
  }
}
