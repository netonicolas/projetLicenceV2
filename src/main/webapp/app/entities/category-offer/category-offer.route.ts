import { Routes } from '@angular/router';

import { UserRouteAccessService } from '../../shared';
import { CategoryOfferComponent } from './category-offer.component';
import { CategoryOfferDetailComponent } from './category-offer-detail.component';
import { CategoryOfferPopupComponent } from './category-offer-dialog.component';
import { CategoryOfferDeletePopupComponent } from './category-offer-delete-dialog.component';

export const categoryOfferRoute: Routes = [
    {
        path: 'category-offer',
        component: CategoryOfferComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'CategoryOffers'
        },
        canActivate: [UserRouteAccessService]
    }, {
        path: 'category-offer/:id',
        component: CategoryOfferDetailComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'CategoryOffers'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const categoryOfferPopupRoute: Routes = [
    {
        path: 'category-offer-new',
        component: CategoryOfferPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'CategoryOffers'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'category-offer/:id/edit',
        component: CategoryOfferPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'CategoryOffers'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'category-offer/:id/delete',
        component: CategoryOfferDeletePopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'CategoryOffers'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
