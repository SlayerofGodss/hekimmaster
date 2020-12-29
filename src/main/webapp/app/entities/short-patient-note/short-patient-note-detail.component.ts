import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IShortPatientNote } from 'app/shared/model/short-patient-note.model';

@Component({
  selector: 'jhi-short-patient-note-detail',
  templateUrl: './short-patient-note-detail.component.html',
})
export class ShortPatientNoteDetailComponent implements OnInit {
  shortPatientNote: IShortPatientNote | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ shortPatientNote }) => (this.shortPatientNote = shortPatientNote));
  }

  previousState(): void {
    window.history.back();
  }
}
