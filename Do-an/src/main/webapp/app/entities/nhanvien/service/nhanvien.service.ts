import { inject, Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { map, Observable } from 'rxjs';

import dayjs from 'dayjs/esm';

import { isPresent } from 'app/core/util/operators';
import { ApplicationConfigService } from 'app/core/config/application-config.service';
import { createRequestOption } from 'app/core/request/request-util';
import { INhanvien, NewNhanvien } from '../nhanvien.model';

export type PartialUpdateNhanvien = Partial<INhanvien> & Pick<INhanvien, 'id'>;

type RestOf<T extends INhanvien | NewNhanvien> = Omit<T, 'ngaysinh'> & {
  ngaysinh?: string | null;
};

export type RestNhanvien = RestOf<INhanvien>;

export type NewRestNhanvien = RestOf<NewNhanvien>;

export type PartialUpdateRestNhanvien = RestOf<PartialUpdateNhanvien>;

export type EntityResponseType = HttpResponse<INhanvien>;
export type EntityArrayResponseType = HttpResponse<INhanvien[]>;

@Injectable({ providedIn: 'root' })
export class NhanvienService {
  protected http = inject(HttpClient);
  protected applicationConfigService = inject(ApplicationConfigService);

  protected resourceUrl = this.applicationConfigService.getEndpointFor('api/nhanviens');

  create(nhanvien: NewNhanvien): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(nhanvien);
    return this.http
      .post<RestNhanvien>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map(res => this.convertResponseFromServer(res)));
  }

  update(nhanvien: INhanvien): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(nhanvien);
    return this.http
      .put<RestNhanvien>(`${this.resourceUrl}/${this.getNhanvienIdentifier(nhanvien)}`, copy, { observe: 'response' })
      .pipe(map(res => this.convertResponseFromServer(res)));
  }

  partialUpdate(nhanvien: PartialUpdateNhanvien): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(nhanvien);
    return this.http
      .patch<RestNhanvien>(`${this.resourceUrl}/${this.getNhanvienIdentifier(nhanvien)}`, copy, { observe: 'response' })
      .pipe(map(res => this.convertResponseFromServer(res)));
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http
      .get<RestNhanvien>(`${this.resourceUrl}/${id}`, { observe: 'response' })
      .pipe(map(res => this.convertResponseFromServer(res)));
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<RestNhanvien[]>(this.resourceUrl, { params: options, observe: 'response' })
      .pipe(map(res => this.convertResponseArrayFromServer(res)));
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  getNhanvienIdentifier(nhanvien: Pick<INhanvien, 'id'>): number {
    return nhanvien.id;
  }

  compareNhanvien(o1: Pick<INhanvien, 'id'> | null, o2: Pick<INhanvien, 'id'> | null): boolean {
    return o1 && o2 ? this.getNhanvienIdentifier(o1) === this.getNhanvienIdentifier(o2) : o1 === o2;
  }

  addNhanvienToCollectionIfMissing<Type extends Pick<INhanvien, 'id'>>(
    nhanvienCollection: Type[],
    ...nhanviensToCheck: (Type | null | undefined)[]
  ): Type[] {
    const nhanviens: Type[] = nhanviensToCheck.filter(isPresent);
    if (nhanviens.length > 0) {
      const nhanvienCollectionIdentifiers = nhanvienCollection.map(nhanvienItem => this.getNhanvienIdentifier(nhanvienItem));
      const nhanviensToAdd = nhanviens.filter(nhanvienItem => {
        const nhanvienIdentifier = this.getNhanvienIdentifier(nhanvienItem);
        if (nhanvienCollectionIdentifiers.includes(nhanvienIdentifier)) {
          return false;
        }
        nhanvienCollectionIdentifiers.push(nhanvienIdentifier);
        return true;
      });
      return [...nhanviensToAdd, ...nhanvienCollection];
    }
    return nhanvienCollection;
  }

  protected convertDateFromClient<T extends INhanvien | NewNhanvien | PartialUpdateNhanvien>(nhanvien: T): RestOf<T> {
    return {
      ...nhanvien,
      ngaysinh: nhanvien.ngaysinh?.toJSON() ?? null,
    };
  }

  protected convertDateFromServer(restNhanvien: RestNhanvien): INhanvien {
    return {
      ...restNhanvien,
      ngaysinh: restNhanvien.ngaysinh ? dayjs(restNhanvien.ngaysinh) : undefined,
    };
  }

  protected convertResponseFromServer(res: HttpResponse<RestNhanvien>): HttpResponse<INhanvien> {
    return res.clone({
      body: res.body ? this.convertDateFromServer(res.body) : null,
    });
  }

  protected convertResponseArrayFromServer(res: HttpResponse<RestNhanvien[]>): HttpResponse<INhanvien[]> {
    return res.clone({
      body: res.body ? res.body.map(item => this.convertDateFromServer(item)) : null,
    });
  }
}
