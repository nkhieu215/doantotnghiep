import { inject } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { ActivatedRouteSnapshot, Router } from '@angular/router';
import { of, EMPTY, Observable } from 'rxjs';
import { mergeMap } from 'rxjs/operators';

import { IChucvu } from '../chucvu.model';
import { ChucvuService } from '../service/chucvu.service';

const chucvuResolve = (route: ActivatedRouteSnapshot): Observable<null | IChucvu> => {
  const id = route.params['id'];
  if (id) {
    return inject(ChucvuService)
      .find(id)
      .pipe(
        mergeMap((chucvu: HttpResponse<IChucvu>) => {
          if (chucvu.body) {
            return of(chucvu.body);
          } else {
            inject(Router).navigate(['404']);
            return EMPTY;
          }
        }),
      );
  }
  return of(null);
};

export default chucvuResolve;
