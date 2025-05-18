import { TestBed } from '@angular/core/testing';

import { sampleWithRequiredData, sampleWithNewData } from '../bangchamcong.test-samples';

import { BangchamcongFormService } from './bangchamcong-form.service';

describe('Bangchamcong Form Service', () => {
  let service: BangchamcongFormService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(BangchamcongFormService);
  });

  describe('Service methods', () => {
    describe('createBangchamcongFormGroup', () => {
      it('should create a new form with FormControl', () => {
        const formGroup = service.createBangchamcongFormGroup();

        expect(formGroup.controls).toEqual(
          expect.objectContaining({
            id: expect.any(Object),
            ncdilam: expect.any(Object),
            thangcc: expect.any(Object),
            nclephep: expect.any(Object),
            xeploai: expect.any(Object),
            ngayththuong: expect.any(Object),
            ngaythle: expect.any(Object),
            nhanvien: expect.any(Object),
          }),
        );
      });

      it('passing IBangchamcong should create a new form with FormGroup', () => {
        const formGroup = service.createBangchamcongFormGroup(sampleWithRequiredData);

        expect(formGroup.controls).toEqual(
          expect.objectContaining({
            id: expect.any(Object),
            ncdilam: expect.any(Object),
            thangcc: expect.any(Object),
            nclephep: expect.any(Object),
            xeploai: expect.any(Object),
            ngayththuong: expect.any(Object),
            ngaythle: expect.any(Object),
            nhanvien: expect.any(Object),
          }),
        );
      });
    });

    describe('getBangchamcong', () => {
      it('should return NewBangchamcong for default Bangchamcong initial value', () => {
        const formGroup = service.createBangchamcongFormGroup(sampleWithNewData);

        const bangchamcong = service.getBangchamcong(formGroup) as any;

        expect(bangchamcong).toMatchObject(sampleWithNewData);
      });

      it('should return NewBangchamcong for empty Bangchamcong initial value', () => {
        const formGroup = service.createBangchamcongFormGroup();

        const bangchamcong = service.getBangchamcong(formGroup) as any;

        expect(bangchamcong).toMatchObject({});
      });

      it('should return IBangchamcong', () => {
        const formGroup = service.createBangchamcongFormGroup(sampleWithRequiredData);

        const bangchamcong = service.getBangchamcong(formGroup) as any;

        expect(bangchamcong).toMatchObject(sampleWithRequiredData);
      });
    });

    describe('resetForm', () => {
      it('passing IBangchamcong should not enable id FormControl', () => {
        const formGroup = service.createBangchamcongFormGroup();
        expect(formGroup.controls.id.disabled).toBe(true);

        service.resetForm(formGroup, sampleWithRequiredData);

        expect(formGroup.controls.id.disabled).toBe(true);
      });

      it('passing NewBangchamcong should disable id FormControl', () => {
        const formGroup = service.createBangchamcongFormGroup(sampleWithRequiredData);
        expect(formGroup.controls.id.disabled).toBe(true);

        service.resetForm(formGroup, { id: null });

        expect(formGroup.controls.id.disabled).toBe(true);
      });
    });
  });
});
