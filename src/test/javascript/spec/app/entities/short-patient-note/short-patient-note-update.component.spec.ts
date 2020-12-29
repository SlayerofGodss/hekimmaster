import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { of } from 'rxjs';

import { HekimmasterTestModule } from '../../../test.module';
import { ShortPatientNoteUpdateComponent } from 'app/entities/short-patient-note/short-patient-note-update.component';
import { ShortPatientNoteService } from 'app/entities/short-patient-note/short-patient-note.service';
import { ShortPatientNote } from 'app/shared/model/short-patient-note.model';

describe('Component Tests', () => {
  describe('ShortPatientNote Management Update Component', () => {
    let comp: ShortPatientNoteUpdateComponent;
    let fixture: ComponentFixture<ShortPatientNoteUpdateComponent>;
    let service: ShortPatientNoteService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HekimmasterTestModule],
        declarations: [ShortPatientNoteUpdateComponent],
        providers: [FormBuilder],
      })
        .overrideTemplate(ShortPatientNoteUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(ShortPatientNoteUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(ShortPatientNoteService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new ShortPatientNote(123);
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
        const entity = new ShortPatientNote();
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
