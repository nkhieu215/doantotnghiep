import { Component, inject, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize, map } from 'rxjs/operators';

import SharedModule from 'app/shared/shared.module';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';

import { IChucvu } from 'app/entities/chucvu/chucvu.model';
import { ChucvuService } from 'app/entities/chucvu/service/chucvu.service';
import { IPhongban } from 'app/entities/phongban/phongban.model';
import { PhongbanService } from 'app/entities/phongban/service/phongban.service';
import { NhanvienService } from '../service/nhanvien.service';
import { INhanvien } from '../nhanvien.model';
import { NhanvienFormService, NhanvienFormGroup } from './nhanvien-form.service';

@Component({
  standalone: true,
  selector: 'jhi-nhanvien-update',
  templateUrl: './nhanvien-update.component.html',
  imports: [SharedModule, FormsModule, ReactiveFormsModule],
})
export class NhanvienUpdateComponent implements OnInit {
  isSaving = false;
  nhanvien: INhanvien | null = null;

  chucvusSharedCollection: IChucvu[] = [];
  phongbansSharedCollection: IPhongban[] = [];

  protected nhanvienService = inject(NhanvienService);
  protected nhanvienFormService = inject(NhanvienFormService);
  protected chucvuService = inject(ChucvuService);
  protected phongbanService = inject(PhongbanService);
  protected activatedRoute = inject(ActivatedRoute);

  // eslint-disable-next-line @typescript-eslint/member-ordering
  editForm: NhanvienFormGroup = this.nhanvienFormService.createNhanvienFormGroup();

  compareChucvu = (o1: IChucvu | null, o2: IChucvu | null): boolean => this.chucvuService.compareChucvu(o1, o2);

  comparePhongban = (o1: IPhongban | null, o2: IPhongban | null): boolean => this.phongbanService.comparePhongban(o1, o2);

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ nhanvien }) => {
      this.nhanvien = nhanvien;
      if (nhanvien) {
        this.updateForm(nhanvien);
      }

      this.loadRelationshipsOptions();
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const nhanvien = this.nhanvienFormService.getNhanvien(this.editForm);
    if (nhanvien.id !== null) {
      this.subscribeToSaveResponse(this.nhanvienService.update(nhanvien));
    } else {
      this.subscribeToSaveResponse(this.nhanvienService.create(nhanvien));
    }
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<INhanvien>>): void {
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

  protected updateForm(nhanvien: INhanvien): void {
    this.nhanvien = nhanvien;
    this.nhanvienFormService.resetForm(this.editForm, nhanvien);

    this.chucvusSharedCollection = this.chucvuService.addChucvuToCollectionIfMissing<IChucvu>(
      this.chucvusSharedCollection,
      nhanvien.chucvu,
    );
    this.phongbansSharedCollection = this.phongbanService.addPhongbanToCollectionIfMissing<IPhongban>(
      this.phongbansSharedCollection,
      nhanvien.phongban,
    );
  }

  protected loadRelationshipsOptions(): void {
    this.chucvuService
      .query()
      .pipe(map((res: HttpResponse<IChucvu[]>) => res.body ?? []))
      .pipe(map((chucvus: IChucvu[]) => this.chucvuService.addChucvuToCollectionIfMissing<IChucvu>(chucvus, this.nhanvien?.chucvu)))
      .subscribe((chucvus: IChucvu[]) => (this.chucvusSharedCollection = chucvus));

    this.phongbanService
      .query()
      .pipe(map((res: HttpResponse<IPhongban[]>) => res.body ?? []))
      .pipe(
        map((phongbans: IPhongban[]) =>
          this.phongbanService.addPhongbanToCollectionIfMissing<IPhongban>(phongbans, this.nhanvien?.phongban),
        ),
      )
      .subscribe((phongbans: IPhongban[]) => (this.phongbansSharedCollection = phongbans));
  }
}
