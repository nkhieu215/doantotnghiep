import { Injectable } from '@angular/core';
import { FormGroup, FormControl, Validators } from '@angular/forms';

import { IBangchamcong, NewBangchamcong } from '../bangchamcong.model';

/**
 * A partial Type with required key is used as form input.
 */
type PartialWithRequiredKeyOf<T extends { id: unknown }> = Partial<Omit<T, 'id'>> & { id: T['id'] };

/**
 * Type for createFormGroup and resetForm argument.
 * It accepts IBangchamcong for edit and NewBangchamcongFormGroupInput for create.
 */
type BangchamcongFormGroupInput = IBangchamcong | PartialWithRequiredKeyOf<NewBangchamcong>;

type BangchamcongFormDefaults = Pick<NewBangchamcong, 'id'>;

type BangchamcongFormGroupContent = {
  id: FormControl<IBangchamcong['id'] | NewBangchamcong['id']>;
  ncdilam: FormControl<IBangchamcong['ncdilam']>;
  thangcc: FormControl<IBangchamcong['thangcc']>;
  nclephep: FormControl<IBangchamcong['nclephep']>;
  xeploai: FormControl<IBangchamcong['xeploai']>;
  ngayththuong: FormControl<IBangchamcong['ngayththuong']>;
  ngaythle: FormControl<IBangchamcong['ngaythle']>;
  nhanvien: FormControl<IBangchamcong['nhanvien']>;
};

export type BangchamcongFormGroup = FormGroup<BangchamcongFormGroupContent>;

@Injectable({ providedIn: 'root' })
export class BangchamcongFormService {
  createBangchamcongFormGroup(bangchamcong: BangchamcongFormGroupInput = { id: null }): BangchamcongFormGroup {
    const bangchamcongRawValue = {
      ...this.getFormDefaults(),
      ...bangchamcong,
    };
    return new FormGroup<BangchamcongFormGroupContent>({
      id: new FormControl(
        { value: bangchamcongRawValue.id, disabled: true },
        {
          nonNullable: true,
          validators: [Validators.required],
        },
      ),
      ncdilam: new FormControl(bangchamcongRawValue.ncdilam),
      thangcc: new FormControl(bangchamcongRawValue.thangcc),
      nclephep: new FormControl(bangchamcongRawValue.nclephep),
      xeploai: new FormControl(bangchamcongRawValue.xeploai),
      ngayththuong: new FormControl(bangchamcongRawValue.ngayththuong),
      ngaythle: new FormControl(bangchamcongRawValue.ngaythle),
      nhanvien: new FormControl(bangchamcongRawValue.nhanvien),
    });
  }

  getBangchamcong(form: BangchamcongFormGroup): IBangchamcong | NewBangchamcong {
    return form.getRawValue() as IBangchamcong | NewBangchamcong;
  }

  resetForm(form: BangchamcongFormGroup, bangchamcong: BangchamcongFormGroupInput): void {
    const bangchamcongRawValue = { ...this.getFormDefaults(), ...bangchamcong };
    form.reset(
      {
        ...bangchamcongRawValue,
        id: { value: bangchamcongRawValue.id, disabled: true },
      } as any /* cast to workaround https://github.com/angular/angular/issues/46458 */,
    );
  }

  private getFormDefaults(): BangchamcongFormDefaults {
    return {
      id: null,
    };
  }
}
