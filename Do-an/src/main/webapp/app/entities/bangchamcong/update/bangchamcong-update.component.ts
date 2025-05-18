import { Component, inject, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize, map } from 'rxjs/operators';

import SharedModule from 'app/shared/shared.module';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';

import { INhanvien } from 'app/entities/nhanvien/nhanvien.model';
import { NhanvienService } from 'app/entities/nhanvien/service/nhanvien.service';
import { IBangchamcong } from '../bangchamcong.model';
import { BangchamcongService } from '../service/bangchamcong.service';
import { BangchamcongFormService, BangchamcongFormGroup } from './bangchamcong-form.service';

@Component({
  standalone: true,
  selector: 'jhi-bangchamcong-update',
  templateUrl: './bangchamcong-update.component.html',
  imports: [SharedModule, FormsModule, ReactiveFormsModule],
})
export class BangchamcongUpdateComponent implements OnInit {
  isSaving = false;
  bangchamcong: IBangchamcong | null = null;

  nhanviensSharedCollection: INhanvien[] = [];

  protected bangchamcongService = inject(BangchamcongService);
  protected bangchamcongFormService = inject(BangchamcongFormService);
  protected nhanvienService = inject(NhanvienService);
  protected activatedRoute = inject(ActivatedRoute);

  // eslint-disable-next-line @typescript-eslint/member-ordering
  editForm: BangchamcongFormGroup = this.bangchamcongFormService.createBangchamcongFormGroup();

  compareNhanvien = (o1: INhanvien | null, o2: INhanvien | null): boolean => this.nhanvienService.compareNhanvien(o1, o2);

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ bangchamcong }) => {
      this.bangchamcong = bangchamcong;
      if (bangchamcong) {
        this.updateForm(bangchamcong);
      }

      this.loadRelationshipsOptions();
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const bangchamcong = this.bangchamcongFormService.getBangchamcong(this.editForm);
    if (bangchamcong.id !== null) {
      this.subscribeToSaveResponse(this.bangchamcongService.update(bangchamcong));
    } else {
      this.subscribeToSaveResponse(this.bangchamcongService.create(bangchamcong));
    }
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IBangchamcong>>): void {
    result.pipe(finalize(() => this.onSaveFinalize())).subscribe({
      next: () => this.onSaveSuccess(),
      error: () => this.onSaveError(),
    });
  }

  protected onSaveSuccess(): void {
    this.previousState();
  }

  protected onSaveError(): void {
    // Api for inheritance.
  }

  protected onSaveFinalize(): void {
    this.isSaving = false;
  }

  protected updateForm(bangchamcong: IBangchamcong): void {
    this.bangchamcong = bangchamcong;
    this.bangchamcongFormService.resetForm(this.editForm, bangchamcong);

    this.nhanviensSharedCollection = this.nhanvienService.addNhanvienToCollectionIfMissing<INhanvien>(
      this.nhanviensSharedCollection,
      bangchamcong.nhanvien,
    );
  }

  protected loadRelationshipsOptions(): void {
    this.nhanvienService
      .query()
      .pipe(map((res: HttpResponse<INhanvien[]>) => res.body ?? []))
      .pipe(
        map((nhanviens: INhanvien[]) =>
          this.nhanvienService.addNhanvienToCollectionIfMissing<INhanvien>(nhanviens, this.bangchamcong?.nhanvien),
        ),
      )
      .subscribe((nhanviens: INhanvien[]) => (this.nhanviensSharedCollection = nhanviens));
  }
}
