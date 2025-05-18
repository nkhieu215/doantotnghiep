import { TestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';

import { ITanggiamtl } from '../tanggiamtl.model';
import { sampleWithRequiredData, sampleWithNewData, sampleWithPartialData, sampleWithFullData } from '../tanggiamtl.test-samples';

import { TanggiamtlService } from './tanggiamtl.service';

const requireRestSample: ITanggiamtl = {
  ...sampleWithRequiredData,
};

describe('Tanggiamtl Service', () => {
  let service: TanggiamtlService;
  let httpMock: HttpTestingController;
  let expectedResult: ITanggiamtl | ITanggiamtl[] | boolean | null;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule],
    });
    expectedResult = null;
    service = TestBed.inject(TanggiamtlService);
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

    it('should create a Tanggiamtl', () => {
      const tanggiamtl = { ...sampleWithNewData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.create(tanggiamtl).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'POST' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should update a Tanggiamtl', () => {
      const tanggiamtl = { ...sampleWithRequiredData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.update(tanggiamtl).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PUT' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should partial update a Tanggiamtl', () => {
      const patchObject = { ...sampleWithPartialData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.partialUpdate(patchObject).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PATCH' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should return a list of Tanggiamtl', () => {
      const returnedFromService = { ...requireRestSample };

      const expected = { ...sampleWithRequiredData };

      service.query().subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'GET' });
      req.flush([returnedFromService]);
      httpMock.verify();
      expect(expectedResult).toMatchObject([expected]);
    });

    it('should delete a Tanggiamtl', () => {
      const expected = true;

      service.delete(123).subscribe(resp => (expectedResult = resp.ok));

      const req = httpMock.expectOne({ method: 'DELETE' });
      req.flush({ status: 200 });
      expect(expectedResult).toBe(expected);
    });

    describe('addTanggiamtlToCollectionIfMissing', () => {
      it('should add a Tanggiamtl to an empty array', () => {
        const tanggiamtl: ITanggiamtl = sampleWithRequiredData;
        expectedResult = service.addTanggiamtlToCollectionIfMissing([], tanggiamtl);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(tanggiamtl);
      });

      it('should not add a Tanggiamtl to an array that contains it', () => {
        const tanggiamtl: ITanggiamtl = sampleWithRequiredData;
        const tanggiamtlCollection: ITanggiamtl[] = [
          {
            ...tanggiamtl,
          },
          sampleWithPartialData,
        ];
        expectedResult = service.addTanggiamtlToCollectionIfMissing(tanggiamtlCollection, tanggiamtl);
        expect(expectedResult).toHaveLength(2);
      });

      it("should add a Tanggiamtl to an array that doesn't contain it", () => {
        const tanggiamtl: ITanggiamtl = sampleWithRequiredData;
        const tanggiamtlCollection: ITanggiamtl[] = [sampleWithPartialData];
        expectedResult = service.addTanggiamtlToCollectionIfMissing(tanggiamtlCollection, tanggiamtl);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(tanggiamtl);
      });

      it('should add only unique Tanggiamtl to an array', () => {
        const tanggiamtlArray: ITanggiamtl[] = [sampleWithRequiredData, sampleWithPartialData, sampleWithFullData];
        const tanggiamtlCollection: ITanggiamtl[] = [sampleWithRequiredData];
        expectedResult = service.addTanggiamtlToCollectionIfMissing(tanggiamtlCollection, ...tanggiamtlArray);
        expect(expectedResult).toHaveLength(3);
      });

      it('should accept varargs', () => {
        const tanggiamtl: ITanggiamtl = sampleWithRequiredData;
        const tanggiamtl2: ITanggiamtl = sampleWithPartialData;
        expectedResult = service.addTanggiamtlToCollectionIfMissing([], tanggiamtl, tanggiamtl2);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(tanggiamtl);
        expect(expectedResult).toContain(tanggiamtl2);
      });

      it('should accept null and undefined values', () => {
        const tanggiamtl: ITanggiamtl = sampleWithRequiredData;
        expectedResult = service.addTanggiamtlToCollectionIfMissing([], null, tanggiamtl, undefined);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(tanggiamtl);
      });

      it('should return initial array if no Tanggiamtl is added', () => {
        const tanggiamtlCollection: ITanggiamtl[] = [sampleWithRequiredData];
        expectedResult = service.addTanggiamtlToCollectionIfMissing(tanggiamtlCollection, undefined, null);
        expect(expectedResult).toEqual(tanggiamtlCollection);
      });
    });

    describe('compareTanggiamtl', () => {
      it('Should return true if both entities are null', () => {
        const entity1 = null;
        const entity2 = null;

        const compareResult = service.compareTanggiamtl(entity1, entity2);

        expect(compareResult).toEqual(true);
      });

      it('Should return false if one entity is null', () => {
        const entity1 = { id: 123 };
        const entity2 = null;

        const compareResult1 = service.compareTanggiamtl(entity1, entity2);
        const compareResult2 = service.compareTanggiamtl(entity2, entity1);

        expect(compareResult1).toEqual(false);
        expect(compareResult2).toEqual(false);
      });

      it('Should return false if primaryKey differs', () => {
        const entity1 = { id: 123 };
        const entity2 = { id: 456 };

        const compareResult1 = service.compareTanggiamtl(entity1, entity2);
        const compareResult2 = service.compareTanggiamtl(entity2, entity1);

        expect(compareResult1).toEqual(false);
        expect(compareResult2).toEqual(false);
      });

      it('Should return false if primaryKey matches', () => {
        const entity1 = { id: 123 };
        const entity2 = { id: 123 };

        const compareResult1 = service.compareTanggiamtl(entity1, entity2);
        const compareResult2 = service.compareTanggiamtl(entity2, entity1);

        expect(compareResult1).toEqual(true);
        expect(compareResult2).toEqual(true);
      });
    });
  });

  afterEach(() => {
    httpMock.verify();
  });
});
