import { Routes } from '@angular/router';

import { UserRouteAccessService } from 'app/core/auth/user-route-access.service';
import { ASC } from 'app/config/navigation.constants';
import { TanggiamtlComponent } from './list/tanggiamtl.component';
import { TanggiamtlDetailComponent } from './detail/tanggiamtl-detail.component';
import { TanggiamtlUpdateComponent } from './update/tanggiamtl-update.component';
import TanggiamtlResolve from './route/tanggiamtl-routing-resolve.service';

const tanggiamtlRoute: Routes = [
  {
    path: '',
    component: TanggiamtlComponent,
    data: {
      defaultSort: 'id,' + ASC,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: TanggiamtlDetailComponent,
    resolve: {
      tanggiamtl: TanggiamtlResolve,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: TanggiamtlUpdateComponent,
    resolve: {
      tanggiamtl: TanggiamtlResolve,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: TanggiamtlUpdateComponent,
    resolve: {
      tanggiamtl: TanggiamtlResolve,
    },
    canActivate: [UserRouteAccessService],
  },
];

export default tanggiamtlRoute;
