import { inject } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { ActivatedRouteSnapshot, Router } from '@angular/router';
import { of, EMPTY, Observable } from 'rxjs';
import { mergeMap } from 'rxjs/operators';

import { IThamsotl } from '../thamsotl.model';
import { ThamsotlService } from '../service/thamsotl.service';

const thamsotlResolve = (route: ActivatedRouteSnapshot): Observable<null | IThamsotl> => {
  const id = route.params['id'];
  if (id) {
    return inject(ThamsotlService)
      .find(id)
      .pipe(
        mergeMap((thamsotl: HttpResponse<IThamsotl>) => {
          if (thamsotl.body) {
            return of(thamsotl.body);
          } else {
            inject(Router).navigate(['404']);
            return EMPTY;
          }
        }),
      );
  }
  return of(null);
};

export default thamsotlResolve;
