import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { HekimmasterTestModule } from '../../../test.module';
import { DayDetailComponent } from 'app/entities/day/day-detail.component';
import { Day } from 'app/shared/model/day.model';

describe('Component Tests', () => {
  describe('Day Management Detail Component', () => {
    let comp: DayDetailComponent;
    let fixture: ComponentFixture<DayDetailComponent>;
    const route = ({ data: of({ day: new Day(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HekimmasterTestModule],
        declarations: [DayDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }],
      })
        .overrideTemplate(DayDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(DayDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should load day on init', () => {
        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.day).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
