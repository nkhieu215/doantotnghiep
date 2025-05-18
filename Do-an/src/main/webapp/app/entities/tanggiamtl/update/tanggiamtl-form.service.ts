import { Injectable } from '@angular/core';
import { FormGroup, FormControl, Validators } from '@angular/forms';

import { ITanggiamtl, NewTanggiamtl } from '../tanggiamtl.model';

/**
 * A partial Type with required key is used as form input.
 */
type PartialWithRequiredKeyOf<T extends { id: unknown }> = Partial<Omit<T, 'id'>> & { id: T['id'] };

/**
 * Type for createFormGroup and resetForm argument.
 * It accepts ITanggiamtl for edit and NewTanggiamtlFormGroupInput for create.
 */
type TanggiamtlFormGroupInput = ITanggiamtl | PartialWithRequiredKeyOf<NewTanggiamtl>;

type TanggiamtlFormDefaults = Pick<NewTanggiamtl, 'id'>;

type TanggiamtlFormGroupContent = {
  id: FormControl<ITanggiamtl['id'] | NewTanggiamtl['id']>;
  ngaythang: FormControl<ITanggiamtl['ngaythang']>;
  tkn: FormControl<ITanggiamtl['tkn']>;
  tkc: FormControl<ITanggiamtl['tkc']>;
  sotien: FormControl<ITanggiamtl['sotien']>;
  diengiai: FormControl<ITanggiamtl['diengiai']>;
  nhanvien: FormControl<ITanggiamtl['nhanvien']>;
};

export type TanggiamtlFormGroup = FormGroup<TanggiamtlFormGroupContent>;

@Injectable({ providedIn: 'root' })
export class TanggiamtlFormService {
  createTanggiamtlFormGroup(tanggiamtl: TanggiamtlFormGroupInput = { id: null }): TanggiamtlFormGroup {
    const tanggiamtlRawValue = {
      ...this.getFormDefaults(),
      ...tanggiamtl,
    };
    return new FormGroup<TanggiamtlFormGroupContent>({
      id: new FormControl(
        { value: tanggiamtlRawValue.id, disabled: true },
        {
          nonNullable: true,
          validators: [Validators.required],
        },
      ),
      ngaythang: new FormControl(tanggiamtlRawValue.ngaythang),
      tkn: new FormControl(tanggiamtlRawValue.tkn),
      tkc: new FormControl(tanggiamtlRawValue.tkc),
      sotien: new FormControl(tanggiamtlRawValue.sotien),
      diengiai: new FormControl(tanggiamtlRawValue.diengiai),
      nhanvien: new FormControl(tanggiamtlRawValue.nhanvien),
    });
  }

  getTanggiamtl(form: TanggiamtlFormGroup): ITanggiamtl | NewTanggiamtl {
    return form.getRawValue() as ITanggiamtl | NewTanggiamtl;
  }

  resetForm(form: TanggiamtlFormGroup, tanggiamtl: TanggiamtlFormGroupInput): void {
    const tanggiamtlRawValue = { ...this.getFormDefaults(), ...tanggiamtl };
    form.reset(
      {
        ...tanggiamtlRawValue,
        id: { value: tanggiamtlRawValue.id, disabled: true },
      } as any /* cast to workaround https://github.com/angular/angular/issues/46458 */,
    );
  }

  private getFormDefaults(): TanggiamtlFormDefaults {
    return {
      id: null,
    };
  }
}
