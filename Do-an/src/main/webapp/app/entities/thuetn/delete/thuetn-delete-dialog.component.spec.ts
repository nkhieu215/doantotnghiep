jest.mock('@ng-bootstrap/ng-bootstrap');

import { ComponentFixture, TestBed, inject, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { of } from 'rxjs';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';

import { ThuetnService } from '../service/thuetn.service';

import { ThuetnDeleteDialogComponent } from './thuetn-delete-dialog.component';

describe('Thuetn Management Delete Component', () => {
  let comp: ThuetnDeleteDialogComponent;
  let fixture: ComponentFixture<ThuetnDeleteDialogComponent>;
  let service: ThuetnService;
  let mockActiveModal: NgbActiveModal;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule, ThuetnDeleteDialogComponent],
      providers: [NgbActiveModal],
    })
      .overrideTemplate(ThuetnDeleteDialogComponent, '')
      .compileComponents();
    fixture = TestBed.createComponent(ThuetnDeleteDialogComponent);
    comp = fixture.componentInstance;
    service = TestBed.inject(ThuetnService);
    mockActiveModal = TestBed.inject(NgbActiveModal);
  });

  describe('confirmDelete', () => {
    it('Should call delete service on confirmDelete', inject(
      [],
      fakeAsync(() => {
        // GIVEN
        jest.spyOn(service, 'delete').mockReturnValue(of(new HttpResponse({ body: {} })));

        // WHEN
        comp.confirmDelete(123);
        tick();

        // THEN
        expect(service.delete).toHaveBeenCalledWith(123);
        expect(mockActiveModal.close).toHaveBeenCalledWith('deleted');
      }),
    ));

    it('Should not call delete service on clear', () => {
      // GIVEN
      jest.spyOn(service, 'delete');

      // WHEN
      comp.cancel();

      // THEN
      expect(service.delete).not.toHaveBeenCalled();
      expect(mockActiveModal.close).not.toHaveBeenCalled();
      expect(mockActiveModal.dismiss).toHaveBeenCalled();
    });
  });
});
