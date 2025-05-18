import { inject, Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { isPresent } from 'app/core/util/operators';
import { ApplicationConfigService } from 'app/core/config/application-config.service';
import { createRequestOption } from 'app/core/request/request-util';
import { IThuetn, NewThuetn } from '../thuetn.model';

export type PartialUpdateThuetn = Partial<IThuetn> & Pick<IThuetn, 'id'>;

export type EntityResponseType = HttpResponse<IThuetn>;
export type EntityArrayResponseType = HttpResponse<IThuetn[]>;

@Injectable({ providedIn: 'root' })
export class ThuetnService {
  protected http = inject(HttpClient);
  protected applicationConfigService = inject(ApplicationConfigService);

  protected resourceUrl = this.applicationConfigService.getEndpointFor('api/thuetns');

  create(thuetn: NewThuetn): Observable<EntityResponseType> {
    return this.http.post<IThuetn>(this.resourceUrl, thuetn, { observe: 'response' });
  }

  update(thuetn: IThuetn): Observable<EntityResponseType> {
    return this.http.put<IThuetn>(`${this.resourceUrl}/${this.getThuetnIdentifier(thuetn)}`, thuetn, { observe: 'response' });
  }

  partialUpdate(thuetn: PartialUpdateThuetn): Observable<EntityResponseType> {
    return this.http.patch<IThuetn>(`${this.resourceUrl}/${this.getThuetnIdentifier(thuetn)}`, thuetn, { observe: 'response' });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<IThuetn>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IThuetn[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  getThuetnIdentifier(thuetn: Pick<IThuetn, 'id'>): number {
    return thuetn.id;
  }

  compareThuetn(o1: Pick<IThuetn, 'id'> | null, o2: Pick<IThuetn, 'id'> | null): boolean {
    return o1 && o2 ? this.getThuetnIdentifier(o1) === this.getThuetnIdentifier(o2) : o1 === o2;
  }

  addThuetnToCollectionIfMissing<Type extends Pick<IThuetn, 'id'>>(
    thuetnCollection: Type[],
    ...thuetnsToCheck: (Type | null | undefined)[]
  ): Type[] {
    const thuetns: Type[] = thuetnsToCheck.filter(isPresent);
    if (thuetns.length > 0) {
      const thuetnCollectionIdentifiers = thuetnCollection.map(thuetnItem => this.getThuetnIdentifier(thuetnItem));
      const thuetnsToAdd = thuetns.filter(thuetnItem => {
        const thuetnIdentifier = this.getThuetnIdentifier(thuetnItem);
        if (thuetnCollectionIdentifiers.includes(thuetnIdentifier)) {
          return false;
        }
        thuetnCollectionIdentifiers.push(thuetnIdentifier);
        return true;
      });
      return [...thuetnsToAdd, ...thuetnCollection];
    }
    return thuetnCollection;
  }
}
