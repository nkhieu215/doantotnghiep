import { Routes } from '@angular/router';

import { UserRouteAccessService } from 'app/core/auth/user-route-access.service';
import { ASC } from 'app/config/navigation.constants';
import { BangchamcongComponent } from './list/bangchamcong.component';
import { BangchamcongDetailComponent } from './detail/bangchamcong-detail.component';
import { BangchamcongUpdateComponent } from './update/bangchamcong-update.component';
import BangchamcongResolve from './route/bangchamcong-routing-resolve.service';

const bangchamcongRoute: Routes = [
  {
    path: '',
    component: BangchamcongComponent,
    data: {
      defaultSort: 'id,' + ASC,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: BangchamcongDetailComponent,
    resolve: {
      bangchamcong: BangchamcongResolve,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: BangchamcongUpdateComponent,
    resolve: {
      bangchamcong: BangchamcongResolve,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: BangchamcongUpdateComponent,
    resolve: {
      bangchamcong: BangchamcongResolve,
    },
    canActivate: [UserRouteAccessService],
  },
];

export default bangchamcongRoute;
