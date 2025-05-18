import { TestBed } from '@angular/core/testing';

import { sampleWithRequiredData, sampleWithNewData } from '../thuetn.test-samples';

import { ThuetnFormService } from './thuetn-form.service';

describe('Thuetn Form Service', () => {
  let service: ThuetnFormService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(ThuetnFormService);
  });

  describe('Service methods', () => {
    describe('createThuetnFormGroup', () => {
      it('should create a new form with FormControl', () => {
        const formGroup = service.createThuetnFormGroup();

        expect(formGroup.controls).toEqual(
          expect.objectContaining({
            id: expect.any(Object),
            bacthue: expect.any(Object),
            tu: expect.any(Object),
            den: expect.any(Object),
            thuesuat: expect.any(Object),
          }),
        );
      });

      it('passing IThuetn should create a new form with FormGroup', () => {
        const formGroup = service.createThuetnFormGroup(sampleWithRequiredData);

        expect(formGroup.controls).toEqual(
          expect.objectContaining({
            id: expect.any(Object),
            bacthue: expect.any(Object),
            tu: expect.any(Object),
            den: expect.any(Object),
            thuesuat: expect.any(Object),
          }),
        );
      });
    });

    describe('getThuetn', () => {
      it('should return NewThuetn for default Thuetn initial value', () => {
        const formGroup = service.createThuetnFormGroup(sampleWithNewData);

        const thuetn = service.getThuetn(formGroup) as any;

        expect(thuetn).toMatchObject(sampleWithNewData);
      });

      it('should return NewThuetn for empty Thuetn initial value', () => {
        const formGroup = service.createThuetnFormGroup();

        const thuetn = service.getThuetn(formGroup) as any;

        expect(thuetn).toMatchObject({});
      });

      it('should return IThuetn', () => {
        const formGroup = service.createThuetnFormGroup(sampleWithRequiredData);

        const thuetn = service.getThuetn(formGroup) as any;

        expect(thuetn).toMatchObject(sampleWithRequiredData);
      });
    });

    describe('resetForm', () => {
      it('passing IThuetn should not enable id FormControl', () => {
        const formGroup = service.createThuetnFormGroup();
        expect(formGroup.controls.id.disabled).toBe(true);

        service.resetForm(formGroup, sampleWithRequiredData);

        expect(formGroup.controls.id.disabled).toBe(true);
      });

      it('passing NewThuetn should disable id FormControl', () => {
        const formGroup = service.createThuetnFormGroup(sampleWithRequiredData);
        expect(formGroup.controls.id.disabled).toBe(true);

        service.resetForm(formGroup, { id: null });

        expect(formGroup.controls.id.disabled).toBe(true);
      });
    });
  });
});
