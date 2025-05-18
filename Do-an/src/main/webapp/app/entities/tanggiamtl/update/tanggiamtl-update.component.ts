import { Component, inject, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize, map } from 'rxjs/operators';

import SharedModule from 'app/shared/shared.module';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';

import { INhanvien } from 'app/entities/nhanvien/nhanvien.model';
import { NhanvienService } from 'app/entities/nhanvien/service/nhanvien.service';
import { ITanggiamtl } from '../tanggiamtl.model';
import { TanggiamtlService } from '../service/tanggiamtl.service';
import { TanggiamtlFormService, TanggiamtlFormGroup } from './tanggiamtl-form.service';

@Component({
  standalone: true,
  selector: 'jhi-tanggiamtl-update',
  templateUrl: './tanggiamtl-update.component.html',
  imports: [SharedModule, FormsModule, ReactiveFormsModule],
})
export class TanggiamtlUpdateComponent implements OnInit {
  isSaving = false;
  tanggiamtl: ITanggiamtl | null = null;

  nhanviensSharedCollection: INhanvien[] = [];

  protected tanggiamtlService = inject(TanggiamtlService);
  protected tanggiamtlFormService = inject(TanggiamtlFormService);
  protected nhanvienService = inject(NhanvienService);
  protected activatedRoute = inject(ActivatedRoute);

  // eslint-disable-next-line @typescript-eslint/member-ordering
  editForm: TanggiamtlFormGroup = this.tanggiamtlFormService.createTanggiamtlFormGroup();

  compareNhanvien = (o1: INhanvien | null, o2: INhanvien | null): boolean => this.nhanvienService.compareNhanvien(o1, o2);

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ tanggiamtl }) => {
      this.tanggiamtl = tanggiamtl;
      if (tanggiamtl) {
        this.updateForm(tanggiamtl);
      }

      this.loadRelationshipsOptions();
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const tanggiamtl = this.tanggiamtlFormService.getTanggiamtl(this.editForm);
    if (tanggiamtl.id !== null) {
      this.subscribeToSaveResponse(this.tanggiamtlService.update(tanggiamtl));
    } else {
      this.subscribeToSaveResponse(this.tanggiamtlService.create(tanggiamtl));
    }
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<ITanggiamtl>>): void {
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

  protected updateForm(tanggiamtl: ITanggiamtl): void {
    this.tanggiamtl = tanggiamtl;
    this.tanggiamtlFormService.resetForm(this.editForm, tanggiamtl);

    this.nhanviensSharedCollection = this.nhanvienService.addNhanvienToCollectionIfMissing<INhanvien>(
      this.nhanviensSharedCollection,
      tanggiamtl.nhanvien,
    );
  }

  protected loadRelationshipsOptions(): void {
    this.nhanvienService
      .query()
      .pipe(map((res: HttpResponse<INhanvien[]>) => res.body ?? []))
      .pipe(
        map((nhanviens: INhanvien[]) =>
          this.nhanvienService.addNhanvienToCollectionIfMissing<INhanvien>(nhanviens, this.tanggiamtl?.nhanvien),
        ),
      )
      .subscribe((nhanviens: INhanvien[]) => (this.nhanviensSharedCollection = nhanviens));
  }
}
