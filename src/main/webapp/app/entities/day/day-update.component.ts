import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import * as moment from 'moment';
import { DATE_TIME_FORMAT } from 'app/shared/constants/input.constants';

import { IDay, Day } from 'app/shared/model/day.model';
import { DayService } from './day.service';

@Component({
  selector: 'jhi-day-update',
  templateUrl: './day-update.component.html',
})
export class DayUpdateComponent implements OnInit {
  isSaving = false;

  editForm = this.fb.group({
    id: [],
    randevuDate: [null, [Validators.required]],
  });

  constructor(protected dayService: DayService, protected activatedRoute: ActivatedRoute, private fb: FormBuilder) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ day }) => {
      if (!day.id) {
        const today = moment().startOf('day');
        day.randevuDate = today;
      }

      this.updateForm(day);
    });
  }

  updateForm(day: IDay): void {
    this.editForm.patchValue({
      id: day.id,
      randevuDate: day.randevuDate ? day.randevuDate.format(DATE_TIME_FORMAT) : null,
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const day = this.createFromForm();
    if (day.id !== undefined) {
      this.subscribeToSaveResponse(this.dayService.update(day));
    } else {
      this.subscribeToSaveResponse(this.dayService.create(day));
    }
  }

  private createFromForm(): IDay {
    return {
      ...new Day(),
      id: this.editForm.get(['id'])!.value,
      randevuDate: this.editForm.get(['randevuDate'])!.value
        ? moment(this.editForm.get(['randevuDate'])!.value, DATE_TIME_FORMAT)
        : undefined,
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IDay>>): void {
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
