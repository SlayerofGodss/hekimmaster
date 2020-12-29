import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { HekimmasterSharedModule } from 'app/shared/shared.module';
import { DayComponent } from './day.component';
import { DayDetailComponent } from './day-detail.component';
import { DayUpdateComponent } from './day-update.component';
import { DayDeleteDialogComponent } from './day-delete-dialog.component';
import { dayRoute } from './day.route';

@NgModule({
  imports: [HekimmasterSharedModule, RouterModule.forChild(dayRoute)],
  declarations: [DayComponent, DayDetailComponent, DayUpdateComponent, DayDeleteDialogComponent],
  entryComponents: [DayDeleteDialogComponent],
})
export class HekimmasterDayModule {}
