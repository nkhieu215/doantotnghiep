import { Injectable } from '@angular/core';
import { FormGroup, FormControl, Validators } from '@angular/forms';

import { IThuetn, NewThuetn } from '../thuetn.model';

/**
 * A partial Type with required key is used as form input.
 */
type PartialWithRequiredKeyOf<T extends { id: unknown }> = Partial<Omit<T, 'id'>> & { id: T['id'] };

/**
 * Type for createFormGroup and resetForm argument.
 * It accepts IThuetn for edit and NewThuetnFormGroupInput for create.
 */
type ThuetnFormGroupInput = IThuetn | PartialWithRequiredKeyOf<NewThuetn>;

type ThuetnFormDefaults = Pick<NewThuetn, 'id'>;

type ThuetnFormGroupContent = {
  id: FormControl<IThuetn['id'] | NewThuetn['id']>;
  bacthue: FormControl<IThuetn['bacthue']>;
  tu: FormControl<IThuetn['tu']>;
  den: FormControl<IThuetn['den']>;
  thuesuat: FormControl<IThuetn['thuesuat']>;
};

export type ThuetnFormGroup = FormGroup<ThuetnFormGroupContent>;

@Injectable({ providedIn: 'root' })
export class ThuetnFormService {
  createThuetnFormGroup(thuetn: ThuetnFormGroupInput = { id: null }): ThuetnFormGroup {
    const thuetnRawValue = {
      ...this.getFormDefaults(),
      ...thuetn,
    };
    return new FormGroup<ThuetnFormGroupContent>({
      id: new FormControl(
        { value: thuetnRawValue.id, disabled: true },
        {
          nonNullable: true,
          validators: [Validators.required],
        },
      ),
      bacthue: new FormControl(thuetnRawValue.bacthue),
      tu: new FormControl(thuetnRawValue.tu),
      den: new FormControl(thuetnRawValue.den),
      thuesuat: new FormControl(thuetnRawValue.thuesuat),
    });
  }

  getThuetn(form: ThuetnFormGroup): IThuetn | NewThuetn {
    return form.getRawValue() as IThuetn | NewThuetn;
  }

  resetForm(form: ThuetnFormGroup, thuetn: ThuetnFormGroupInput): void {
    const thuetnRawValue = { ...this.getFormDefaults(), ...thuetn };
    form.reset(
      {
        ...thuetnRawValue,
        id: { value: thuetnRawValue.id, disabled: true },
      } as any /* cast to workaround https://github.com/angular/angular/issues/46458 */,
    );
  }

  private getFormDefaults(): ThuetnFormDefaults {
    return {
      id: null,
    };
  }
}
