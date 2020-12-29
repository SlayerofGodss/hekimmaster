import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { HekimmasterTestModule } from '../../../test.module';
import { ShortPatientNoteDetailComponent } from 'app/entities/short-patient-note/short-patient-note-detail.component';
import { ShortPatientNote } from 'app/shared/model/short-patient-note.model';

describe('Component Tests', () => {
  describe('ShortPatientNote Management Detail Component', () => {
    let comp: ShortPatientNoteDetailComponent;
    let fixture: ComponentFixture<ShortPatientNoteDetailComponent>;
    const route = ({ data: of({ shortPatientNote: new ShortPatientNote(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HekimmasterTestModule],
        declarations: [ShortPatientNoteDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }],
      })
        .overrideTemplate(ShortPatientNoteDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(ShortPatientNoteDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should load shortPatientNote on init', () => {
        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.shortPatientNote).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
