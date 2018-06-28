import { Routes } from '@angular/router';

import { UserRouteAccessService } from '../../shared';
import { EtudeComponent } from './etude.component';
import { EtudeDetailComponent } from './etude-detail.component';
import { EtudePopupComponent } from './etude-dialog.component';
import { EtudeDeletePopupComponent } from './etude-delete-dialog.component';

export const etudeRoute: Routes = [
    {
        path: 'etude',
        component: EtudeComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'Etudes'
        },
        canActivate: [UserRouteAccessService]
    }, {
        path: 'etude/:id',
        component: EtudeDetailComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'Etudes'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const etudePopupRoute: Routes = [
    {
        path: 'etude-new',
        component: EtudePopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'Etudes'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'etude/:id/edit',
        component: EtudePopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'Etudes'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'etude/:id/delete',
        component: EtudeDeletePopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'Etudes'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
