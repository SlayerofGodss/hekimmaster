import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';

import './vendor';
import { HekimmasterSharedModule } from 'app/shared/shared.module';
import { HekimmasterCoreModule } from 'app/core/core.module';
import { HekimmasterAppRoutingModule } from './app-routing.module';
import { HekimmasterHomeModule } from './home/home.module';
import { HekimmasterEntityModule } from './entities/entity.module';
// jhipster-needle-angular-add-module-import JHipster will add new module here
import { MainComponent } from './layouts/main/main.component';
import { NavbarComponent } from './layouts/navbar/navbar.component';
import { FooterComponent } from './layouts/footer/footer.component';
import { PageRibbonComponent } from './layouts/profiles/page-ribbon.component';
import { ActiveMenuDirective } from './layouts/navbar/active-menu.directive';
import { ErrorComponent } from './layouts/error/error.component';
import { FullCalendarModule } from 'primeng/fullcalendar';
import { CalendarModule } from 'primeng/calendar';

@NgModule({
  imports: [
    BrowserModule,
    HekimmasterSharedModule,
    HekimmasterCoreModule,
    HekimmasterHomeModule,
    // jhipster-needle-angular-add-module JHipster will add new module here
    HekimmasterEntityModule,
    HekimmasterAppRoutingModule,
    FullCalendarModule,
    CalendarModule
  ],
  declarations: [MainComponent, NavbarComponent, ErrorComponent, PageRibbonComponent, ActiveMenuDirective, FooterComponent],
  bootstrap: [MainComponent],
})
export class HekimmasterAppModule {}
