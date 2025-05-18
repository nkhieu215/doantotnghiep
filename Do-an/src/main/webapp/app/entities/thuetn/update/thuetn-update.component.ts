import { Component, inject, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize } from 'rxjs/operators';

import SharedModule from 'app/shared/shared.module';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';

import { IThuetn } from '../thuetn.model';
import { ThuetnService } from '../service/thuetn.service';
import { ThuetnFormService, ThuetnFormGroup } from './thuetn-form.service';

@Component({
  standalone: true,
  selector: 'jhi-thuetn-update',
  templateUrl: './thuetn-update.component.html',
  imports: [SharedModule, FormsModule, ReactiveFormsModule],
})
export class ThuetnUpdateComponent implements OnInit {
  isSaving = false;
  thuetn: IThuetn | null = null;

  protected thuetnService = inject(ThuetnService);
  protected thuetnFormService = inject(ThuetnFormService);
  protected activatedRoute = inject(ActivatedRoute);

  // eslint-disable-next-line @typescript-eslint/member-ordering
  editForm: ThuetnFormGroup = this.thuetnFormService.createThuetnFormGroup();

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ thuetn }) => {
      this.thuetn = thuetn;
      if (thuetn) {
        this.updateForm(thuetn);
      }
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const thuetn = this.thuetnFormService.getThuetn(this.editForm);
    if (thuetn.id !== null) {
      this.subscribeToSaveResponse(this.thuetnService.update(thuetn));
    } else {
      this.subscribeToSaveResponse(this.thuetnService.create(thuetn));
    }
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IThuetn>>): void {
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

  protected updateForm(thuetn: IThuetn): void {
    this.thuetn = thuetn;
    this.thuetnFormService.resetForm(this.editForm, thuetn);
  }
}
