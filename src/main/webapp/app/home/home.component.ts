import { Component, OnInit, OnDestroy } from '@angular/core';
import { from, Subscription } from 'rxjs';
import dayGridPlugin from '@fullcalendar/daygrid';
import interactionPlugin from '@fullcalendar/interaction';
import timeGridPlugin from '@fullcalendar/timegrid';
import momentPlugin from '@fullcalendar/moment';





import { LoginModalService } from 'app/core/login/login-modal.service';
import { AccountService } from 'app/core/auth/account.service';
import { EventService } from 'app/home/event.service';
import { Account } from 'app/core/user/account.model';
import { toMoment, toDuration } from '@fullcalendar/moment';
import * as moment from 'moment';

export class CalendarEvent {
  title: any = '';
  start: any;
  end: any;


  constructor() {}
}

@Component({
  selector: 'jhi-home',
  templateUrl: './home.component.html',
  styleUrls: ['home.scss'],
})
export class HomeComponent implements OnInit, OnDestroy {
  account: Account | null = null;
  authSubscription?: Subscription;

  events: any[];

  options: any;

  header: any;

   constructor(private eventService: EventService,private accountService: AccountService, private loginModalService: LoginModalService) {
    this.events = [];
        this.options = {};
        this.header = {};

        
  }

  

  ngOnInit(): void {
    this.authSubscription = this.accountService.getAuthenticationState().subscribe(account => (this.account = account));
    this.eventService.getEvents().subscribe(events => {this.events = events;});


    this.options = {
          plugins: [momentPlugin, dayGridPlugin, timeGridPlugin, interactionPlugin],
          titleFormat: 'MMMM D, YYYY',
          defaultDate: '2020-10-06',
          locale: 'tr',
          selectable: true,
          themeSystem: 'litera',
          droppable: true,
          buttonText: {
            day: 'Gün',
            month: 'Ay',
            week: 'Hafta',
            today: 'Bu Gün',
          },
          header: {
            left: 'prev,next,today',
            center: 'title',
            right: 'dayGridMonth,timeGridWeek,timeGridDay',
          }
        };

  }

  isAuthenticated(): boolean {
    return this.accountService.isAuthenticated();
  }

  login(): void {
    this.loginModalService.open();
  }

  ngOnDestroy(): void {
    if (this.authSubscription) {
      this.authSubscription.unsubscribe();
    }
  }
}
