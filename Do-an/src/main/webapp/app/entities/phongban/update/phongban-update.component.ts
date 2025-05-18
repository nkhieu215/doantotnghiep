import { Component, inject, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize } from 'rxjs/operators';

import SharedModule from 'app/shared/shared.module';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';

import { IPhongban } from '../phongban.model';
import { PhongbanService } from '../service/phongban.service';
import { PhongbanFormService, PhongbanFormGroup } from './phongban-form.service';

@Component({
  standalone: true,
  selector: 'jhi-phongban-update',
  templateUrl: './phongban-update.component.html',
  imports: [SharedModule, FormsModule, ReactiveFormsModule],
})
export class PhongbanUpdateComponent implements OnInit {
  isSaving = false;
  phongban: IPhongban | null = null;

  protected phongbanService = inject(PhongbanService);
  protected phongbanFormService = inject(PhongbanFormService);
  protected activatedRoute = inject(ActivatedRoute);

  // eslint-disable-next-line @typescript-eslint/member-ordering
  editForm: PhongbanFormGroup = this.phongbanFormService.createPhongbanFormGroup();

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ phongban }) => {
      this.phongban = phongban;
      if (phongban) {
        this.updateForm(phongban);
      }
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const phongban = this.phongbanFormService.getPhongban(this.editForm);
    if (phongban.id !== null) {
      this.subscribeToSaveResponse(this.phongbanService.update(phongban));
    } else {
      this.subscribeToSaveResponse(this.phongbanService.create(phongban));
    }
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IPhongban>>): void {
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

  protected updateForm(phongban: IPhongban): void {
    this.phongban = phongban;
    this.phongbanFormService.resetForm(this.editForm, phongban);
  }
}
