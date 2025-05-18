import { TestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';

import { IChucvu } from '../chucvu.model';
import { sampleWithRequiredData, sampleWithNewData, sampleWithPartialData, sampleWithFullData } from '../chucvu.test-samples';

import { ChucvuService } from './chucvu.service';

const requireRestSample: IChucvu = {
  ...sampleWithRequiredData,
};

describe('Chucvu Service', () => {
  let service: ChucvuService;
  let httpMock: HttpTestingController;
  let expectedResult: IChucvu | IChucvu[] | boolean | null;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule],
    });
    expectedResult = null;
    service = TestBed.inject(ChucvuService);
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

    it('should create a Chucvu', () => {
      const chucvu = { ...sampleWithNewData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.create(chucvu).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'POST' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should update a Chucvu', () => {
      const chucvu = { ...sampleWithRequiredData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.update(chucvu).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PUT' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should partial update a Chucvu', () => {
      const patchObject = { ...sampleWithPartialData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.partialUpdate(patchObject).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PATCH' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should return a list of Chucvu', () => {
      const returnedFromService = { ...requireRestSample };

      const expected = { ...sampleWithRequiredData };

      service.query().subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'GET' });
      req.flush([returnedFromService]);
      httpMock.verify();
      expect(expectedResult).toMatchObject([expected]);
    });

    it('should delete a Chucvu', () => {
      const expected = true;

      service.delete(123).subscribe(resp => (expectedResult = resp.ok));

      const req = httpMock.expectOne({ method: 'DELETE' });
      req.flush({ status: 200 });
      expect(expectedResult).toBe(expected);
    });

    describe('addChucvuToCollectionIfMissing', () => {
      it('should add a Chucvu to an empty array', () => {
        const chucvu: IChucvu = sampleWithRequiredData;
        expectedResult = service.addChucvuToCollectionIfMissing([], chucvu);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(chucvu);
      });

      it('should not add a Chucvu to an array that contains it', () => {
        const chucvu: IChucvu = sampleWithRequiredData;
        const chucvuCollection: IChucvu[] = [
          {
            ...chucvu,
          },
          sampleWithPartialData,
        ];
        expectedResult = service.addChucvuToCollectionIfMissing(chucvuCollection, chucvu);
        expect(expectedResult).toHaveLength(2);
      });

      it("should add a Chucvu to an array that doesn't contain it", () => {
        const chucvu: IChucvu = sampleWithRequiredData;
        const chucvuCollection: IChucvu[] = [sampleWithPartialData];
        expectedResult = service.addChucvuToCollectionIfMissing(chucvuCollection, chucvu);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(chucvu);
      });

      it('should add only unique Chucvu to an array', () => {
        const chucvuArray: IChucvu[] = [sampleWithRequiredData, sampleWithPartialData, sampleWithFullData];
        const chucvuCollection: IChucvu[] = [sampleWithRequiredData];
        expectedResult = service.addChucvuToCollectionIfMissing(chucvuCollection, ...chucvuArray);
        expect(expectedResult).toHaveLength(3);
      });

      it('should accept varargs', () => {
        const chucvu: IChucvu = sampleWithRequiredData;
        const chucvu2: IChucvu = sampleWithPartialData;
        expectedResult = service.addChucvuToCollectionIfMissing([], chucvu, chucvu2);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(chucvu);
        expect(expectedResult).toContain(chucvu2);
      });

      it('should accept null and undefined values', () => {
        const chucvu: IChucvu = sampleWithRequiredData;
        expectedResult = service.addChucvuToCollectionIfMissing([], null, chucvu, undefined);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(chucvu);
      });

      it('should return initial array if no Chucvu is added', () => {
        const chucvuCollection: IChucvu[] = [sampleWithRequiredData];
        expectedResult = service.addChucvuToCollectionIfMissing(chucvuCollection, undefined, null);
        expect(expectedResult).toEqual(chucvuCollection);
      });
    });

    describe('compareChucvu', () => {
      it('Should return true if both entities are null', () => {
        const entity1 = null;
        const entity2 = null;

        const compareResult = service.compareChucvu(entity1, entity2);

        expect(compareResult).toEqual(true);
      });

      it('Should return false if one entity is null', () => {
        const entity1 = { id: 123 };
        const entity2 = null;

        const compareResult1 = service.compareChucvu(entity1, entity2);
        const compareResult2 = service.compareChucvu(entity2, entity1);

        expect(compareResult1).toEqual(false);
        expect(compareResult2).toEqual(false);
      });

      it('Should return false if primaryKey differs', () => {
        const entity1 = { id: 123 };
        const entity2 = { id: 456 };

        const compareResult1 = service.compareChucvu(entity1, entity2);
        const compareResult2 = service.compareChucvu(entity2, entity1);

        expect(compareResult1).toEqual(false);
        expect(compareResult2).toEqual(false);
      });

      it('Should return false if primaryKey matches', () => {
        const entity1 = { id: 123 };
        const entity2 = { id: 123 };

        const compareResult1 = service.compareChucvu(entity1, entity2);
        const compareResult2 = service.compareChucvu(entity2, entity1);

        expect(compareResult1).toEqual(true);
        expect(compareResult2).toEqual(true);
      });
    });
  });

  afterEach(() => {
    httpMock.verify();
  });
});
