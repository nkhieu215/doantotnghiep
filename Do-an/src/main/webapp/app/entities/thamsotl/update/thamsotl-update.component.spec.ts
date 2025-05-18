import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { RouterTestingModule } from '@angular/router/testing';
import { of, Subject, from } from 'rxjs';

import { INhanvien } from 'app/entities/nhanvien/nhanvien.model';
import { NhanvienService } from 'app/entities/nhanvien/service/nhanvien.service';
import { ThamsotlService } from '../service/thamsotl.service';
import { IThamsotl } from '../thamsotl.model';
import { ThamsotlFormService } from './thamsotl-form.service';

import { ThamsotlUpdateComponent } from './thamsotl-update.component';

describe('Thamsotl Management Update Component', () => {
  let comp: ThamsotlUpdateComponent;
  let fixture: ComponentFixture<ThamsotlUpdateComponent>;
  let activatedRoute: ActivatedRoute;
  let thamsotlFormService: ThamsotlFormService;
  let thamsotlService: ThamsotlService;
  let nhanvienService: NhanvienService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule, RouterTestingModule.withRoutes([]), ThamsotlUpdateComponent],
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
      .overrideTemplate(ThamsotlUpdateComponent, '')
      .compileComponents();

    fixture = TestBed.createComponent(ThamsotlUpdateComponent);
    activatedRoute = TestBed.inject(ActivatedRoute);
    thamsotlFormService = TestBed.inject(ThamsotlFormService);
    thamsotlService = TestBed.inject(ThamsotlService);
    nhanvienService = TestBed.inject(NhanvienService);

    comp = fixture.componentInstance;
  });

  describe('ngOnInit', () => {
    it('Should call nhanvien query and add missing value', () => {
      const thamsotl: IThamsotl = { id: 456 };
      const nhanvien: INhanvien = { id: 2868 };
      thamsotl.nhanvien = nhanvien;

      const nhanvienCollection: INhanvien[] = [{ id: 431 }];
      jest.spyOn(nhanvienService, 'query').mockReturnValue(of(new HttpResponse({ body: nhanvienCollection })));
      const expectedCollection: INhanvien[] = [nhanvien, ...nhanvienCollection];
      jest.spyOn(nhanvienService, 'addNhanvienToCollectionIfMissing').mockReturnValue(expectedCollection);

      activatedRoute.data = of({ thamsotl });
      comp.ngOnInit();

      expect(nhanvienService.query).toHaveBeenCalled();
      expect(nhanvienService.addNhanvienToCollectionIfMissing).toHaveBeenCalledWith(nhanvienCollection, nhanvien);
      expect(comp.nhanviensCollection).toEqual(expectedCollection);
    });

    it('Should update editForm', () => {
      const thamsotl: IThamsotl = { id: 456 };
      const nhanvien: INhanvien = { id: 11344 };
      thamsotl.nhanvien = nhanvien;

      activatedRoute.data = of({ thamsotl });
      comp.ngOnInit();

      expect(comp.nhanviensCollection).toContain(nhanvien);
      expect(comp.thamsotl).toEqual(thamsotl);
    });
  });

  describe('save', () => {
    it('Should call update service on save for existing entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IThamsotl>>();
      const thamsotl = { id: 123 };
      jest.spyOn(thamsotlFormService, 'getThamsotl').mockReturnValue(thamsotl);
      jest.spyOn(thamsotlService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ thamsotl });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: thamsotl }));
      saveSubject.complete();

      // THEN
      expect(thamsotlFormService.getThamsotl).toHaveBeenCalled();
      expect(comp.previousState).toHaveBeenCalled();
      expect(thamsotlService.update).toHaveBeenCalledWith(expect.objectContaining(thamsotl));
      expect(comp.isSaving).toEqual(false);
    });

    it('Should call create service on save for new entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IThamsotl>>();
      const thamsotl = { id: 123 };
      jest.spyOn(thamsotlFormService, 'getThamsotl').mockReturnValue({ id: null });
      jest.spyOn(thamsotlService, 'create').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ thamsotl: null });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: thamsotl }));
      saveSubject.complete();

      // THEN
      expect(thamsotlFormService.getThamsotl).toHaveBeenCalled();
      expect(thamsotlService.create).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).toHaveBeenCalled();
    });

    it('Should set isSaving to false on error', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IThamsotl>>();
      const thamsotl = { id: 123 };
      jest.spyOn(thamsotlService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ thamsotl });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.error('This is an error!');

      // THEN
      expect(thamsotlService.update).toHaveBeenCalled();
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
