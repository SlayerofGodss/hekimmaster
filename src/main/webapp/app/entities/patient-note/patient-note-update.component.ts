import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';

import { IPatientNote, PatientNote } from 'app/shared/model/patient-note.model';
import { PatientNoteService } from './patient-note.service';
import { IPatient } from 'app/shared/model/patient.model';
import { PatientService } from 'app/entities/patient/patient.service';

@Component({
  selector: 'jhi-patient-note-update',
  templateUrl: './patient-note-update.component.html',
})
export class PatientNoteUpdateComponent implements OnInit {
  isSaving = false;
  patients: IPatient[] = [];

  editForm = this.fb.group({
    id: [],
    patientNote: [],
    patientId: [],
  });

  constructor(
    protected patientNoteService: PatientNoteService,
    protected patientService: PatientService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ patientNote }) => {
      this.updateForm(patientNote);

      this.patientService.query().subscribe((res: HttpResponse<IPatient[]>) => (this.patients = res.body || []));
    });
  }

  updateForm(patientNote: IPatientNote): void {
    this.editForm.patchValue({
      id: patientNote.id,
      patientNote: patientNote.patientNote,
      patientId: patientNote.patientId,
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const patientNote = this.createFromForm();
    if (patientNote.id !== undefined) {
      this.subscribeToSaveResponse(this.patientNoteService.update(patientNote));
    } else {
      this.subscribeToSaveResponse(this.patientNoteService.create(patientNote));
    }
  }

  private createFromForm(): IPatientNote {
    return {
      ...new PatientNote(),
      id: this.editForm.get(['id'])!.value,
      patientNote: this.editForm.get(['patientNote'])!.value,
      patientId: this.editForm.get(['patientId'])!.value,
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IPatientNote>>): void {
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

  trackById(index: number, item: IPatient): any {
    return item.id;
  }
}
