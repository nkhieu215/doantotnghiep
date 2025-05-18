import { inject } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { ActivatedRouteSnapshot, Router } from '@angular/router';
import { of, EMPTY, Observable } from 'rxjs';
import { mergeMap } from 'rxjs/operators';

import { IThuetn } from '../thuetn.model';
import { ThuetnService } from '../service/thuetn.service';

const thuetnResolve = (route: ActivatedRouteSnapshot): Observable<null | IThuetn> => {
  const id = route.params['id'];
  if (id) {
    return inject(ThuetnService)
      .find(id)
      .pipe(
        mergeMap((thuetn: HttpResponse<IThuetn>) => {
          if (thuetn.body) {
            return of(thuetn.body);
          } else {
            inject(Router).navigate(['404']);
            return EMPTY;
          }
        }),
      );
  }
  return of(null);
};

export default thuetnResolve;
