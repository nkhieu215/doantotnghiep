import { Injectable } from '@angular/core';
import { FormGroup, FormControl, Validators } from '@angular/forms';

import { IThamsotl, NewThamsotl } from '../thamsotl.model';

/**
 * A partial Type with required key is used as form input.
 */
type PartialWithRequiredKeyOf<T extends { id: unknown }> = Partial<Omit<T, 'id'>> & { id: T['id'] };

/**
 * Type for createFormGroup and resetForm argument.
 * It accepts IThamsotl for edit and NewThamsotlFormGroupInput for create.
 */
type ThamsotlFormGroupInput = IThamsotl | PartialWithRequiredKeyOf<NewThamsotl>;

type ThamsotlFormDefaults = Pick<NewThamsotl, 'id'>;

type ThamsotlFormGroupContent = {
  id: FormControl<IThamsotl['id'] | NewThamsotl['id']>;
  thangnam: FormControl<IThamsotl['thangnam']>;
  ncchuan: FormControl<IThamsotl['ncchuan']>;
  giocchuan: FormControl<IThamsotl['giocchuan']>;
  hsgioth: FormControl<IThamsotl['hsgioth']>;
  hsgiole: FormControl<IThamsotl['hsgiole']>;
  pcan: FormControl<IThamsotl['pcan']>;
  tlbhxh: FormControl<IThamsotl['tlbhxh']>;
  tlbhyt: FormControl<IThamsotl['tlbhyt']>;
  tlbhtn: FormControl<IThamsotl['tlbhtn']>;
  tlkpcd: FormControl<IThamsotl['tlkpcd']>;
  nhanvien: FormControl<IThamsotl['nhanvien']>;
};

export type ThamsotlFormGroup = FormGroup<ThamsotlFormGroupContent>;

@Injectable({ providedIn: 'root' })
export class ThamsotlFormService {
  createThamsotlFormGroup(thamsotl: ThamsotlFormGroupInput = { id: null }): ThamsotlFormGroup {
    const thamsotlRawValue = {
      ...this.getFormDefaults(),
      ...thamsotl,
    };
    return new FormGroup<ThamsotlFormGroupContent>({
      id: new FormControl(
        { value: thamsotlRawValue.id, disabled: true },
        {
          nonNullable: true,
          validators: [Validators.required],
        },
      ),
      thangnam: new FormControl(thamsotlRawValue.thangnam),
      ncchuan: new FormControl(thamsotlRawValue.ncchuan),
      giocchuan: new FormControl(thamsotlRawValue.giocchuan),
      hsgioth: new FormControl(thamsotlRawValue.hsgioth),
      hsgiole: new FormControl(thamsotlRawValue.hsgiole),
      pcan: new FormControl(thamsotlRawValue.pcan),
      tlbhxh: new FormControl(thamsotlRawValue.tlbhxh),
      tlbhyt: new FormControl(thamsotlRawValue.tlbhyt),
      tlbhtn: new FormControl(thamsotlRawValue.tlbhtn),
      tlkpcd: new FormControl(thamsotlRawValue.tlkpcd),
      nhanvien: new FormControl(thamsotlRawValue.nhanvien),
    });
  }

  getThamsotl(form: ThamsotlFormGroup): IThamsotl | NewThamsotl {
    return form.getRawValue() as IThamsotl | NewThamsotl;
  }

  resetForm(form: ThamsotlFormGroup, thamsotl: ThamsotlFormGroupInput): void {
    const thamsotlRawValue = { ...this.getFormDefaults(), ...thamsotl };
    form.reset(
      {
        ...thamsotlRawValue,
        id: { value: thamsotlRawValue.id, disabled: true },
      } as any /* cast to workaround https://github.com/angular/angular/issues/46458 */,
    );
  }

  private getFormDefaults(): ThamsotlFormDefaults {
    return {
      id: null,
    };
  }
}
