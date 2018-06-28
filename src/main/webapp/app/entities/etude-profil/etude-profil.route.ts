import { Routes } from '@angular/router';

import { UserRouteAccessService } from '../../shared';
import { EtudeProfilComponent } from './etude-profil.component';
import { EtudeProfilDetailComponent } from './etude-profil-detail.component';
import { EtudeProfilPopupComponent } from './etude-profil-dialog.component';
import { EtudeProfilDeletePopupComponent } from './etude-profil-delete-dialog.component';

export const etudeProfilRoute: Routes = [
    {
        path: 'etude-profil',
        component: EtudeProfilComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'EtudeProfils'
        },
        canActivate: [UserRouteAccessService]
    }, {
        path: 'etude-profil/:id',
        component: EtudeProfilDetailComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'EtudeProfils'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const etudeProfilPopupRoute: Routes = [
    {
        path: 'etude-profil-new',
        component: EtudeProfilPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'EtudeProfils'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'etude-profil/:id/edit',
        component: EtudeProfilPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'EtudeProfils'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'etude-profil/:id/delete',
        component: EtudeProfilDeletePopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'EtudeProfils'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
