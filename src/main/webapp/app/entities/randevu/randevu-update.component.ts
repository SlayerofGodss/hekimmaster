import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';

import { IRandevu, Randevu } from 'app/shared/model/randevu.model';
import { RandevuService } from './randevu.service';
import { IPatient } from 'app/shared/model/patient.model';
import { PatientService } from 'app/entities/patient/patient.service';

@Component({
  selector: 'jhi-randevu-update',
  templateUrl: './randevu-update.component.html',
})
export class RandevuUpdateComponent implements OnInit {
  isSaving = false;
  patients: IPatient[] = [];

  editForm = this.fb.group({
    id: [],
    randevu: [null, [Validators.required, Validators.maxLength(255)]],
    patientId: [null, Validators.required],
  });

  constructor(
    protected randevuService: RandevuService,
    protected patientService: PatientService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ randevu }) => {
      this.updateForm(randevu);

      this.patientService.query().subscribe((res: HttpResponse<IPatient[]>) => (this.patients = res.body || []));
    });
  }

  updateForm(randevu: IRandevu): void {
    this.editForm.patchValue({
      id: randevu.id,
      randevu: randevu.randevu,
      patientId: randevu.patientId,
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const randevu = this.createFromForm();
    if (randevu.id !== undefined) {
      this.subscribeToSaveResponse(this.randevuService.update(randevu));
    } else {
      this.subscribeToSaveResponse(this.randevuService.create(randevu));
    }
  }

  private createFromForm(): IRandevu {
    return {
      ...new Randevu(),
      id: this.editForm.get(['id'])!.value,
      randevu: this.editForm.get(['randevu'])!.value,
      patientId: this.editForm.get(['patientId'])!.value,
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IRandevu>>): void {
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
