import { Routes } from '@angular/router';

import { UserRouteAccessService } from 'app/core/auth/user-route-access.service';
import { ASC } from 'app/config/navigation.constants';
import { PhongbanComponent } from './list/phongban.component';
import { PhongbanDetailComponent } from './detail/phongban-detail.component';
import { PhongbanUpdateComponent } from './update/phongban-update.component';
import PhongbanResolve from './route/phongban-routing-resolve.service';

const phongbanRoute: Routes = [
  {
    path: '',
    component: PhongbanComponent,
    data: {
      defaultSort: 'id,' + ASC,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: PhongbanDetailComponent,
    resolve: {
      phongban: PhongbanResolve,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: PhongbanUpdateComponent,
    resolve: {
      phongban: PhongbanResolve,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: PhongbanUpdateComponent,
    resolve: {
      phongban: PhongbanResolve,
    },
    canActivate: [UserRouteAccessService],
  },
];

export default phongbanRoute;
