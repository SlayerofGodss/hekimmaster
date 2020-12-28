import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { HekimmasterSharedModule } from 'app/shared/shared.module';
import { RandevuComponent } from './randevu.component';
import { RandevuDetailComponent } from './randevu-detail.component';
import { RandevuUpdateComponent } from './randevu-update.component';
import { RandevuDeleteDialogComponent } from './randevu-delete-dialog.component';
import { randevuRoute } from './randevu.route';

@NgModule({
  imports: [HekimmasterSharedModule, RouterModule.forChild(randevuRoute)],
  declarations: [RandevuComponent, RandevuDetailComponent, RandevuUpdateComponent, RandevuDeleteDialogComponent],
  entryComponents: [RandevuDeleteDialogComponent],
})
export class HekimmasterRandevuModule {}
