import { Routes } from '@angular/router';

import { UserRouteAccessService } from '../../shared';
import { JobResponseComponent } from './job-response.component';
import { JobResponseDetailComponent } from './job-response-detail.component';
import { JobResponsePopupComponent } from './job-response-dialog.component';
import { JobResponseDeletePopupComponent } from './job-response-delete-dialog.component';

export const jobResponseRoute: Routes = [
    {
        path: 'job-response',
        component: JobResponseComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'JobResponses'
        },
        canActivate: [UserRouteAccessService]
    }, {
        path: 'job-response/:id',
        component: JobResponseDetailComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'JobResponses'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const jobResponsePopupRoute: Routes = [
    {
        path: 'job-response-new',
        component: JobResponsePopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'JobResponses'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'job-response/:id/edit',
        component: JobResponsePopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'JobResponses'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'job-response/:id/delete',
        component: JobResponseDeletePopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'JobResponses'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
