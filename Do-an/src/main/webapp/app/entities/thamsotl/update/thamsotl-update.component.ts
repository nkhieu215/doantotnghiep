import { Component, inject, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize, map } from 'rxjs/operators';

import SharedModule from 'app/shared/shared.module';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';

import { INhanvien } from 'app/entities/nhanvien/nhanvien.model';
import { NhanvienService } from 'app/entities/nhanvien/service/nhanvien.service';
import { IThamsotl } from '../thamsotl.model';
import { ThamsotlService } from '../service/thamsotl.service';
import { ThamsotlFormService, ThamsotlFormGroup } from './thamsotl-form.service';

@Component({
  standalone: true,
  selector: 'jhi-thamsotl-update',
  templateUrl: './thamsotl-update.component.html',
  imports: [SharedModule, FormsModule, ReactiveFormsModule],
})
export class ThamsotlUpdateComponent implements OnInit {
  isSaving = false;
  thamsotl: IThamsotl | null = null;

  nhanviensCollection: INhanvien[] = [];

  protected thamsotlService = inject(ThamsotlService);
  protected thamsotlFormService = inject(ThamsotlFormService);
  protected nhanvienService = inject(NhanvienService);
  protected activatedRoute = inject(ActivatedRoute);

  // eslint-disable-next-line @typescript-eslint/member-ordering
  editForm: ThamsotlFormGroup = this.thamsotlFormService.createThamsotlFormGroup();

  compareNhanvien = (o1: INhanvien | null, o2: INhanvien | null): boolean => this.nhanvienService.compareNhanvien(o1, o2);

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ thamsotl }) => {
      this.thamsotl = thamsotl;
      if (thamsotl) {
        this.updateForm(thamsotl);
      }

      this.loadRelationshipsOptions();
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const thamsotl = this.thamsotlFormService.getThamsotl(this.editForm);
    if (thamsotl.id !== null) {
      this.subscribeToSaveResponse(this.thamsotlService.update(thamsotl));
    } else {
      this.subscribeToSaveResponse(this.thamsotlService.create(thamsotl));
    }
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IThamsotl>>): void {
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

  protected updateForm(thamsotl: IThamsotl): void {
    this.thamsotl = thamsotl;
    this.thamsotlFormService.resetForm(this.editForm, thamsotl);

    this.nhanviensCollection = this.nhanvienService.addNhanvienToCollectionIfMissing<INhanvien>(
      this.nhanviensCollection,
      thamsotl.nhanvien,
    );
  }

  protected loadRelationshipsOptions(): void {
    this.nhanvienService
      .query({ filter: 'thamsotl-is-null' })
      .pipe(map((res: HttpResponse<INhanvien[]>) => res.body ?? []))
      .pipe(
        map((nhanviens: INhanvien[]) =>
          this.nhanvienService.addNhanvienToCollectionIfMissing<INhanvien>(nhanviens, this.thamsotl?.nhanvien),
        ),
      )
      .subscribe((nhanviens: INhanvien[]) => (this.nhanviensCollection = nhanviens));
  }
}
