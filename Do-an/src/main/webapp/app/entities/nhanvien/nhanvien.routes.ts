import { Routes } from '@angular/router';

import { UserRouteAccessService } from 'app/core/auth/user-route-access.service';
import { ASC } from 'app/config/navigation.constants';
import { NhanvienComponent } from './list/nhanvien.component';
import { NhanvienDetailComponent } from './detail/nhanvien-detail.component';
import { NhanvienUpdateComponent } from './update/nhanvien-update.component';
import NhanvienResolve from './route/nhanvien-routing-resolve.service';

const nhanvienRoute: Routes = [
  {
    path: '',
    component: NhanvienComponent,
    data: {
      defaultSort: 'id,' + ASC,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: NhanvienDetailComponent,
    resolve: {
      nhanvien: NhanvienResolve,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: NhanvienUpdateComponent,
    resolve: {
      nhanvien: NhanvienResolve,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: NhanvienUpdateComponent,
    resolve: {
      nhanvien: NhanvienResolve,
    },
    canActivate: [UserRouteAccessService],
  },
];

export default nhanvienRoute;
