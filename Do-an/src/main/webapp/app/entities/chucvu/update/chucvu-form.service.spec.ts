import { TestBed } from '@angular/core/testing';

import { sampleWithRequiredData, sampleWithNewData } from '../chucvu.test-samples';

import { ChucvuFormService } from './chucvu-form.service';

describe('Chucvu Form Service', () => {
  let service: ChucvuFormService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(ChucvuFormService);
  });

  describe('Service methods', () => {
    describe('createChucvuFormGroup', () => {
      it('should create a new form with FormControl', () => {
        const formGroup = service.createChucvuFormGroup();

        expect(formGroup.controls).toEqual(
          expect.objectContaining({
            id: expect.any(Object),
            macv: expect.any(Object),
            tencv: expect.any(Object),
            hcpccv: expect.any(Object),
          }),
        );
      });

      it('passing IChucvu should create a new form with FormGroup', () => {
        const formGroup = service.createChucvuFormGroup(sampleWithRequiredData);

        expect(formGroup.controls).toEqual(
          expect.objectContaining({
            id: expect.any(Object),
            macv: expect.any(Object),
            tencv: expect.any(Object),
            hcpccv: expect.any(Object),
          }),
        );
      });
    });

    describe('getChucvu', () => {
      it('should return NewChucvu for default Chucvu initial value', () => {
        const formGroup = service.createChucvuFormGroup(sampleWithNewData);

        const chucvu = service.getChucvu(formGroup) as any;

        expect(chucvu).toMatchObject(sampleWithNewData);
      });

      it('should return NewChucvu for empty Chucvu initial value', () => {
        const formGroup = service.createChucvuFormGroup();

        const chucvu = service.getChucvu(formGroup) as any;

        expect(chucvu).toMatchObject({});
      });

      it('should return IChucvu', () => {
        const formGroup = service.createChucvuFormGroup(sampleWithRequiredData);

        const chucvu = service.getChucvu(formGroup) as any;

        expect(chucvu).toMatchObject(sampleWithRequiredData);
      });
    });

    describe('resetForm', () => {
      it('passing IChucvu should not enable id FormControl', () => {
        const formGroup = service.createChucvuFormGroup();
        expect(formGroup.controls.id.disabled).toBe(true);

        service.resetForm(formGroup, sampleWithRequiredData);

        expect(formGroup.controls.id.disabled).toBe(true);
      });

      it('passing NewChucvu should disable id FormControl', () => {
        const formGroup = service.createChucvuFormGroup(sampleWithRequiredData);
        expect(formGroup.controls.id.disabled).toBe(true);

        service.resetForm(formGroup, { id: null });

        expect(formGroup.controls.id.disabled).toBe(true);
      });
    });
  });
});
