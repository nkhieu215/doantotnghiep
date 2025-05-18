import { Injectable } from '@angular/core';
import { FormGroup, FormControl, Validators } from '@angular/forms';

import { IChucvu, NewChucvu } from '../chucvu.model';

/**
 * A partial Type with required key is used as form input.
 */
type PartialWithRequiredKeyOf<T extends { id: unknown }> = Partial<Omit<T, 'id'>> & { id: T['id'] };

/**
 * Type for createFormGroup and resetForm argument.
 * It accepts IChucvu for edit and NewChucvuFormGroupInput for create.
 */
type ChucvuFormGroupInput = IChucvu | PartialWithRequiredKeyOf<NewChucvu>;

type ChucvuFormDefaults = Pick<NewChucvu, 'id'>;

type ChucvuFormGroupContent = {
  id: FormControl<IChucvu['id'] | NewChucvu['id']>;
  macv: FormControl<IChucvu['macv']>;
  tencv: FormControl<IChucvu['tencv']>;
  hcpccv: FormControl<IChucvu['hcpccv']>;
};

export type ChucvuFormGroup = FormGroup<ChucvuFormGroupContent>;

@Injectable({ providedIn: 'root' })
export class ChucvuFormService {
  createChucvuFormGroup(chucvu: ChucvuFormGroupInput = { id: null }): ChucvuFormGroup {
    const chucvuRawValue = {
      ...this.getFormDefaults(),
      ...chucvu,
    };
    return new FormGroup<ChucvuFormGroupContent>({
      id: new FormControl(
        { value: chucvuRawValue.id, disabled: true },
        {
          nonNullable: true,
          validators: [Validators.required],
        },
      ),
      macv: new FormControl(chucvuRawValue.macv),
      tencv: new FormControl(chucvuRawValue.tencv),
      hcpccv: new FormControl(chucvuRawValue.hcpccv),
    });
  }

  getChucvu(form: ChucvuFormGroup): IChucvu | NewChucvu {
    return form.getRawValue() as IChucvu | NewChucvu;
  }

  resetForm(form: ChucvuFormGroup, chucvu: ChucvuFormGroupInput): void {
    const chucvuRawValue = { ...this.getFormDefaults(), ...chucvu };
    form.reset(
      {
        ...chucvuRawValue,
        id: { value: chucvuRawValue.id, disabled: true },
      } as any /* cast to workaround https://github.com/angular/angular/issues/46458 */,
    );
  }

  private getFormDefaults(): ChucvuFormDefaults {
    return {
      id: null,
    };
  }
}
