import { TestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';

import { IThuetn } from '../thuetn.model';
import { sampleWithRequiredData, sampleWithNewData, sampleWithPartialData, sampleWithFullData } from '../thuetn.test-samples';

import { ThuetnService } from './thuetn.service';

const requireRestSample: IThuetn = {
  ...sampleWithRequiredData,
};

describe('Thuetn Service', () => {
  let service: ThuetnService;
  let httpMock: HttpTestingController;
  let expectedResult: IThuetn | IThuetn[] | boolean | null;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule],
    });
    expectedResult = null;
    service = TestBed.inject(ThuetnService);
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

    it('should create a Thuetn', () => {
      const thuetn = { ...sampleWithNewData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.create(thuetn).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'POST' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should update a Thuetn', () => {
      const thuetn = { ...sampleWithRequiredData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.update(thuetn).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PUT' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should partial update a Thuetn', () => {
      const patchObject = { ...sampleWithPartialData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.partialUpdate(patchObject).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PATCH' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should return a list of Thuetn', () => {
      const returnedFromService = { ...requireRestSample };

      const expected = { ...sampleWithRequiredData };

      service.query().subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'GET' });
      req.flush([returnedFromService]);
      httpMock.verify();
      expect(expectedResult).toMatchObject([expected]);
    });

    it('should delete a Thuetn', () => {
      const expected = true;

      service.delete(123).subscribe(resp => (expectedResult = resp.ok));

      const req = httpMock.expectOne({ method: 'DELETE' });
      req.flush({ status: 200 });
      expect(expectedResult).toBe(expected);
    });

    describe('addThuetnToCollectionIfMissing', () => {
      it('should add a Thuetn to an empty array', () => {
        const thuetn: IThuetn = sampleWithRequiredData;
        expectedResult = service.addThuetnToCollectionIfMissing([], thuetn);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(thuetn);
      });

      it('should not add a Thuetn to an array that contains it', () => {
        const thuetn: IThuetn = sampleWithRequiredData;
        const thuetnCollection: IThuetn[] = [
          {
            ...thuetn,
          },
          sampleWithPartialData,
        ];
        expectedResult = service.addThuetnToCollectionIfMissing(thuetnCollection, thuetn);
        expect(expectedResult).toHaveLength(2);
      });

      it("should add a Thuetn to an array that doesn't contain it", () => {
        const thuetn: IThuetn = sampleWithRequiredData;
        const thuetnCollection: IThuetn[] = [sampleWithPartialData];
        expectedResult = service.addThuetnToCollectionIfMissing(thuetnCollection, thuetn);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(thuetn);
      });

      it('should add only unique Thuetn to an array', () => {
        const thuetnArray: IThuetn[] = [sampleWithRequiredData, sampleWithPartialData, sampleWithFullData];
        const thuetnCollection: IThuetn[] = [sampleWithRequiredData];
        expectedResult = service.addThuetnToCollectionIfMissing(thuetnCollection, ...thuetnArray);
        expect(expectedResult).toHaveLength(3);
      });

      it('should accept varargs', () => {
        const thuetn: IThuetn = sampleWithRequiredData;
        const thuetn2: IThuetn = sampleWithPartialData;
        expectedResult = service.addThuetnToCollectionIfMissing([], thuetn, thuetn2);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(thuetn);
        expect(expectedResult).toContain(thuetn2);
      });

      it('should accept null and undefined values', () => {
        const thuetn: IThuetn = sampleWithRequiredData;
        expectedResult = service.addThuetnToCollectionIfMissing([], null, thuetn, undefined);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(thuetn);
      });

      it('should return initial array if no Thuetn is added', () => {
        const thuetnCollection: IThuetn[] = [sampleWithRequiredData];
        expectedResult = service.addThuetnToCollectionIfMissing(thuetnCollection, undefined, null);
        expect(expectedResult).toEqual(thuetnCollection);
      });
    });

    describe('compareThuetn', () => {
      it('Should return true if both entities are null', () => {
        const entity1 = null;
        const entity2 = null;

        const compareResult = service.compareThuetn(entity1, entity2);

        expect(compareResult).toEqual(true);
      });

      it('Should return false if one entity is null', () => {
        const entity1 = { id: 123 };
        const entity2 = null;

        const compareResult1 = service.compareThuetn(entity1, entity2);
        const compareResult2 = service.compareThuetn(entity2, entity1);

        expect(compareResult1).toEqual(false);
        expect(compareResult2).toEqual(false);
      });

      it('Should return false if primaryKey differs', () => {
        const entity1 = { id: 123 };
        const entity2 = { id: 456 };

        const compareResult1 = service.compareThuetn(entity1, entity2);
        const compareResult2 = service.compareThuetn(entity2, entity1);

        expect(compareResult1).toEqual(false);
        expect(compareResult2).toEqual(false);
      });

      it('Should return false if primaryKey matches', () => {
        const entity1 = { id: 123 };
        const entity2 = { id: 123 };

        const compareResult1 = service.compareThuetn(entity1, entity2);
        const compareResult2 = service.compareThuetn(entity2, entity1);

        expect(compareResult1).toEqual(true);
        expect(compareResult2).toEqual(true);
      });
    });
  });

  afterEach(() => {
    httpMock.verify();
  });
});
