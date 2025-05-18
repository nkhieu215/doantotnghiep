import { inject, Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { isPresent } from 'app/core/util/operators';
import { ApplicationConfigService } from 'app/core/config/application-config.service';
import { createRequestOption } from 'app/core/request/request-util';
import { IChucvu, NewChucvu } from '../chucvu.model';

export type PartialUpdateChucvu = Partial<IChucvu> & Pick<IChucvu, 'id'>;

export type EntityResponseType = HttpResponse<IChucvu>;
export type EntityArrayResponseType = HttpResponse<IChucvu[]>;

@Injectable({ providedIn: 'root' })
export class ChucvuService {
  protected http = inject(HttpClient);
  protected applicationConfigService = inject(ApplicationConfigService);

  protected resourceUrl = this.applicationConfigService.getEndpointFor('api/chucvus');

  create(chucvu: NewChucvu): Observable<EntityResponseType> {
    return this.http.post<IChucvu>(this.resourceUrl, chucvu, { observe: 'response' });
  }

  update(chucvu: IChucvu): Observable<EntityResponseType> {
    return this.http.put<IChucvu>(`${this.resourceUrl}/${this.getChucvuIdentifier(chucvu)}`, chucvu, { observe: 'response' });
  }

  partialUpdate(chucvu: PartialUpdateChucvu): Observable<EntityResponseType> {
    return this.http.patch<IChucvu>(`${this.resourceUrl}/${this.getChucvuIdentifier(chucvu)}`, chucvu, { observe: 'response' });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<IChucvu>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IChucvu[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  getChucvuIdentifier(chucvu: Pick<IChucvu, 'id'>): number {
    return chucvu.id;
  }

  compareChucvu(o1: Pick<IChucvu, 'id'> | null, o2: Pick<IChucvu, 'id'> | null): boolean {
    return o1 && o2 ? this.getChucvuIdentifier(o1) === this.getChucvuIdentifier(o2) : o1 === o2;
  }

  addChucvuToCollectionIfMissing<Type extends Pick<IChucvu, 'id'>>(
    chucvuCollection: Type[],
    ...chucvusToCheck: (Type | null | undefined)[]
  ): Type[] {
    const chucvus: Type[] = chucvusToCheck.filter(isPresent);
    if (chucvus.length > 0) {
      const chucvuCollectionIdentifiers = chucvuCollection.map(chucvuItem => this.getChucvuIdentifier(chucvuItem));
      const chucvusToAdd = chucvus.filter(chucvuItem => {
        const chucvuIdentifier = this.getChucvuIdentifier(chucvuItem);
        if (chucvuCollectionIdentifiers.includes(chucvuIdentifier)) {
          return false;
        }
        chucvuCollectionIdentifiers.push(chucvuIdentifier);
        return true;
      });
      return [...chucvusToAdd, ...chucvuCollection];
    }
    return chucvuCollection;
  }
}
