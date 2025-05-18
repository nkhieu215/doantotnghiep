import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { RouterTestingModule } from '@angular/router/testing';
import { of, Subject, from } from 'rxjs';

import { ChucvuService } from '../service/chucvu.service';
import { IChucvu } from '../chucvu.model';
import { ChucvuFormService } from './chucvu-form.service';

import { ChucvuUpdateComponent } from './chucvu-update.component';

describe('Chucvu Management Update Component', () => {
  let comp: ChucvuUpdateComponent;
  let fixture: ComponentFixture<ChucvuUpdateComponent>;
  let activatedRoute: ActivatedRoute;
  let chucvuFormService: ChucvuFormService;
  let chucvuService: ChucvuService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule, RouterTestingModule.withRoutes([]), ChucvuUpdateComponent],
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
      .overrideTemplate(ChucvuUpdateComponent, '')
      .compileComponents();

    fixture = TestBed.createComponent(ChucvuUpdateComponent);
    activatedRoute = TestBed.inject(ActivatedRoute);
    chucvuFormService = TestBed.inject(ChucvuFormService);
    chucvuService = TestBed.inject(ChucvuService);

    comp = fixture.componentInstance;
  });

  describe('ngOnInit', () => {
    it('Should update editForm', () => {
      const chucvu: IChucvu = { id: 456 };

      activatedRoute.data = of({ chucvu });
      comp.ngOnInit();

      expect(comp.chucvu).toEqual(chucvu);
    });
  });

  describe('save', () => {
    it('Should call update service on save for existing entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IChucvu>>();
      const chucvu = { id: 123 };
      jest.spyOn(chucvuFormService, 'getChucvu').mockReturnValue(chucvu);
      jest.spyOn(chucvuService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ chucvu });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: chucvu }));
      saveSubject.complete();

      // THEN
      expect(chucvuFormService.getChucvu).toHaveBeenCalled();
      expect(comp.previousState).toHaveBeenCalled();
      expect(chucvuService.update).toHaveBeenCalledWith(expect.objectContaining(chucvu));
      expect(comp.isSaving).toEqual(false);
    });

    it('Should call create service on save for new entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IChucvu>>();
      const chucvu = { id: 123 };
      jest.spyOn(chucvuFormService, 'getChucvu').mockReturnValue({ id: null });
      jest.spyOn(chucvuService, 'create').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ chucvu: null });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: chucvu }));
      saveSubject.complete();

      // THEN
      expect(chucvuFormService.getChucvu).toHaveBeenCalled();
      expect(chucvuService.create).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).toHaveBeenCalled();
    });

    it('Should set isSaving to false on error', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IChucvu>>();
      const chucvu = { id: 123 };
      jest.spyOn(chucvuService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ chucvu });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.error('This is an error!');

      // THEN
      expect(chucvuService.update).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).not.toHaveBeenCalled();
    });
  });
});
