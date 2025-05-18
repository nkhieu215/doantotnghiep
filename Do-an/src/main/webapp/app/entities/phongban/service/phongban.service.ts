import { inject, Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { isPresent } from 'app/core/util/operators';
import { ApplicationConfigService } from 'app/core/config/application-config.service';
import { createRequestOption } from 'app/core/request/request-util';
import { IPhongban, NewPhongban } from '../phongban.model';

export type PartialUpdatePhongban = Partial<IPhongban> & Pick<IPhongban, 'id'>;

export type EntityResponseType = HttpResponse<IPhongban>;
export type EntityArrayResponseType = HttpResponse<IPhongban[]>;

@Injectable({ providedIn: 'root' })
export class PhongbanService {
  protected http = inject(HttpClient);
  protected applicationConfigService = inject(ApplicationConfigService);

  protected resourceUrl = this.applicationConfigService.getEndpointFor('api/phongbans');

  create(phongban: NewPhongban): Observable<EntityResponseType> {
    return this.http.post<IPhongban>(this.resourceUrl, phongban, { observe: 'response' });
  }

  update(phongban: IPhongban): Observable<EntityResponseType> {
    return this.http.put<IPhongban>(`${this.resourceUrl}/${this.getPhongbanIdentifier(phongban)}`, phongban, { observe: 'response' });
  }

  partialUpdate(phongban: PartialUpdatePhongban): Observable<EntityResponseType> {
    return this.http.patch<IPhongban>(`${this.resourceUrl}/${this.getPhongbanIdentifier(phongban)}`, phongban, { observe: 'response' });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<IPhongban>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IPhongban[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  getPhongbanIdentifier(phongban: Pick<IPhongban, 'id'>): number {
    return phongban.id;
  }

  comparePhongban(o1: Pick<IPhongban, 'id'> | null, o2: Pick<IPhongban, 'id'> | null): boolean {
    return o1 && o2 ? this.getPhongbanIdentifier(o1) === this.getPhongbanIdentifier(o2) : o1 === o2;
  }

  addPhongbanToCollectionIfMissing<Type extends Pick<IPhongban, 'id'>>(
    phongbanCollection: Type[],
    ...phongbansToCheck: (Type | null | undefined)[]
  ): Type[] {
    const phongbans: Type[] = phongbansToCheck.filter(isPresent);
    if (phongbans.length > 0) {
      const phongbanCollectionIdentifiers = phongbanCollection.map(phongbanItem => this.getPhongbanIdentifier(phongbanItem));
      const phongbansToAdd = phongbans.filter(phongbanItem => {
        const phongbanIdentifier = this.getPhongbanIdentifier(phongbanItem);
        if (phongbanCollectionIdentifiers.includes(phongbanIdentifier)) {
          return false;
        }
        phongbanCollectionIdentifiers.push(phongbanIdentifier);
        return true;
      });
      return [...phongbansToAdd, ...phongbanCollection];
    }
    return phongbanCollection;
  }
}
