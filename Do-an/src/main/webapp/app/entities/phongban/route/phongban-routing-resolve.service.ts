import { inject } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { ActivatedRouteSnapshot, Router } from '@angular/router';
import { of, EMPTY, Observable } from 'rxjs';
import { mergeMap } from 'rxjs/operators';

import { IPhongban } from '../phongban.model';
import { PhongbanService } from '../service/phongban.service';

const phongbanResolve = (route: ActivatedRouteSnapshot): Observable<null | IPhongban> => {
  const id = route.params['id'];
  if (id) {
    return inject(PhongbanService)
      .find(id)
      .pipe(
        mergeMap((phongban: HttpResponse<IPhongban>) => {
          if (phongban.body) {
            return of(phongban.body);
          } else {
            inject(Router).navigate(['404']);
            return EMPTY;
          }
        }),
      );
  }
  return of(null);
};

export default phongbanResolve;
