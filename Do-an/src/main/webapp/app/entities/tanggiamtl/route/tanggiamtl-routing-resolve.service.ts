import { inject } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { ActivatedRouteSnapshot, Router } from '@angular/router';
import { of, EMPTY, Observable } from 'rxjs';
import { mergeMap } from 'rxjs/operators';

import { ITanggiamtl } from '../tanggiamtl.model';
import { TanggiamtlService } from '../service/tanggiamtl.service';

const tanggiamtlResolve = (route: ActivatedRouteSnapshot): Observable<null | ITanggiamtl> => {
  const id = route.params['id'];
  if (id) {
    return inject(TanggiamtlService)
      .find(id)
      .pipe(
        mergeMap((tanggiamtl: HttpResponse<ITanggiamtl>) => {
          if (tanggiamtl.body) {
            return of(tanggiamtl.body);
          } else {
            inject(Router).navigate(['404']);
            return EMPTY;
          }
        }),
      );
  }
  return of(null);
};

export default tanggiamtlResolve;
