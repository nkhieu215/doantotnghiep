import { TestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';

import { IBangchamcong } from '../bangchamcong.model';
import { sampleWithRequiredData, sampleWithNewData, sampleWithPartialData, sampleWithFullData } from '../bangchamcong.test-samples';

import { BangchamcongService } from './bangchamcong.service';

const requireRestSample: IBangchamcong = {
  ...sampleWithRequiredData,
};

describe('Bangchamcong Service', () => {
  let service: BangchamcongService;
  let httpMock: HttpTestingController;
  let expectedResult: IBangchamcong | IBangchamcong[] | boolean | null;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule],
    });
    expectedResult = null;
    service = TestBed.inject(BangchamcongService);
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

    it('should create a Bangchamcong', () => {
      const bangchamcong = { ...sampleWithNewData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.create(bangchamcong).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'POST' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should update a Bangchamcong', () => {
      const bangchamcong = { ...sampleWithRequiredData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.update(bangchamcong).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PUT' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should partial update a Bangchamcong', () => {
      const patchObject = { ...sampleWithPartialData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.partialUpdate(patchObject).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PATCH' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should return a list of Bangchamcong', () => {
      const returnedFromService = { ...requireRestSample };

      const expected = { ...sampleWithRequiredData };

      service.query().subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'GET' });
      req.flush([returnedFromService]);
      httpMock.verify();
      expect(expectedResult).toMatchObject([expected]);
    });

    it('should delete a Bangchamcong', () => {
      const expected = true;

      service.delete(123).subscribe(resp => (expectedResult = resp.ok));

      const req = httpMock.expectOne({ method: 'DELETE' });
      req.flush({ status: 200 });
      expect(expectedResult).toBe(expected);
    });

    describe('addBangchamcongToCollectionIfMissing', () => {
      it('should add a Bangchamcong to an empty array', () => {
        const bangchamcong: IBangchamcong = sampleWithRequiredData;
        expectedResult = service.addBangchamcongToCollectionIfMissing([], bangchamcong);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(bangchamcong);
      });

      it('should not add a Bangchamcong to an array that contains it', () => {
        const bangchamcong: IBangchamcong = sampleWithRequiredData;
        const bangchamcongCollection: IBangchamcong[] = [
          {
            ...bangchamcong,
          },
          sampleWithPartialData,
        ];
        expectedResult = service.addBangchamcongToCollectionIfMissing(bangchamcongCollection, bangchamcong);
        expect(expectedResult).toHaveLength(2);
      });

      it("should add a Bangchamcong to an array that doesn't contain it", () => {
        const bangchamcong: IBangchamcong = sampleWithRequiredData;
        const bangchamcongCollection: IBangchamcong[] = [sampleWithPartialData];
        expectedResult = service.addBangchamcongToCollectionIfMissing(bangchamcongCollection, bangchamcong);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(bangchamcong);
      });

      it('should add only unique Bangchamcong to an array', () => {
        const bangchamcongArray: IBangchamcong[] = [sampleWithRequiredData, sampleWithPartialData, sampleWithFullData];
        const bangchamcongCollection: IBangchamcong[] = [sampleWithRequiredData];
        expectedResult = service.addBangchamcongToCollectionIfMissing(bangchamcongCollection, ...bangchamcongArray);
        expect(expectedResult).toHaveLength(3);
      });

      it('should accept varargs', () => {
        const bangchamcong: IBangchamcong = sampleWithRequiredData;
        const bangchamcong2: IBangchamcong = sampleWithPartialData;
        expectedResult = service.addBangchamcongToCollectionIfMissing([], bangchamcong, bangchamcong2);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(bangchamcong);
        expect(expectedResult).toContain(bangchamcong2);
      });

      it('should accept null and undefined values', () => {
        const bangchamcong: IBangchamcong = sampleWithRequiredData;
        expectedResult = service.addBangchamcongToCollectionIfMissing([], null, bangchamcong, undefined);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(bangchamcong);
      });

      it('should return initial array if no Bangchamcong is added', () => {
        const bangchamcongCollection: IBangchamcong[] = [sampleWithRequiredData];
        expectedResult = service.addBangchamcongToCollectionIfMissing(bangchamcongCollection, undefined, null);
        expect(expectedResult).toEqual(bangchamcongCollection);
      });
    });

    describe('compareBangchamcong', () => {
      it('Should return true if both entities are null', () => {
        const entity1 = null;
        const entity2 = null;

        const compareResult = service.compareBangchamcong(entity1, entity2);

        expect(compareResult).toEqual(true);
      });

      it('Should return false if one entity is null', () => {
        const entity1 = { id: 123 };
        const entity2 = null;

        const compareResult1 = service.compareBangchamcong(entity1, entity2);
        const compareResult2 = service.compareBangchamcong(entity2, entity1);

        expect(compareResult1).toEqual(false);
        expect(compareResult2).toEqual(false);
      });

      it('Should return false if primaryKey differs', () => {
        const entity1 = { id: 123 };
        const entity2 = { id: 456 };

        const compareResult1 = service.compareBangchamcong(entity1, entity2);
        const compareResult2 = service.compareBangchamcong(entity2, entity1);

        expect(compareResult1).toEqual(false);
        expect(compareResult2).toEqual(false);
      });

      it('Should return false if primaryKey matches', () => {
        const entity1 = { id: 123 };
        const entity2 = { id: 123 };

        const compareResult1 = service.compareBangchamcong(entity1, entity2);
        const compareResult2 = service.compareBangchamcong(entity2, entity1);

        expect(compareResult1).toEqual(true);
        expect(compareResult2).toEqual(true);
      });
    });
  });

  afterEach(() => {
    httpMock.verify();
  });
});
