import { TestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';

import { IPhongban } from '../phongban.model';
import { sampleWithRequiredData, sampleWithNewData, sampleWithPartialData, sampleWithFullData } from '../phongban.test-samples';

import { PhongbanService } from './phongban.service';

const requireRestSample: IPhongban = {
  ...sampleWithRequiredData,
};

describe('Phongban Service', () => {
  let service: PhongbanService;
  let httpMock: HttpTestingController;
  let expectedResult: IPhongban | IPhongban[] | boolean | null;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule],
    });
    expectedResult = null;
    service = TestBed.inject(PhongbanService);
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

    it('should create a Phongban', () => {
      const phongban = { ...sampleWithNewData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.create(phongban).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'POST' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should update a Phongban', () => {
      const phongban = { ...sampleWithRequiredData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.update(phongban).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PUT' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should partial update a Phongban', () => {
      const patchObject = { ...sampleWithPartialData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.partialUpdate(patchObject).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PATCH' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should return a list of Phongban', () => {
      const returnedFromService = { ...requireRestSample };

      const expected = { ...sampleWithRequiredData };

      service.query().subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'GET' });
      req.flush([returnedFromService]);
      httpMock.verify();
      expect(expectedResult).toMatchObject([expected]);
    });

    it('should delete a Phongban', () => {
      const expected = true;

      service.delete(123).subscribe(resp => (expectedResult = resp.ok));

      const req = httpMock.expectOne({ method: 'DELETE' });
      req.flush({ status: 200 });
      expect(expectedResult).toBe(expected);
    });

    describe('addPhongbanToCollectionIfMissing', () => {
      it('should add a Phongban to an empty array', () => {
        const phongban: IPhongban = sampleWithRequiredData;
        expectedResult = service.addPhongbanToCollectionIfMissing([], phongban);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(phongban);
      });

      it('should not add a Phongban to an array that contains it', () => {
        const phongban: IPhongban = sampleWithRequiredData;
        const phongbanCollection: IPhongban[] = [
          {
            ...phongban,
          },
          sampleWithPartialData,
        ];
        expectedResult = service.addPhongbanToCollectionIfMissing(phongbanCollection, phongban);
        expect(expectedResult).toHaveLength(2);
      });

      it("should add a Phongban to an array that doesn't contain it", () => {
        const phongban: IPhongban = sampleWithRequiredData;
        const phongbanCollection: IPhongban[] = [sampleWithPartialData];
        expectedResult = service.addPhongbanToCollectionIfMissing(phongbanCollection, phongban);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(phongban);
      });

      it('should add only unique Phongban to an array', () => {
        const phongbanArray: IPhongban[] = [sampleWithRequiredData, sampleWithPartialData, sampleWithFullData];
        const phongbanCollection: IPhongban[] = [sampleWithRequiredData];
        expectedResult = service.addPhongbanToCollectionIfMissing(phongbanCollection, ...phongbanArray);
        expect(expectedResult).toHaveLength(3);
      });

      it('should accept varargs', () => {
        const phongban: IPhongban = sampleWithRequiredData;
        const phongban2: IPhongban = sampleWithPartialData;
        expectedResult = service.addPhongbanToCollectionIfMissing([], phongban, phongban2);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(phongban);
        expect(expectedResult).toContain(phongban2);
      });

      it('should accept null and undefined values', () => {
        const phongban: IPhongban = sampleWithRequiredData;
        expectedResult = service.addPhongbanToCollectionIfMissing([], null, phongban, undefined);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(phongban);
      });

      it('should return initial array if no Phongban is added', () => {
        const phongbanCollection: IPhongban[] = [sampleWithRequiredData];
        expectedResult = service.addPhongbanToCollectionIfMissing(phongbanCollection, undefined, null);
        expect(expectedResult).toEqual(phongbanCollection);
      });
    });

    describe('comparePhongban', () => {
      it('Should return true if both entities are null', () => {
        const entity1 = null;
        const entity2 = null;

        const compareResult = service.comparePhongban(entity1, entity2);

        expect(compareResult).toEqual(true);
      });

      it('Should return false if one entity is null', () => {
        const entity1 = { id: 123 };
        const entity2 = null;

        const compareResult1 = service.comparePhongban(entity1, entity2);
        const compareResult2 = service.comparePhongban(entity2, entity1);

        expect(compareResult1).toEqual(false);
        expect(compareResult2).toEqual(false);
      });

      it('Should return false if primaryKey differs', () => {
        const entity1 = { id: 123 };
        const entity2 = { id: 456 };

        const compareResult1 = service.comparePhongban(entity1, entity2);
        const compareResult2 = service.comparePhongban(entity2, entity1);

        expect(compareResult1).toEqual(false);
        expect(compareResult2).toEqual(false);
      });

      it('Should return false if primaryKey matches', () => {
        const entity1 = { id: 123 };
        const entity2 = { id: 123 };

        const compareResult1 = service.comparePhongban(entity1, entity2);
        const compareResult2 = service.comparePhongban(entity2, entity1);

        expect(compareResult1).toEqual(true);
        expect(compareResult2).toEqual(true);
      });
    });
  });

  afterEach(() => {
    httpMock.verify();
  });
});
