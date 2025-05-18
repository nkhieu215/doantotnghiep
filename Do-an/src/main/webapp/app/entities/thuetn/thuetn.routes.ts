import { Routes } from '@angular/router';

import { UserRouteAccessService } from 'app/core/auth/user-route-access.service';
import { ASC } from 'app/config/navigation.constants';
import { ThuetnComponent } from './list/thuetn.component';
import { ThuetnDetailComponent } from './detail/thuetn-detail.component';
import { ThuetnUpdateComponent } from './update/thuetn-update.component';
import ThuetnResolve from './route/thuetn-routing-resolve.service';

const thuetnRoute: Routes = [
  {
    path: '',
    component: ThuetnComponent,
    data: {
      defaultSort: 'id,' + ASC,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: ThuetnDetailComponent,
    resolve: {
      thuetn: ThuetnResolve,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: ThuetnUpdateComponent,
    resolve: {
      thuetn: ThuetnResolve,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: ThuetnUpdateComponent,
    resolve: {
      thuetn: ThuetnResolve,
    },
    canActivate: [UserRouteAccessService],
  },
];

export default thuetnRoute;
