import { Injectable } from '@angular/core';
import { FormGroup, FormControl, Validators } from '@angular/forms';

import { IPhongban, NewPhongban } from '../phongban.model';

/**
 * A partial Type with required key is used as form input.
 */
type PartialWithRequiredKeyOf<T extends { id: unknown }> = Partial<Omit<T, 'id'>> & { id: T['id'] };

/**
 * Type for createFormGroup and resetForm argument.
 * It accepts IPhongban for edit and NewPhongbanFormGroupInput for create.
 */
type PhongbanFormGroupInput = IPhongban | PartialWithRequiredKeyOf<NewPhongban>;

type PhongbanFormDefaults = Pick<NewPhongban, 'id'>;

type PhongbanFormGroupContent = {
  id: FormControl<IPhongban['id'] | NewPhongban['id']>;
  mapb: FormControl<IPhongban['mapb']>;
  tenpb: FormControl<IPhongban['tenpb']>;
  sdt: FormControl<IPhongban['sdt']>;
};

export type PhongbanFormGroup = FormGroup<PhongbanFormGroupContent>;

@Injectable({ providedIn: 'root' })
export class PhongbanFormService {
  createPhongbanFormGroup(phongban: PhongbanFormGroupInput = { id: null }): PhongbanFormGroup {
    const phongbanRawValue = {
      ...this.getFormDefaults(),
      ...phongban,
    };
    return new FormGroup<PhongbanFormGroupContent>({
      id: new FormControl(
        { value: phongbanRawValue.id, disabled: true },
        {
          nonNullable: true,
          validators: [Validators.required],
        },
      ),
      mapb: new FormControl(phongbanRawValue.mapb),
      tenpb: new FormControl(phongbanRawValue.tenpb),
      sdt: new FormControl(phongbanRawValue.sdt),
    });
  }

  getPhongban(form: PhongbanFormGroup): IPhongban | NewPhongban {
    return form.getRawValue() as IPhongban | NewPhongban;
  }

  resetForm(form: PhongbanFormGroup, phongban: PhongbanFormGroupInput): void {
    const phongbanRawValue = { ...this.getFormDefaults(), ...phongban };
    form.reset(
      {
        ...phongbanRawValue,
        id: { value: phongbanRawValue.id, disabled: true },
      } as any /* cast to workaround https://github.com/angular/angular/issues/46458 */,
    );
  }

  private getFormDefaults(): PhongbanFormDefaults {
    return {
      id: null,
    };
  }
}
