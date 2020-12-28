import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { of } from 'rxjs';

import { HekimmasterTestModule } from '../../../test.module';
import { RandevuUpdateComponent } from 'app/entities/randevu/randevu-update.component';
import { RandevuService } from 'app/entities/randevu/randevu.service';
import { Randevu } from 'app/shared/model/randevu.model';

describe('Component Tests', () => {
  describe('Randevu Management Update Component', () => {
    let comp: RandevuUpdateComponent;
    let fixture: ComponentFixture<RandevuUpdateComponent>;
    let service: RandevuService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HekimmasterTestModule],
        declarations: [RandevuUpdateComponent],
        providers: [FormBuilder],
      })
        .overrideTemplate(RandevuUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(RandevuUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(RandevuService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new Randevu(123);
        spyOn(service, 'update').and.returnValue(of(new HttpResponse({ body: entity })));
        comp.updateForm(entity);
        // WHEN
        comp.save();
        tick(); // simulate async

        // THEN
        expect(service.update).toHaveBeenCalledWith(entity);
        expect(comp.isSaving).toEqual(false);
      }));

      it('Should call create service on save for new entity', fakeAsync(() => {
        // GIVEN
        const entity = new Randevu();
        spyOn(service, 'create').and.returnValue(of(new HttpResponse({ body: entity })));
        comp.updateForm(entity);
        // WHEN
        comp.save();
        tick(); // simulate async

        // THEN
        expect(service.create).toHaveBeenCalledWith(entity);
        expect(comp.isSaving).toEqual(false);
      }));
    });
  });
});
