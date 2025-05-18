import { TestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';

import { INhanvien } from '../nhanvien.model';
import { sampleWithRequiredData, sampleWithNewData, sampleWithPartialData, sampleWithFullData } from '../nhanvien.test-samples';

import { NhanvienService, RestNhanvien } from './nhanvien.service';

const requireRestSample: RestNhanvien = {
  ...sampleWithRequiredData,
  ngaysinh: sampleWithRequiredData.ngaysinh?.toJSON(),
};

describe('Nhanvien Service', () => {
  let service: NhanvienService;
  let httpMock: HttpTestingController;
  let expectedResult: INhanvien | INhanvien[] | boolean | null;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule],
    });
    expectedResult = null;
    service = TestBed.inject(NhanvienService);
    httpMock = TestBed.inject(HttpTestingController);
  });

  describe('Service methods', () => {
    it('should find an element', () => {
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.find(123).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'GET' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should create a Nhanvien', () => {
      const nhanvien = { ...sampleWithNewData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.create(nhanvien).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'POST' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should update a Nhanvien', () => {
      const nhanvien = { ...sampleWithRequiredData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.update(nhanvien).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PUT' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should partial update a Nhanvien', () => {
      const patchObject = { ...sampleWithPartialData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.partialUpdate(patchObject).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PATCH' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should return a list of Nhanvien', () => {
      const returnedFromService = { ...requireRestSample };

      const expected = { ...sampleWithRequiredData };

      service.query().subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'GET' });
      req.flush([returnedFromService]);
      httpMock.verify();
      expect(expectedResult).toMatchObject([expected]);
    });

    it('should delete a Nhanvien', () => {
      const expected = true;

      service.delete(123).subscribe(resp => (expectedResult = resp.ok));

      const req = httpMock.expectOne({ method: 'DELETE' });
      req.flush({ status: 200 });
      expect(expectedResult).toBe(expected);
    });

    describe('addNhanvienToCollectionIfMissing', () => {
      it('should add a Nhanvien to an empty array', () => {
        const nhanvien: INhanvien = sampleWithRequiredData;
        expectedResult = service.addNhanvienToCollectionIfMissing([], nhanvien);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(nhanvien);
      });

      it('should not add a Nhanvien to an array that contains it', () => {
        const nhanvien: INhanvien = sampleWithRequiredData;
        const nhanvienCollection: INhanvien[] = [
          {
            ...nhanvien,
          },
          sampleWithPartialData,
        ];
        expectedResult = service.addNhanvienToCollectionIfMissing(nhanvienCollection, nhanvien);
        expect(expectedResult).toHaveLength(2);
      });

      it("should add a Nhanvien to an array that doesn't contain it", () => {
        const nhanvien: INhanvien = sampleWithRequiredData;
        const nhanvienCollection: INhanvien[] = [sampleWithPartialData];
        expectedResult = service.addNhanvienToCollectionIfMissing(nhanvienCollection, nhanvien);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(nhanvien);
      });

      it('should add only unique Nhanvien to an array', () => {
        const nhanvienArray: INhanvien[] = [sampleWithRequiredData, sampleWithPartialData, sampleWithFullData];
        const nhanvienCollection: INhanvien[] = [sampleWithRequiredData];
        expectedResult = service.addNhanvienToCollectionIfMissing(nhanvienCollection, ...nhanvienArray);
        expect(expectedResult).toHaveLength(3);
      });

      it('should accept varargs', () => {
        const nhanvien: INhanvien = sampleWithRequiredData;
        const nhanvien2: INhanvien = sampleWithPartialData;
        expectedResult = service.addNhanvienToCollectionIfMissing([], nhanvien, nhanvien2);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(nhanvien);
        expect(expectedResult).toContain(nhanvien2);
      });

      it('should accept null and undefined values', () => {
        const nhanvien: INhanvien = sampleWithRequiredData;
        expectedResult = service.addNhanvienToCollectionIfMissing([], null, nhanvien, undefined);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(nhanvien);
      });

      it('should return initial array if no Nhanvien is added', () => {
        const nhanvienCollection: INhanvien[] = [sampleWithRequiredData];
        expectedResult = service.addNhanvienToCollectionIfMissing(nhanvienCollection, undefined, null);
        expect(expectedResult).toEqual(nhanvienCollection);
      });
    });

    describe('compareNhanvien', () => {
      it('Should return true if both entities are null', () => {
        const entity1 = null;
        const entity2 = null;

        const compareResult = service.compareNhanvien(entity1, entity2);

        expect(compareResult).toEqual(true);
      });

      it('Should return false if one entity is null', () => {
        const entity1 = { id: 123 };
        const entity2 = null;

        const compareResult1 = service.compareNhanvien(entity1, entity2);
        const compareResult2 = service.compareNhanvien(entity2, entity1);

        expect(compareResult1).toEqual(false);
        expect(compareResult2).toEqual(false);
      });

      it('Should return false if primaryKey differs', () => {
        const entity1 = { id: 123 };
        const entity2 = { id: 456 };

        const compareResult1 = service.compareNhanvien(entity1, entity2);
        const compareResult2 = service.compareNhanvien(entity2, entity1);

        expect(compareResult1).toEqual(false);
        expect(compareResult2).toEqual(false);
      });

      it('Should return false if primaryKey matches', () => {
        const entity1 = { id: 123 };
        const entity2 = { id: 123 };

        const compareResult1 = service.compareNhanvien(entity1, entity2);
        const compareResult2 = service.compareNhanvien(entity2, entity1);

        expect(compareResult1).toEqual(true);
        expect(compareResult2).toEqual(true);
      });
    });
  });

  afterEach(() => {
    httpMock.verify();
  });
});
