import { TestBed } from '@angular/core/testing';

import { sampleWithRequiredData, sampleWithNewData } from '../tanggiamtl.test-samples';

import { TanggiamtlFormService } from './tanggiamtl-form.service';

describe('Tanggiamtl Form Service', () => {
  let service: TanggiamtlFormService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(TanggiamtlFormService);
  });

  describe('Service methods', () => {
    describe('createTanggiamtlFormGroup', () => {
      it('should create a new form with FormControl', () => {
        const formGroup = service.createTanggiamtlFormGroup();

        expect(formGroup.controls).toEqual(
          expect.objectContaining({
            id: expect.any(Object),
            ngaythang: expect.any(Object),
            tkn: expect.any(Object),
            tkc: expect.any(Object),
            sotien: expect.any(Object),
            diengiai: expect.any(Object),
            nhanvien: expect.any(Object),
          }),
        );
      });

      it('passing ITanggiamtl should create a new form with FormGroup', () => {
        const formGroup = service.createTanggiamtlFormGroup(sampleWithRequiredData);

        expect(formGroup.controls).toEqual(
          expect.objectContaining({
            id: expect.any(Object),
            ngaythang: expect.any(Object),
            tkn: expect.any(Object),
            tkc: expect.any(Object),
            sotien: expect.any(Object),
            diengiai: expect.any(Object),
            nhanvien: expect.any(Object),
          }),
        );
      });
    });

    describe('getTanggiamtl', () => {
      it('should return NewTanggiamtl for default Tanggiamtl initial value', () => {
        const formGroup = service.createTanggiamtlFormGroup(sampleWithNewData);

        const tanggiamtl = service.getTanggiamtl(formGroup) as any;

        expect(tanggiamtl).toMatchObject(sampleWithNewData);
      });

      it('should return NewTanggiamtl for empty Tanggiamtl initial value', () => {
        const formGroup = service.createTanggiamtlFormGroup();

        const tanggiamtl = service.getTanggiamtl(formGroup) as any;

        expect(tanggiamtl).toMatchObject({});
      });

      it('should return ITanggiamtl', () => {
        const formGroup = service.createTanggiamtlFormGroup(sampleWithRequiredData);

        const tanggiamtl = service.getTanggiamtl(formGroup) as any;

        expect(tanggiamtl).toMatchObject(sampleWithRequiredData);
      });
    });

    describe('resetForm', () => {
      it('passing ITanggiamtl should not enable id FormControl', () => {
        const formGroup = service.createTanggiamtlFormGroup();
        expect(formGroup.controls.id.disabled).toBe(true);

        service.resetForm(formGroup, sampleWithRequiredData);

        expect(formGroup.controls.id.disabled).toBe(true);
      });

      it('passing NewTanggiamtl should disable id FormControl', () => {
        const formGroup = service.createTanggiamtlFormGroup(sampleWithRequiredData);
        expect(formGroup.controls.id.disabled).toBe(true);

        service.resetForm(formGroup, { id: null });

        expect(formGroup.controls.id.disabled).toBe(true);
      });
    });
  });
});
