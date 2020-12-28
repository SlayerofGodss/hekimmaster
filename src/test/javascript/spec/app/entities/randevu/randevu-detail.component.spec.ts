import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { HekimmasterTestModule } from '../../../test.module';
import { RandevuDetailComponent } from 'app/entities/randevu/randevu-detail.component';
import { Randevu } from 'app/shared/model/randevu.model';

describe('Component Tests', () => {
  describe('Randevu Management Detail Component', () => {
    let comp: RandevuDetailComponent;
    let fixture: ComponentFixture<RandevuDetailComponent>;
    const route = ({ data: of({ randevu: new Randevu(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HekimmasterTestModule],
        declarations: [RandevuDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }],
      })
        .overrideTemplate(RandevuDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(RandevuDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should load randevu on init', () => {
        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.randevu).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
