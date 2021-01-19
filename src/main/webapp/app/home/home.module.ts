import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { HekimmasterSharedModule } from 'app/shared/shared.module';
import { HOME_ROUTE } from './home.route';
import { HomeComponent } from './home.component';
import { FullCalendarModule } from 'primeng/fullcalendar';
import { CalendarModule } from 'primeng/calendar';

@NgModule({
  imports: [HekimmasterSharedModule, CalendarModule, RouterModule.forChild([HOME_ROUTE])],
  declarations: [HomeComponent],
})
export class HekimmasterHomeModule {}
