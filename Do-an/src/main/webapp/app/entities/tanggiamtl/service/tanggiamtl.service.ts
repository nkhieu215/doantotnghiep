import { inject, Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { isPresent } from 'app/core/util/operators';
import { ApplicationConfigService } from 'app/core/config/application-config.service';
import { createRequestOption } from 'app/core/request/request-util';
import { ITanggiamtl, NewTanggiamtl } from '../tanggiamtl.model';

export type PartialUpdateTanggiamtl = Partial<ITanggiamtl> & Pick<ITanggiamtl, 'id'>;

export type EntityResponseType = HttpResponse<ITanggiamtl>;
export type EntityArrayResponseType = HttpResponse<ITanggiamtl[]>;

@Injectable({ providedIn: 'root' })
export class TanggiamtlService {
  protected http = inject(HttpClient);
  protected applicationConfigService = inject(ApplicationConfigService);

  protected resourceUrl = this.applicationConfigService.getEndpointFor('api/tanggiamtls');

  create(tanggiamtl: NewTanggiamtl): Observable<EntityResponseType> {
    return this.http.post<ITanggiamtl>(this.resourceUrl, tanggiamtl, { observe: 'response' });
  }

  update(tanggiamtl: ITanggiamtl): Observable<EntityResponseType> {
    return this.http.put<ITanggiamtl>(`${this.resourceUrl}/${this.getTanggiamtlIdentifier(tanggiamtl)}`, tanggiamtl, {
      observe: 'response',
    });
  }

  partialUpdate(tanggiamtl: PartialUpdateTanggiamtl): Observable<EntityResponseType> {
    return this.http.patch<ITanggiamtl>(`${this.resourceUrl}/${this.getTanggiamtlIdentifier(tanggiamtl)}`, tanggiamtl, {
      observe: 'response',
    });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<ITanggiamtl>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<ITanggiamtl[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  getTanggiamtlIdentifier(tanggiamtl: Pick<ITanggiamtl, 'id'>): number {
    return tanggiamtl.id;
  }

  compareTanggiamtl(o1: Pick<ITanggiamtl, 'id'> | null, o2: Pick<ITanggiamtl, 'id'> | null): boolean {
    return o1 && o2 ? this.getTanggiamtlIdentifier(o1) === this.getTanggiamtlIdentifier(o2) : o1 === o2;
  }

  addTanggiamtlToCollectionIfMissing<Type extends Pick<ITanggiamtl, 'id'>>(
    tanggiamtlCollection: Type[],
    ...tanggiamtlsToCheck: (Type | null | undefined)[]
  ): Type[] {
    const tanggiamtls: Type[] = tanggiamtlsToCheck.filter(isPresent);
    if (tanggiamtls.length > 0) {
      const tanggiamtlCollectionIdentifiers = tanggiamtlCollection.map(tanggiamtlItem => this.getTanggiamtlIdentifier(tanggiamtlItem));
      const tanggiamtlsToAdd = tanggiamtls.filter(tanggiamtlItem => {
        const tanggiamtlIdentifier = this.getTanggiamtlIdentifier(tanggiamtlItem);
        if (tanggiamtlCollectionIdentifiers.includes(tanggiamtlIdentifier)) {
          return false;
        }
        tanggiamtlCollectionIdentifiers.push(tanggiamtlIdentifier);
        return true;
      });
      return [...tanggiamtlsToAdd, ...tanggiamtlCollection];
    }
    return tanggiamtlCollection;
  }
}
