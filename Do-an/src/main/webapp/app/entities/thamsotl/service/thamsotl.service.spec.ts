import { TestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';

import { IThamsotl } from '../thamsotl.model';
import { sampleWithRequiredData, sampleWithNewData, sampleWithPartialData, sampleWithFullData } from '../thamsotl.test-samples';

import { ThamsotlService } from './thamsotl.service';

const requireRestSample: IThamsotl = {
  ...sampleWithRequiredData,
};

describe('Thamsotl Service', () => {
  let service: ThamsotlService;
  let httpMock: HttpTestingController;
  let expectedResult: IThamsotl | IThamsotl[] | boolean | null;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule],
    });
    expectedResult = null;
    service = TestBed.inject(ThamsotlService);
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

    it('should create a Thamsotl', () => {
      const thamsotl = { ...sampleWithNewData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.create(thamsotl).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'POST' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should update a Thamsotl', () => {
      const thamsotl = { ...sampleWithRequiredData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.update(thamsotl).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PUT' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should partial update a Thamsotl', () => {
      const patchObject = { ...sampleWithPartialData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.partialUpdate(patchObject).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PATCH' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should return a list of Thamsotl', () => {
      const returnedFromService = { ...requireRestSample };

      const expected = { ...sampleWithRequiredData };

      service.query().subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'GET' });
      req.flush([returnedFromService]);
      httpMock.verify();
      expect(expectedResult).toMatchObject([expected]);
    });

    it('should delete a Thamsotl', () => {
      const expected = true;

      service.delete(123).subscribe(resp => (expectedResult = resp.ok));

      const req = httpMock.expectOne({ method: 'DELETE' });
      req.flush({ status: 200 });
      expect(expectedResult).toBe(expected);
    });

    describe('addThamsotlToCollectionIfMissing', () => {
      it('should add a Thamsotl to an empty array', () => {
        const thamsotl: IThamsotl = sampleWithRequiredData;
        expectedResult = service.addThamsotlToCollectionIfMissing([], thamsotl);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(thamsotl);
      });

      it('should not add a Thamsotl to an array that contains it', () => {
        const thamsotl: IThamsotl = sampleWithRequiredData;
        const thamsotlCollection: IThamsotl[] = [
          {
            ...thamsotl,
          },
          sampleWithPartialData,
        ];
        expectedResult = service.addThamsotlToCollectionIfMissing(thamsotlCollection, thamsotl);
        expect(expectedResult).toHaveLength(2);
      });

      it("should add a Thamsotl to an array that doesn't contain it", () => {
        const thamsotl: IThamsotl = sampleWithRequiredData;
        const thamsotlCollection: IThamsotl[] = [sampleWithPartialData];
        expectedResult = service.addThamsotlToCollectionIfMissing(thamsotlCollection, thamsotl);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(thamsotl);
      });

      it('should add only unique Thamsotl to an array', () => {
        const thamsotlArray: IThamsotl[] = [sampleWithRequiredData, sampleWithPartialData, sampleWithFullData];
        const thamsotlCollection: IThamsotl[] = [sampleWithRequiredData];
        expectedResult = service.addThamsotlToCollectionIfMissing(thamsotlCollection, ...thamsotlArray);
        expect(expectedResult).toHaveLength(3);
      });

      it('should accept varargs', () => {
        const thamsotl: IThamsotl = sampleWithRequiredData;
        const thamsotl2: IThamsotl = sampleWithPartialData;
        expectedResult = service.addThamsotlToCollectionIfMissing([], thamsotl, thamsotl2);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(thamsotl);
        expect(expectedResult).toContain(thamsotl2);
      });

      it('should accept null and undefined values', () => {
        const thamsotl: IThamsotl = sampleWithRequiredData;
        expectedResult = service.addThamsotlToCollectionIfMissing([], null, thamsotl, undefined);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(thamsotl);
      });

      it('should return initial array if no Thamsotl is added', () => {
        const thamsotlCollection: IThamsotl[] = [sampleWithRequiredData];
        expectedResult = service.addThamsotlToCollectionIfMissing(thamsotlCollection, undefined, null);
        expect(expectedResult).toEqual(thamsotlCollection);
      });
    });

    describe('compareThamsotl', () => {
      it('Should return true if both entities are null', () => {
        const entity1 = null;
        const entity2 = null;

        const compareResult = service.compareThamsotl(entity1, entity2);

        expect(compareResult).toEqual(true);
      });

      it('Should return false if one entity is null', () => {
        const entity1 = { id: 123 };
        const entity2 = null;

        const compareResult1 = service.compareThamsotl(entity1, entity2);
        const compareResult2 = service.compareThamsotl(entity2, entity1);

        expect(compareResult1).toEqual(false);
        expect(compareResult2).toEqual(false);
      });

      it('Should return false if primaryKey differs', () => {
        const entity1 = { id: 123 };
        const entity2 = { id: 456 };

        const compareResult1 = service.compareThamsotl(entity1, entity2);
        const compareResult2 = service.compareThamsotl(entity2, entity1);

        expect(compareResult1).toEqual(false);
        expect(compareResult2).toEqual(false);
      });

      it('Should return false if primaryKey matches', () => {
        const entity1 = { id: 123 };
        const entity2 = { id: 123 };

        const compareResult1 = service.compareThamsotl(entity1, entity2);
        const compareResult2 = service.compareThamsotl(entity2, entity1);

        expect(compareResult1).toEqual(true);
        expect(compareResult2).toEqual(true);
      });
    });
  });

  afterEach(() => {
    httpMock.verify();
  });
});
