import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { RouterTestingModule } from '@angular/router/testing';
import { of, Subject, from } from 'rxjs';

import { PhongbanService } from '../service/phongban.service';
import { IPhongban } from '../phongban.model';
import { PhongbanFormService } from './phongban-form.service';

import { PhongbanUpdateComponent } from './phongban-update.component';

describe('Phongban Management Update Component', () => {
  let comp: PhongbanUpdateComponent;
  let fixture: ComponentFixture<PhongbanUpdateComponent>;
  let activatedRoute: ActivatedRoute;
  let phongbanFormService: PhongbanFormService;
  let phongbanService: PhongbanService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule, RouterTestingModule.withRoutes([]), PhongbanUpdateComponent],
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
      .overrideTemplate(PhongbanUpdateComponent, '')
      .compileComponents();

    fixture = TestBed.createComponent(PhongbanUpdateComponent);
    activatedRoute = TestBed.inject(ActivatedRoute);
    phongbanFormService = TestBed.inject(PhongbanFormService);
    phongbanService = TestBed.inject(PhongbanService);

    comp = fixture.componentInstance;
  });

  describe('ngOnInit', () => {
    it('Should update editForm', () => {
      const phongban: IPhongban = { id: 456 };

      activatedRoute.data = of({ phongban });
      comp.ngOnInit();

      expect(comp.phongban).toEqual(phongban);
    });
  });

  describe('save', () => {
    it('Should call update service on save for existing entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IPhongban>>();
      const phongban = { id: 123 };
      jest.spyOn(phongbanFormService, 'getPhongban').mockReturnValue(phongban);
      jest.spyOn(phongbanService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ phongban });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: phongban }));
      saveSubject.complete();

      // THEN
      expect(phongbanFormService.getPhongban).toHaveBeenCalled();
      expect(comp.previousState).toHaveBeenCalled();
      expect(phongbanService.update).toHaveBeenCalledWith(expect.objectContaining(phongban));
      expect(comp.isSaving).toEqual(false);
    });

    it('Should call create service on save for new entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IPhongban>>();
      const phongban = { id: 123 };
      jest.spyOn(phongbanFormService, 'getPhongban').mockReturnValue({ id: null });
      jest.spyOn(phongbanService, 'create').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ phongban: null });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: phongban }));
      saveSubject.complete();

      // THEN
      expect(phongbanFormService.getPhongban).toHaveBeenCalled();
      expect(phongbanService.create).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).toHaveBeenCalled();
    });

    it('Should set isSaving to false on error', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IPhongban>>();
      const phongban = { id: 123 };
      jest.spyOn(phongbanService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ phongban });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.error('This is an error!');

      // THEN
      expect(phongbanService.update).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).not.toHaveBeenCalled();
    });
  });
});
