import { Routes } from '@angular/router';

import { UserRouteAccessService } from 'app/core/auth/user-route-access.service';
import { ASC } from 'app/config/navigation.constants';
import { ChucvuComponent } from './list/chucvu.component';
import { ChucvuDetailComponent } from './detail/chucvu-detail.component';
import { ChucvuUpdateComponent } from './update/chucvu-update.component';
import ChucvuResolve from './route/chucvu-routing-resolve.service';

const chucvuRoute: Routes = [
  {
    path: '',
    component: ChucvuComponent,
    data: {
      defaultSort: 'id,' + ASC,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: ChucvuDetailComponent,
    resolve: {
      chucvu: ChucvuResolve,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: ChucvuUpdateComponent,
    resolve: {
      chucvu: ChucvuResolve,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: ChucvuUpdateComponent,
    resolve: {
      chucvu: ChucvuResolve,
    },
    canActivate: [UserRouteAccessService],
  },
];

export default chucvuRoute;
