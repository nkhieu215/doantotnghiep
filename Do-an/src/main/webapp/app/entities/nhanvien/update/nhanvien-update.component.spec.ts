import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { RouterTestingModule } from '@angular/router/testing';
import { of, Subject, from } from 'rxjs';

import { IChucvu } from 'app/entities/chucvu/chucvu.model';
import { ChucvuService } from 'app/entities/chucvu/service/chucvu.service';
import { IPhongban } from 'app/entities/phongban/phongban.model';
import { PhongbanService } from 'app/entities/phongban/service/phongban.service';
import { INhanvien } from '../nhanvien.model';
import { NhanvienService } from '../service/nhanvien.service';
import { NhanvienFormService } from './nhanvien-form.service';

import { NhanvienUpdateComponent } from './nhanvien-update.component';

describe('Nhanvien Management Update Component', () => {
  let comp: NhanvienUpdateComponent;
  let fixture: ComponentFixture<NhanvienUpdateComponent>;
  let activatedRoute: ActivatedRoute;
  let nhanvienFormService: NhanvienFormService;
  let nhanvienService: NhanvienService;
  let chucvuService: ChucvuService;
  let phongbanService: PhongbanService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule, RouterTestingModule.withRoutes([]), NhanvienUpdateComponent],
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
      .overrideTemplate(NhanvienUpdateComponent, '')
      .compileComponents();

    fixture = TestBed.createComponent(NhanvienUpdateComponent);
    activatedRoute = TestBed.inject(ActivatedRoute);
    nhanvienFormService = TestBed.inject(NhanvienFormService);
    nhanvienService = TestBed.inject(NhanvienService);
    chucvuService = TestBed.inject(ChucvuService);
    phongbanService = TestBed.inject(PhongbanService);

    comp = fixture.componentInstance;
  });

  describe('ngOnInit', () => {
    it('Should call Chucvu query and add missing value', () => {
      const nhanvien: INhanvien = { id: 456 };
      const chucvu: IChucvu = { id: 17353 };
      nhanvien.chucvu = chucvu;

      const chucvuCollection: IChucvu[] = [{ id: 30011 }];
      jest.spyOn(chucvuService, 'query').mockReturnValue(of(new HttpResponse({ body: chucvuCollection })));
      const additionalChucvus = [chucvu];
      const expectedCollection: IChucvu[] = [...additionalChucvus, ...chucvuCollection];
      jest.spyOn(chucvuService, 'addChucvuToCollectionIfMissing').mockReturnValue(expectedCollection);

      activatedRoute.data = of({ nhanvien });
      comp.ngOnInit();

      expect(chucvuService.query).toHaveBeenCalled();
      expect(chucvuService.addChucvuToCollectionIfMissing).toHaveBeenCalledWith(
        chucvuCollection,
        ...additionalChucvus.map(expect.objectContaining),
      );
      expect(comp.chucvusSharedCollection).toEqual(expectedCollection);
    });

    it('Should call Phongban query and add missing value', () => {
      const nhanvien: INhanvien = { id: 456 };
      const phongban: IPhongban = { id: 24958 };
      nhanvien.phongban = phongban;

      const phongbanCollection: IPhongban[] = [{ id: 11854 }];
      jest.spyOn(phongbanService, 'query').mockReturnValue(of(new HttpResponse({ body: phongbanCollection })));
      const additionalPhongbans = [phongban];
      const expectedCollection: IPhongban[] = [...additionalPhongbans, ...phongbanCollection];
      jest.spyOn(phongbanService, 'addPhongbanToCollectionIfMissing').mockReturnValue(expectedCollection);

      activatedRoute.data = of({ nhanvien });
      comp.ngOnInit();

      expect(phongbanService.query).toHaveBeenCalled();
      expect(phongbanService.addPhongbanToCollectionIfMissing).toHaveBeenCalledWith(
        phongbanCollection,
        ...additionalPhongbans.map(expect.objectContaining),
      );
      expect(comp.phongbansSharedCollection).toEqual(expectedCollection);
    });

    it('Should update editForm', () => {
      const nhanvien: INhanvien = { id: 456 };
      const chucvu: IChucvu = { id: 1604 };
      nhanvien.chucvu = chucvu;
      const phongban: IPhongban = { id: 22785 };
      nhanvien.phongban = phongban;

      activatedRoute.data = of({ nhanvien });
      comp.ngOnInit();

      expect(comp.chucvusSharedCollection).toContain(chucvu);
      expect(comp.phongbansSharedCollection).toContain(phongban);
      expect(comp.nhanvien).toEqual(nhanvien);
    });
  });

  describe('save', () => {
    it('Should call update service on save for existing entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<INhanvien>>();
      const nhanvien = { id: 123 };
      jest.spyOn(nhanvienFormService, 'getNhanvien').mockReturnValue(nhanvien);
      jest.spyOn(nhanvienService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ nhanvien });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: nhanvien }));
      saveSubject.complete();

      // THEN
      expect(nhanvienFormService.getNhanvien).toHaveBeenCalled();
      expect(comp.previousState).toHaveBeenCalled();
      expect(nhanvienService.update).toHaveBeenCalledWith(expect.objectContaining(nhanvien));
      expect(comp.isSaving).toEqual(false);
    });

    it('Should call create service on save for new entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<INhanvien>>();
      const nhanvien = { id: 123 };
      jest.spyOn(nhanvienFormService, 'getNhanvien').mockReturnValue({ id: null });
      jest.spyOn(nhanvienService, 'create').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ nhanvien: null });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: nhanvien }));
      saveSubject.complete();

      // THEN
      expect(nhanvienFormService.getNhanvien).toHaveBeenCalled();
      expect(nhanvienService.create).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).toHaveBeenCalled();
    });

    it('Should set isSaving to false on error', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<INhanvien>>();
      const nhanvien = { id: 123 };
      jest.spyOn(nhanvienService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ nhanvien });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.error('This is an error!');

      // THEN
      expect(nhanvienService.update).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).not.toHaveBeenCalled();
    });
  });

  describe('Compare relationships', () => {
    describe('compareChucvu', () => {
      it('Should forward to chucvuService', () => {
        const entity = { id: 123 };
        const entity2 = { id: 456 };
        jest.spyOn(chucvuService, 'compareChucvu');
        comp.compareChucvu(entity, entity2);
        expect(chucvuService.compareChucvu).toHaveBeenCalledWith(entity, entity2);
      });
    });

    describe('comparePhongban', () => {
      it('Should forward to phongbanService', () => {
        const entity = { id: 123 };
        const entity2 = { id: 456 };
        jest.spyOn(phongbanService, 'comparePhongban');
        comp.comparePhongban(entity, entity2);
        expect(phongbanService.comparePhongban).toHaveBeenCalledWith(entity, entity2);
      });
    });
  });
});
