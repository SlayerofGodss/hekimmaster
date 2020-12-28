import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IRandevu } from 'app/shared/model/randevu.model';

@Component({
  selector: 'jhi-randevu-detail',
  templateUrl: './randevu-detail.component.html',
})
export class RandevuDetailComponent implements OnInit {
  randevu: IRandevu | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ randevu }) => (this.randevu = randevu));
  }

  previousState(): void {
    window.history.back();
  }
}
