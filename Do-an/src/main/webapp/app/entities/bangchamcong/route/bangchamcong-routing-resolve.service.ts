import { inject } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { ActivatedRouteSnapshot, Router } from '@angular/router';
import { of, EMPTY, Observable } from 'rxjs';
import { mergeMap } from 'rxjs/operators';

import { IBangchamcong } from '../bangchamcong.model';
import { BangchamcongService } from '../service/bangchamcong.service';

const bangchamcongResolve = (route: ActivatedRouteSnapshot): Observable<null | IBangchamcong> => {
  const id = route.params['id'];
  if (id) {
    return inject(BangchamcongService)
      .find(id)
      .pipe(
        mergeMap((bangchamcong: HttpResponse<IBangchamcong>) => {
          if (bangchamcong.body) {
            return of(bangchamcong.body);
          } else {
            inject(Router).navigate(['404']);
            return EMPTY;
          }
        }),
      );
  }
  return of(null);
};

export default bangchamcongResolve;
