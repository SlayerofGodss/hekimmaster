import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { of } from 'rxjs';

import { HekimmasterTestModule } from '../../../test.module';
import { PatientNoteUpdateComponent } from 'app/entities/patient-note/patient-note-update.component';
import { PatientNoteService } from 'app/entities/patient-note/patient-note.service';
import { PatientNote } from 'app/shared/model/patient-note.model';

describe('Component Tests', () => {
  describe('PatientNote Management Update Component', () => {
    let comp: PatientNoteUpdateComponent;
    let fixture: ComponentFixture<PatientNoteUpdateComponent>;
    let service: PatientNoteService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HekimmasterTestModule],
        declarations: [PatientNoteUpdateComponent],
        providers: [FormBuilder],
      })
        .overrideTemplate(PatientNoteUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(PatientNoteUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(PatientNoteService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new PatientNote(123);
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
        const entity = new PatientNote();
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
