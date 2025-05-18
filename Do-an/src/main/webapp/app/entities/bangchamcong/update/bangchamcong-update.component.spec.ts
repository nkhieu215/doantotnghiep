import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { RouterTestingModule } from '@angular/router/testing';
import { of, Subject, from } from 'rxjs';

import { INhanvien } from 'app/entities/nhanvien/nhanvien.model';
import { NhanvienService } from 'app/entities/nhanvien/service/nhanvien.service';
import { BangchamcongService } from '../service/bangchamcong.service';
import { IBangchamcong } from '../bangchamcong.model';
import { BangchamcongFormService } from './bangchamcong-form.service';

import { BangchamcongUpdateComponent } from './bangchamcong-update.component';

describe('Bangchamcong Management Update Component', () => {
  let comp: BangchamcongUpdateComponent;
  let fixture: ComponentFixture<BangchamcongUpdateComponent>;
  let activatedRoute: ActivatedRoute;
  let bangchamcongFormService: BangchamcongFormService;
  let bangchamcongService: BangchamcongService;
  let nhanvienService: NhanvienService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule, RouterTestingModule.withRoutes([]), BangchamcongUpdateComponent],
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
      .overrideTemplate(BangchamcongUpdateComponent, '')
      .compileComponents();

    fixture = TestBed.createComponent(BangchamcongUpdateComponent);
    activatedRoute = TestBed.inject(ActivatedRoute);
    bangchamcongFormService = TestBed.inject(BangchamcongFormService);
    bangchamcongService = TestBed.inject(BangchamcongService);
    nhanvienService = TestBed.inject(NhanvienService);

    comp = fixture.componentInstance;
  });

  describe('ngOnInit', () => {
    it('Should call Nhanvien query and add missing value', () => {
      const bangchamcong: IBangchamcong = { id: 456 };
      const nhanvien: INhanvien = { id: 32577 };
      bangchamcong.nhanvien = nhanvien;

      const nhanvienCollection: INhanvien[] = [{ id: 13388 }];
      jest.spyOn(nhanvienService, 'query').mockReturnValue(of(new HttpResponse({ body: nhanvienCollection })));
      const additionalNhanviens = [nhanvien];
      const expectedCollection: INhanvien[] = [...additionalNhanviens, ...nhanvienCollection];
      jest.spyOn(nhanvienService, 'addNhanvienToCollectionIfMissing').mockReturnValue(expectedCollection);

      activatedRoute.data = of({ bangchamcong });
      comp.ngOnInit();

      expect(nhanvienService.query).toHaveBeenCalled();
      expect(nhanvienService.addNhanvienToCollectionIfMissing).toHaveBeenCalledWith(
        nhanvienCollection,
        ...additionalNhanviens.map(expect.objectContaining),
      );
      expect(comp.nhanviensSharedCollection).toEqual(expectedCollection);
    });

    it('Should update editForm', () => {
      const bangchamcong: IBangchamcong = { id: 456 };
      const nhanvien: INhanvien = { id: 25481 };
      bangchamcong.nhanvien = nhanvien;

      activatedRoute.data = of({ bangchamcong });
      comp.ngOnInit();

      expect(comp.nhanviensSharedCollection).toContain(nhanvien);
      expect(comp.bangchamcong).toEqual(bangchamcong);
    });
  });

  describe('save', () => {
    it('Should call update service on save for existing entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IBangchamcong>>();
      const bangchamcong = { id: 123 };
      jest.spyOn(bangchamcongFormService, 'getBangchamcong').mockReturnValue(bangchamcong);
      jest.spyOn(bangchamcongService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ bangchamcong });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: bangchamcong }));
      saveSubject.complete();

      // THEN
      expect(bangchamcongFormService.getBangchamcong).toHaveBeenCalled();
      expect(comp.previousState).toHaveBeenCalled();
      expect(bangchamcongService.update).toHaveBeenCalledWith(expect.objectContaining(bangchamcong));
      expect(comp.isSaving).toEqual(false);
    });

    it('Should call create service on save for new entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IBangchamcong>>();
      const bangchamcong = { id: 123 };
      jest.spyOn(bangchamcongFormService, 'getBangchamcong').mockReturnValue({ id: null });
      jest.spyOn(bangchamcongService, 'create').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ bangchamcong: null });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: bangchamcong }));
      saveSubject.complete();

      // THEN
      expect(bangchamcongFormService.getBangchamcong).toHaveBeenCalled();
      expect(bangchamcongService.create).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).toHaveBeenCalled();
    });

    it('Should set isSaving to false on error', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IBangchamcong>>();
      const bangchamcong = { id: 123 };
      jest.spyOn(bangchamcongService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ bangchamcong });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.error('This is an error!');

      // THEN
      expect(bangchamcongService.update).toHaveBeenCalled();
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
