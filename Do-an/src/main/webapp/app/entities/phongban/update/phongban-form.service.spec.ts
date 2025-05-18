import { TestBed } from '@angular/core/testing';

import { sampleWithRequiredData, sampleWithNewData } from '../phongban.test-samples';

import { PhongbanFormService } from './phongban-form.service';

describe('Phongban Form Service', () => {
  let service: PhongbanFormService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(PhongbanFormService);
  });

  describe('Service methods', () => {
    describe('createPhongbanFormGroup', () => {
      it('should create a new form with FormControl', () => {
        const formGroup = service.createPhongbanFormGroup();

        expect(formGroup.controls).toEqual(
          expect.objectContaining({
            id: expect.any(Object),
            mapb: expect.any(Object),
            tenpb: expect.any(Object),
            sdt: expect.any(Object),
          }),
        );
      });

      it('passing IPhongban should create a new form with FormGroup', () => {
        const formGroup = service.createPhongbanFormGroup(sampleWithRequiredData);

        expect(formGroup.controls).toEqual(
          expect.objectContaining({
            id: expect.any(Object),
            mapb: expect.any(Object),
            tenpb: expect.any(Object),
            sdt: expect.any(Object),
          }),
        );
      });
    });

    describe('getPhongban', () => {
      it('should return NewPhongban for default Phongban initial value', () => {
        const formGroup = service.createPhongbanFormGroup(sampleWithNewData);

        const phongban = service.getPhongban(formGroup) as any;

        expect(phongban).toMatchObject(sampleWithNewData);
      });

      it('should return NewPhongban for empty Phongban initial value', () => {
        const formGroup = service.createPhongbanFormGroup();

        const phongban = service.getPhongban(formGroup) as any;

        expect(phongban).toMatchObject({});
      });

      it('should return IPhongban', () => {
        const formGroup = service.createPhongbanFormGroup(sampleWithRequiredData);

        const phongban = service.getPhongban(formGroup) as any;

        expect(phongban).toMatchObject(sampleWithRequiredData);
      });
    });

    describe('resetForm', () => {
      it('passing IPhongban should not enable id FormControl', () => {
        const formGroup = service.createPhongbanFormGroup();
        expect(formGroup.controls.id.disabled).toBe(true);

        service.resetForm(formGroup, sampleWithRequiredData);

        expect(formGroup.controls.id.disabled).toBe(true);
      });

      it('passing NewPhongban should disable id FormControl', () => {
        const formGroup = service.createPhongbanFormGroup(sampleWithRequiredData);
        expect(formGroup.controls.id.disabled).toBe(true);

        service.resetForm(formGroup, { id: null });

        expect(formGroup.controls.id.disabled).toBe(true);
      });
    });
  });
});
