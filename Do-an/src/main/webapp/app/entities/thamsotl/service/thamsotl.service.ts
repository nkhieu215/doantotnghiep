import { inject, Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { isPresent } from 'app/core/util/operators';
import { ApplicationConfigService } from 'app/core/config/application-config.service';
import { createRequestOption } from 'app/core/request/request-util';
import { IThamsotl, NewThamsotl } from '../thamsotl.model';

export type PartialUpdateThamsotl = Partial<IThamsotl> & Pick<IThamsotl, 'id'>;

export type EntityResponseType = HttpResponse<IThamsotl>;
export type EntityArrayResponseType = HttpResponse<IThamsotl[]>;

@Injectable({ providedIn: 'root' })
export class ThamsotlService {
  protected http = inject(HttpClient);
  protected applicationConfigService = inject(ApplicationConfigService);

  protected resourceUrl = this.applicationConfigService.getEndpointFor('api/thamsotls');

  create(thamsotl: NewThamsotl): Observable<EntityResponseType> {
    return this.http.post<IThamsotl>(this.resourceUrl, thamsotl, { observe: 'response' });
  }

  update(thamsotl: IThamsotl): Observable<EntityResponseType> {
    return this.http.put<IThamsotl>(`${this.resourceUrl}/${this.getThamsotlIdentifier(thamsotl)}`, thamsotl, { observe: 'response' });
  }

  partialUpdate(thamsotl: PartialUpdateThamsotl): Observable<EntityResponseType> {
    return this.http.patch<IThamsotl>(`${this.resourceUrl}/${this.getThamsotlIdentifier(thamsotl)}`, thamsotl, { observe: 'response' });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<IThamsotl>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IThamsotl[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  getThamsotlIdentifier(thamsotl: Pick<IThamsotl, 'id'>): number {
    return thamsotl.id;
  }

  compareThamsotl(o1: Pick<IThamsotl, 'id'> | null, o2: Pick<IThamsotl, 'id'> | null): boolean {
    return o1 && o2 ? this.getThamsotlIdentifier(o1) === this.getThamsotlIdentifier(o2) : o1 === o2;
  }

  addThamsotlToCollectionIfMissing<Type extends Pick<IThamsotl, 'id'>>(
    thamsotlCollection: Type[],
    ...thamsotlsToCheck: (Type | null | undefined)[]
  ): Type[] {
    const thamsotls: Type[] = thamsotlsToCheck.filter(isPresent);
    if (thamsotls.length > 0) {
      const thamsotlCollectionIdentifiers = thamsotlCollection.map(thamsotlItem => this.getThamsotlIdentifier(thamsotlItem));
      const thamsotlsToAdd = thamsotls.filter(thamsotlItem => {
        const thamsotlIdentifier = this.getThamsotlIdentifier(thamsotlItem);
        if (thamsotlCollectionIdentifiers.includes(thamsotlIdentifier)) {
          return false;
        }
        thamsotlCollectionIdentifiers.push(thamsotlIdentifier);
        return true;
      });
      return [...thamsotlsToAdd, ...thamsotlCollection];
    }
    return thamsotlCollection;
  }
}
