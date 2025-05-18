jest.mock('@ng-bootstrap/ng-bootstrap');

import { ComponentFixture, TestBed, inject, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { of } from 'rxjs';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';

import { NhanvienService } from '../service/nhanvien.service';

import { NhanvienDeleteDialogComponent } from './nhanvien-delete-dialog.component';

describe('Nhanvien Management Delete Component', () => {
  let comp: NhanvienDeleteDialogComponent;
  let fixture: ComponentFixture<NhanvienDeleteDialogComponent>;
  let service: NhanvienService;
  let mockActiveModal: NgbActiveModal;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule, NhanvienDeleteDialogComponent],
      providers: [NgbActiveModal],
    })
      .overrideTemplate(NhanvienDeleteDialogComponent, '')
      .compileComponents();
    fixture = TestBed.createComponent(NhanvienDeleteDialogComponent);
    comp = fixture.componentInstance;
    service = TestBed.inject(NhanvienService);
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
