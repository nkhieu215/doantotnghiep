import { Routes } from '@angular/router';

import { UserRouteAccessService } from 'app/core/auth/user-route-access.service';
import { ASC } from 'app/config/navigation.constants';
import { ThamsotlComponent } from './list/thamsotl.component';
import { ThamsotlDetailComponent } from './detail/thamsotl-detail.component';
import { ThamsotlUpdateComponent } from './update/thamsotl-update.component';
import ThamsotlResolve from './route/thamsotl-routing-resolve.service';

const thamsotlRoute: Routes = [
  {
    path: '',
    component: ThamsotlComponent,
    data: {
      defaultSort: 'id,' + ASC,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: ThamsotlDetailComponent,
    resolve: {
      thamsotl: ThamsotlResolve,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: ThamsotlUpdateComponent,
    resolve: {
      thamsotl: ThamsotlResolve,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: ThamsotlUpdateComponent,
    resolve: {
      thamsotl: ThamsotlResolve,
    },
    canActivate: [UserRouteAccessService],
  },
];

export default thamsotlRoute;
