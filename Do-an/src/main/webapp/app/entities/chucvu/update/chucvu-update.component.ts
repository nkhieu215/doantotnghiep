import { Component, inject, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize } from 'rxjs/operators';

import SharedModule from 'app/shared/shared.module';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';

import { IChucvu } from '../chucvu.model';
import { ChucvuService } from '../service/chucvu.service';
import { ChucvuFormService, ChucvuFormGroup } from './chucvu-form.service';

@Component({
  standalone: true,
  selector: 'jhi-chucvu-update',
  templateUrl: './chucvu-update.component.html',
  imports: [SharedModule, FormsModule, ReactiveFormsModule],
})
export class ChucvuUpdateComponent implements OnInit {
  isSaving = false;
  chucvu: IChucvu | null = null;

  protected chucvuService = inject(ChucvuService);
  protected chucvuFormService = inject(ChucvuFormService);
  protected activatedRoute = inject(ActivatedRoute);

  // eslint-disable-next-line @typescript-eslint/member-ordering
  editForm: ChucvuFormGroup = this.chucvuFormService.createChucvuFormGroup();

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ chucvu }) => {
      this.chucvu = chucvu;
      if (chucvu) {
        this.updateForm(chucvu);
      }
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const chucvu = this.chucvuFormService.getChucvu(this.editForm);
    if (chucvu.id !== null) {
      this.subscribeToSaveResponse(this.chucvuService.update(chucvu));
    } else {
      this.subscribeToSaveResponse(this.chucvuService.create(chucvu));
    }
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IChucvu>>): void {
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

  protected updateForm(chucvu: IChucvu): void {
    this.chucvu = chucvu;
    this.chucvuFormService.resetForm(this.editForm, chucvu);
  }
}
