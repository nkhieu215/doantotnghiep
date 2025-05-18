import { inject } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { ActivatedRouteSnapshot, Router } from '@angular/router';
import { of, EMPTY, Observable } from 'rxjs';
import { mergeMap } from 'rxjs/operators';

import { INhanvien } from '../nhanvien.model';
import { NhanvienService } from '../service/nhanvien.service';

const nhanvienResolve = (route: ActivatedRouteSnapshot): Observable<null | INhanvien> => {
  const id = route.params['id'];
  if (id) {
    return inject(NhanvienService)
      .find(id)
      .pipe(
        mergeMap((nhanvien: HttpResponse<INhanvien>) => {
          if (nhanvien.body) {
            return of(nhanvien.body);
          } else {
            inject(Router).navigate(['404']);
            return EMPTY;
          }
        }),
      );
  }
  return of(null);
};

export default nhanvienResolve;
