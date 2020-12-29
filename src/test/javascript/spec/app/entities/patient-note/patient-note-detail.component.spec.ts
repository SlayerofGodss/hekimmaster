import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { HekimmasterTestModule } from '../../../test.module';
import { PatientNoteDetailComponent } from 'app/entities/patient-note/patient-note-detail.component';
import { PatientNote } from 'app/shared/model/patient-note.model';

describe('Component Tests', () => {
  describe('PatientNote Management Detail Component', () => {
    let comp: PatientNoteDetailComponent;
    let fixture: ComponentFixture<PatientNoteDetailComponent>;
    const route = ({ data: of({ patientNote: new PatientNote(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HekimmasterTestModule],
        declarations: [PatientNoteDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }],
      })
        .overrideTemplate(PatientNoteDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(PatientNoteDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should load patientNote on init', () => {
        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.patientNote).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
