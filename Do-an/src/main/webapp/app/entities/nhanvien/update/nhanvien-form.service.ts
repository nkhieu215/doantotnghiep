import { Injectable } from '@angular/core';
import { FormGroup, FormControl, Validators } from '@angular/forms';

import dayjs from 'dayjs/esm';
import { DATE_TIME_FORMAT } from 'app/config/input.constants';
import { INhanvien, NewNhanvien } from '../nhanvien.model';

/**
 * A partial Type with required key is used as form input.
 */
type PartialWithRequiredKeyOf<T extends { id: unknown }> = Partial<Omit<T, 'id'>> & { id: T['id'] };

/**
 * Type for createFormGroup and resetForm argument.
 * It accepts INhanvien for edit and NewNhanvienFormGroupInput for create.
 */
type NhanvienFormGroupInput = INhanvien | PartialWithRequiredKeyOf<NewNhanvien>;

/**
 * Type that converts some properties for forms.
 */
type FormValueOf<T extends INhanvien | NewNhanvien> = Omit<T, 'ngaysinh'> & {
  ngaysinh?: string | null;
};

type NhanvienFormRawValue = FormValueOf<INhanvien>;

type NewNhanvienFormRawValue = FormValueOf<NewNhanvien>;

type NhanvienFormDefaults = Pick<NewNhanvien, 'id' | 'ngaysinh'>;

type NhanvienFormGroupContent = {
  id: FormControl<NhanvienFormRawValue['id'] | NewNhanvien['id']>;
  manv: FormControl<NhanvienFormRawValue['manv']>;
  hoten: FormControl<NhanvienFormRawValue['hoten']>;
  ngaysinh: FormControl<NhanvienFormRawValue['ngaysinh']>;
  gioitinh: FormControl<NhanvienFormRawValue['gioitinh']>;
  quequan: FormControl<NhanvienFormRawValue['quequan']>;
  diachi: FormControl<NhanvienFormRawValue['diachi']>;
  hsluong: FormControl<NhanvienFormRawValue['hsluong']>;
  msthue: FormControl<NhanvienFormRawValue['msthue']>;
  chucvu: FormControl<NhanvienFormRawValue['chucvu']>;
  phongban: FormControl<NhanvienFormRawValue['phongban']>;
};

export type NhanvienFormGroup = FormGroup<NhanvienFormGroupContent>;

@Injectable({ providedIn: 'root' })
export class NhanvienFormService {
  createNhanvienFormGroup(nhanvien: NhanvienFormGroupInput = { id: null }): NhanvienFormGroup {
    const nhanvienRawValue = this.convertNhanvienToNhanvienRawValue({
      ...this.getFormDefaults(),
      ...nhanvien,
    });
    return new FormGroup<NhanvienFormGroupContent>({
      id: new FormControl(
        { value: nhanvienRawValue.id, disabled: true },
        {
          nonNullable: true,
          validators: [Validators.required],
        },
      ),
      manv: new FormControl(nhanvienRawValue.manv),
      hoten: new FormControl(nhanvienRawValue.hoten),
      ngaysinh: new FormControl(nhanvienRawValue.ngaysinh),
      gioitinh: new FormControl(nhanvienRawValue.gioitinh),
      quequan: new FormControl(nhanvienRawValue.quequan),
      diachi: new FormControl(nhanvienRawValue.diachi),
      hsluong: new FormControl(nhanvienRawValue.hsluong),
      msthue: new FormControl(nhanvienRawValue.msthue),
      chucvu: new FormControl(nhanvienRawValue.chucvu),
      phongban: new FormControl(nhanvienRawValue.phongban),
    });
  }

  getNhanvien(form: NhanvienFormGroup): INhanvien | NewNhanvien {
    return this.convertNhanvienRawValueToNhanvien(form.getRawValue() as NhanvienFormRawValue | NewNhanvienFormRawValue);
  }

  resetForm(form: NhanvienFormGroup, nhanvien: NhanvienFormGroupInput): void {
    const nhanvienRawValue = this.convertNhanvienToNhanvienRawValue({ ...this.getFormDefaults(), ...nhanvien });
    form.reset(
      {
        ...nhanvienRawValue,
        id: { value: nhanvienRawValue.id, disabled: true },
      } as any /* cast to workaround https://github.com/angular/angular/issues/46458 */,
    );
  }

  private getFormDefaults(): NhanvienFormDefaults {
    const currentTime = dayjs();

    return {
      id: null,
      ngaysinh: currentTime,
    };
  }

  private convertNhanvienRawValueToNhanvien(rawNhanvien: NhanvienFormRawValue | NewNhanvienFormRawValue): INhanvien | NewNhanvien {
    return {
      ...rawNhanvien,
      ngaysinh: dayjs(rawNhanvien.ngaysinh, DATE_TIME_FORMAT),
    };
  }

  private convertNhanvienToNhanvienRawValue(
    nhanvien: INhanvien | (Partial<NewNhanvien> & NhanvienFormDefaults),
  ): NhanvienFormRawValue | PartialWithRequiredKeyOf<NewNhanvienFormRawValue> {
    return {
      ...nhanvien,
      ngaysinh: nhanvien.ngaysinh ? nhanvien.ngaysinh.format(DATE_TIME_FORMAT) : undefined,
    };
  }
}
