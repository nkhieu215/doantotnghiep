import { inject, Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { isPresent } from 'app/core/util/operators';
import { ApplicationConfigService } from 'app/core/config/application-config.service';
import { createRequestOption } from 'app/core/request/request-util';
import { IBangchamcong, NewBangchamcong } from '../bangchamcong.model';

export type PartialUpdateBangchamcong = Partial<IBangchamcong> & Pick<IBangchamcong, 'id'>;

export type EntityResponseType = HttpResponse<IBangchamcong>;
export type EntityArrayResponseType = HttpResponse<IBangchamcong[]>;

@Injectable({ providedIn: 'root' })
export class BangchamcongService {
  protected http = inject(HttpClient);
  protected applicationConfigService = inject(ApplicationConfigService);

  protected resourceUrl = this.applicationConfigService.getEndpointFor('api/bangchamcongs');

  create(bangchamcong: NewBangchamcong): Observable<EntityResponseType> {
    return this.http.post<IBangchamcong>(this.resourceUrl, bangchamcong, { observe: 'response' });
  }

  update(bangchamcong: IBangchamcong): Observable<EntityResponseType> {
    return this.http.put<IBangchamcong>(`${this.resourceUrl}/${this.getBangchamcongIdentifier(bangchamcong)}`, bangchamcong, {
      observe: 'response',
    });
  }

  partialUpdate(bangchamcong: PartialUpdateBangchamcong): Observable<EntityResponseType> {
    return this.http.patch<IBangchamcong>(`${this.resourceUrl}/${this.getBangchamcongIdentifier(bangchamcong)}`, bangchamcong, {
      observe: 'response',
    });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<IBangchamcong>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IBangchamcong[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  getBangchamcongIdentifier(bangchamcong: Pick<IBangchamcong, 'id'>): number {
    return bangchamcong.id;
  }

  compareBangchamcong(o1: Pick<IBangchamcong, 'id'> | null, o2: Pick<IBangchamcong, 'id'> | null): boolean {
    return o1 && o2 ? this.getBangchamcongIdentifier(o1) === this.getBangchamcongIdentifier(o2) : o1 === o2;
  }

  addBangchamcongToCollectionIfMissing<Type extends Pick<IBangchamcong, 'id'>>(
    bangchamcongCollection: Type[],
    ...bangchamcongsToCheck: (Type | null | undefined)[]
  ): Type[] {
    const bangchamcongs: Type[] = bangchamcongsToCheck.filter(isPresent);
    if (bangchamcongs.length > 0) {
      const bangchamcongCollectionIdentifiers = bangchamcongCollection.map(bangchamcongItem =>
        this.getBangchamcongIdentifier(bangchamcongItem),
      );
      const bangchamcongsToAdd = bangchamcongs.filter(bangchamcongItem => {
        const bangchamcongIdentifier = this.getBangchamcongIdentifier(bangchamcongItem);
        if (bangchamcongCollectionIdentifiers.includes(bangchamcongIdentifier)) {
          return false;
        }
        bangchamcongCollectionIdentifiers.push(bangchamcongIdentifier);
        return true;
      });
      return [...bangchamcongsToAdd, ...bangchamcongCollection];
    }
    return bangchamcongCollection;
  }
}
