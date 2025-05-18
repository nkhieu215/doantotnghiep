import { TestBed } from '@angular/core/testing';

import { sampleWithRequiredData, sampleWithNewData } from '../nhanvien.test-samples';

import { NhanvienFormService } from './nhanvien-form.service';

describe('Nhanvien Form Service', () => {
  let service: NhanvienFormService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(NhanvienFormService);
  });

  describe('Service methods', () => {
    describe('createNhanvienFormGroup', () => {
      it('should create a new form with FormControl', () => {
        const formGroup = service.createNhanvienFormGroup();

        expect(formGroup.controls).toEqual(
          expect.objectContaining({
            id: expect.any(Object),
            manv: expect.any(Object),
            hoten: expect.any(Object),
            ngaysinh: expect.any(Object),
            gioitinh: expect.any(Object),
            quequan: expect.any(Object),
            diachi: expect.any(Object),
            hsluong: expect.any(Object),
            msthue: expect.any(Object),
            chucvu: expect.any(Object),
            phongban: expect.any(Object),
          }),
        );
      });

      it('passing INhanvien should create a new form with FormGroup', () => {
        const formGroup = service.createNhanvienFormGroup(sampleWithRequiredData);

        expect(formGroup.controls).toEqual(
          expect.objectContaining({
            id: expect.any(Object),
            manv: expect.any(Object),
            hoten: expect.any(Object),
            ngaysinh: expect.any(Object),
            gioitinh: expect.any(Object),
            quequan: expect.any(Object),
            diachi: expect.any(Object),
            hsluong: expect.any(Object),
            msthue: expect.any(Object),
            chucvu: expect.any(Object),
            phongban: expect.any(Object),
          }),
        );
      });
    });

    describe('getNhanvien', () => {
      it('should return NewNhanvien for default Nhanvien initial value', () => {
        const formGroup = service.createNhanvienFormGroup(sampleWithNewData);

        const nhanvien = service.getNhanvien(formGroup) as any;

        expect(nhanvien).toMatchObject(sampleWithNewData);
      });

      it('should return NewNhanvien for empty Nhanvien initial value', () => {
        const formGroup = service.createNhanvienFormGroup();

        const nhanvien = service.getNhanvien(formGroup) as any;

        expect(nhanvien).toMatchObject({});
      });

      it('should return INhanvien', () => {
        const formGroup = service.createNhanvienFormGroup(sampleWithRequiredData);

        const nhanvien = service.getNhanvien(formGroup) as any;

        expect(nhanvien).toMatchObject(sampleWithRequiredData);
      });
    });

    describe('resetForm', () => {
      it('passing INhanvien should not enable id FormControl', () => {
        const formGroup = service.createNhanvienFormGroup();
        expect(formGroup.controls.id.disabled).toBe(true);

        service.resetForm(formGroup, sampleWithRequiredData);

        expect(formGroup.controls.id.disabled).toBe(true);
      });

      it('passing NewNhanvien should disable id FormControl', () => {
        const formGroup = service.createNhanvienFormGroup(sampleWithRequiredData);
        expect(formGroup.controls.id.disabled).toBe(true);

        service.resetForm(formGroup, { id: null });

        expect(formGroup.controls.id.disabled).toBe(true);
      });
    });
  });
});
