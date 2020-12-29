import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';

import { IShortPatientNote, ShortPatientNote } from 'app/shared/model/short-patient-note.model';
import { ShortPatientNoteService } from './short-patient-note.service';

@Component({
  selector: 'jhi-short-patient-note-update',
  templateUrl: './short-patient-note-update.component.html',
})
export class ShortPatientNoteUpdateComponent implements OnInit {
  isSaving = false;

  editForm = this.fb.group({
    id: [],
    shortPatientNote: [null, [Validators.required]],
  });

  constructor(
    protected shortPatientNoteService: ShortPatientNoteService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ shortPatientNote }) => {
      this.updateForm(shortPatientNote);
    });
  }

  updateForm(shortPatientNote: IShortPatientNote): void {
    this.editForm.patchValue({
      id: shortPatientNote.id,
      shortPatientNote: shortPatientNote.shortPatientNote,
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const shortPatientNote = this.createFromForm();
    if (shortPatientNote.id !== undefined) {
      this.subscribeToSaveResponse(this.shortPatientNoteService.update(shortPatientNote));
    } else {
      this.subscribeToSaveResponse(this.shortPatientNoteService.create(shortPatientNote));
    }
  }

  private createFromForm(): IShortPatientNote {
    return {
      ...new ShortPatientNote(),
      id: this.editForm.get(['id'])!.value,
      shortPatientNote: this.editForm.get(['shortPatientNote'])!.value,
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IShortPatientNote>>): void {
    result.subscribe(
      () => this.onSaveSuccess(),
      () => this.onSaveError()
    );
  }

  protected onSaveSuccess(): void {
    this.isSaving = false;
    this.previousState();
  }

  protected onSaveError(): void {
    this.isSaving = false;
  }
}
