import { TestBed } from '@angular/core/testing';

import { sampleWithRequiredData, sampleWithNewData } from '../thamsotl.test-samples';

import { ThamsotlFormService } from './thamsotl-form.service';

describe('Thamsotl Form Service', () => {
  let service: ThamsotlFormService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(ThamsotlFormService);
  });

  describe('Service methods', () => {
    describe('createThamsotlFormGroup', () => {
      it('should create a new form with FormControl', () => {
        const formGroup = service.createThamsotlFormGroup();

        expect(formGroup.controls).toEqual(
          expect.objectContaining({
            id: expect.any(Object),
            thangnam: expect.any(Object),
            ncchuan: expect.any(Object),
            giocchuan: expect.any(Object),
            hsgioth: expect.any(Object),
            hsgiole: expect.any(Object),
            pcan: expect.any(Object),
            tlbhxh: expect.any(Object),
            tlbhyt: expect.any(Object),
            tlbhtn: expect.any(Object),
            tlkpcd: expect.any(Object),
            nhanvien: expect.any(Object),
          }),
        );
      });

      it('passing IThamsotl should create a new form with FormGroup', () => {
        const formGroup = service.createThamsotlFormGroup(sampleWithRequiredData);

        expect(formGroup.controls).toEqual(
          expect.objectContaining({
            id: expect.any(Object),
            thangnam: expect.any(Object),
            ncchuan: expect.any(Object),
            giocchuan: expect.any(Object),
            hsgioth: expect.any(Object),
            hsgiole: expect.any(Object),
            pcan: expect.any(Object),
            tlbhxh: expect.any(Object),
            tlbhyt: expect.any(Object),
            tlbhtn: expect.any(Object),
            tlkpcd: expect.any(Object),
            nhanvien: expect.any(Object),
          }),
        );
      });
    });

    describe('getThamsotl', () => {
      it('should return NewThamsotl for default Thamsotl initial value', () => {
        const formGroup = service.createThamsotlFormGroup(sampleWithNewData);

        const thamsotl = service.getThamsotl(formGroup) as any;

        expect(thamsotl).toMatchObject(sampleWithNewData);
      });

      it('should return NewThamsotl for empty Thamsotl initial value', () => {
        const formGroup = service.createThamsotlFormGroup();

        const thamsotl = service.getThamsotl(formGroup) as any;

        expect(thamsotl).toMatchObject({});
      });

      it('should return IThamsotl', () => {
        const formGroup = service.createThamsotlFormGroup(sampleWithRequiredData);

        const thamsotl = service.getThamsotl(formGroup) as any;

        expect(thamsotl).toMatchObject(sampleWithRequiredData);
      });
    });

    describe('resetForm', () => {
      it('passing IThamsotl should not enable id FormControl', () => {
        const formGroup = service.createThamsotlFormGroup();
        expect(formGroup.controls.id.disabled).toBe(true);

        service.resetForm(formGroup, sampleWithRequiredData);

        expect(formGroup.controls.id.disabled).toBe(true);
      });

      it('passing NewThamsotl should disable id FormControl', () => {
        const formGroup = service.createThamsotlFormGroup(sampleWithRequiredData);
        expect(formGroup.controls.id.disabled).toBe(true);

        service.resetForm(formGroup, { id: null });

        expect(formGroup.controls.id.disabled).toBe(true);
      });
    });
  });
});
