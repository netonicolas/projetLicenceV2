import { Routes } from '@angular/router';

import { UserRouteAccessService } from '../../shared';
import { JobOfferComponent } from './job-offer.component';
import { JobOfferDetailComponent } from './job-offer-detail.component';
import { JobOfferPopupComponent } from './job-offer-dialog.component';
import { JobOfferDeletePopupComponent } from './job-offer-delete-dialog.component';

export const jobOfferRoute: Routes = [
    {
        path: 'job-offer',
        component: JobOfferComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'JobOffers'
        },
        canActivate: [UserRouteAccessService]
    }, {
        path: 'job-offer/:id',
        component: JobOfferDetailComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'JobOffers'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const jobOfferPopupRoute: Routes = [
    {
        path: 'job-offer-new',
        component: JobOfferPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'JobOffers'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'job-offer/:id/edit',
        component: JobOfferPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'JobOffers'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'job-offer/:id/delete',
        component: JobOfferDeletePopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'JobOffers'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
