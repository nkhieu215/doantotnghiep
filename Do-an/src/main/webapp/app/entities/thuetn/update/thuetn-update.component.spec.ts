import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { RouterTestingModule } from '@angular/router/testing';
import { of, Subject, from } from 'rxjs';

import { ThuetnService } from '../service/thuetn.service';
import { IThuetn } from '../thuetn.model';
import { ThuetnFormService } from './thuetn-form.service';

import { ThuetnUpdateComponent } from './thuetn-update.component';

describe('Thuetn Management Update Component', () => {
  let comp: ThuetnUpdateComponent;
  let fixture: ComponentFixture<ThuetnUpdateComponent>;
  let activatedRoute: ActivatedRoute;
  let thuetnFormService: ThuetnFormService;
  let thuetnService: ThuetnService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule, RouterTestingModule.withRoutes([]), ThuetnUpdateComponent],
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
      .overrideTemplate(ThuetnUpdateComponent, '')
      .compileComponents();

    fixture = TestBed.createComponent(ThuetnUpdateComponent);
    activatedRoute = TestBed.inject(ActivatedRoute);
    thuetnFormService = TestBed.inject(ThuetnFormService);
    thuetnService = TestBed.inject(ThuetnService);

    comp = fixture.componentInstance;
  });

  describe('ngOnInit', () => {
    it('Should update editForm', () => {
      const thuetn: IThuetn = { id: 456 };

      activatedRoute.data = of({ thuetn });
      comp.ngOnInit();

      expect(comp.thuetn).toEqual(thuetn);
    });
  });

  describe('save', () => {
    it('Should call update service on save for existing entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IThuetn>>();
      const thuetn = { id: 123 };
      jest.spyOn(thuetnFormService, 'getThuetn').mockReturnValue(thuetn);
      jest.spyOn(thuetnService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ thuetn });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: thuetn }));
      saveSubject.complete();

      // THEN
      expect(thuetnFormService.getThuetn).toHaveBeenCalled();
      expect(comp.previousState).toHaveBeenCalled();
      expect(thuetnService.update).toHaveBeenCalledWith(expect.objectContaining(thuetn));
      expect(comp.isSaving).toEqual(false);
    });

    it('Should call create service on save for new entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IThuetn>>();
      const thuetn = { id: 123 };
      jest.spyOn(thuetnFormService, 'getThuetn').mockReturnValue({ id: null });
      jest.spyOn(thuetnService, 'create').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ thuetn: null });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: thuetn }));
      saveSubject.complete();

      // THEN
      expect(thuetnFormService.getThuetn).toHaveBeenCalled();
      expect(thuetnService.create).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).toHaveBeenCalled();
    });

    it('Should set isSaving to false on error', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IThuetn>>();
      const thuetn = { id: 123 };
      jest.spyOn(thuetnService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ thuetn });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.error('This is an error!');

      // THEN
      expect(thuetnService.update).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).not.toHaveBeenCalled();
    });
  });
});
