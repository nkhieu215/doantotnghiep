import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { RouterTestingModule } from '@angular/router/testing';
import { of, Subject, from } from 'rxjs';

import { INhanvien } from 'app/entities/nhanvien/nhanvien.model';
import { NhanvienService } from 'app/entities/nhanvien/service/nhanvien.service';
import { TanggiamtlService } from '../service/tanggiamtl.service';
import { ITanggiamtl } from '../tanggiamtl.model';
import { TanggiamtlFormService } from './tanggiamtl-form.service';

import { TanggiamtlUpdateComponent } from './tanggiamtl-update.component';

describe('Tanggiamtl Management Update Component', () => {
  let comp: TanggiamtlUpdateComponent;
  let fixture: ComponentFixture<TanggiamtlUpdateComponent>;
  let activatedRoute: ActivatedRoute;
  let tanggiamtlFormService: TanggiamtlFormService;
  let tanggiamtlService: TanggiamtlService;
  let nhanvienService: NhanvienService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule, RouterTestingModule.withRoutes([]), TanggiamtlUpdateComponent],
      providers: [
        FormBuilder,
        {
          provide: ActivatedRoute,
          useValue: {
            params: from([{}]),
          },
        },
      ],
    })
      .overrideTemplate(TanggiamtlUpdateComponent, '')
      .compileComponents();

    fixture = TestBed.createComponent(TanggiamtlUpdateComponent);
    activatedRoute = TestBed.inject(ActivatedRoute);
    tanggiamtlFormService = TestBed.inject(TanggiamtlFormService);
    tanggiamtlService = TestBed.inject(TanggiamtlService);
    nhanvienService = TestBed.inject(NhanvienService);

    comp = fixture.componentInstance;
  });

  describe('ngOnInit', () => {
    it('Should call Nhanvien query and add missing value', () => {
      const tanggiamtl: ITanggiamtl = { id: 456 };
      const nhanvien: INhanvien = { id: 19562 };
      tanggiamtl.nhanvien = nhanvien;

      const nhanvienCollection: INhanvien[] = [{ id: 29717 }];
      jest.spyOn(nhanvienService, 'query').mockReturnValue(of(new HttpResponse({ body: nhanvienCollection })));
      const additionalNhanviens = [nhanvien];
      const expectedCollection: INhanvien[] = [...additionalNhanviens, ...nhanvienCollection];
      jest.spyOn(nhanvienService, 'addNhanvienToCollectionIfMissing').mockReturnValue(expectedCollection);

      activatedRoute.data = of({ tanggiamtl });
      comp.ngOnInit();

      expect(nhanvienService.query).toHaveBeenCalled();
      expect(nhanvienService.addNhanvienToCollectionIfMissing).toHaveBeenCalledWith(
        nhanvienCollection,
        ...additionalNhanviens.map(expect.objectContaining),
      );
      expect(comp.nhanviensSharedCollection).toEqual(expectedCollection);
    });

    it('Should update editForm', () => {
      const tanggiamtl: ITanggiamtl = { id: 456 };
      const nhanvien: INhanvien = { id: 32369 };
      tanggiamtl.nhanvien = nhanvien;

      activatedRoute.data = of({ tanggiamtl });
      comp.ngOnInit();

      expect(comp.nhanviensSharedCollection).toContain(nhanvien);
      expect(comp.tanggiamtl).toEqual(tanggiamtl);
    });
  });

  describe('save', () => {
    it('Should call update service on save for existing entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<ITanggiamtl>>();
      const tanggiamtl = { id: 123 };
      jest.spyOn(tanggiamtlFormService, 'getTanggiamtl').mockReturnValue(tanggiamtl);
      jest.spyOn(tanggiamtlService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ tanggiamtl });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: tanggiamtl }));
      saveSubject.complete();

      // THEN
      expect(tanggiamtlFormService.getTanggiamtl).toHaveBeenCalled();
      expect(comp.previousState).toHaveBeenCalled();
      expect(tanggiamtlService.update).toHaveBeenCalledWith(expect.objectContaining(tanggiamtl));
      expect(comp.isSaving).toEqual(false);
    });

    it('Should call create service on save for new entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<ITanggiamtl>>();
      const tanggiamtl = { id: 123 };
      jest.spyOn(tanggiamtlFormService, 'getTanggiamtl').mockReturnValue({ id: null });
      jest.spyOn(tanggiamtlService, 'create').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ tanggiamtl: null });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: tanggiamtl }));
      saveSubject.complete();

      // THEN
      expect(tanggiamtlFormService.getTanggiamtl).toHaveBeenCalled();
      expect(tanggiamtlService.create).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).toHaveBeenCalled();
    });

    it('Should set isSaving to false on error', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<ITanggiamtl>>();
      const tanggiamtl = { id: 123 };
      jest.spyOn(tanggiamtlService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ tanggiamtl });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.error('This is an error!');

      // THEN
      expect(tanggiamtlService.update).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).not.toHaveBeenCalled();
    });
  });

  describe('Compare relationships', () => {
    describe('compareNhanvien', () => {
      it('Should forward to nhanvienService', () => {
        const entity = { id: 123 };
        const entity2 = { id: 456 };
        jest.spyOn(nhanvienService, 'compareNhanvien');
        comp.compareNhanvien(entity, entity2);
        expect(nhanvienService.compareNhanvien).toHaveBeenCalledWith(entity, entity2);
      });
    });
  });
});
