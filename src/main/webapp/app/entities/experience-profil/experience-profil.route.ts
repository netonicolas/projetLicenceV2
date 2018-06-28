import { Routes } from '@angular/router';

import { UserRouteAccessService } from '../../shared';
import { ExperienceProfilComponent } from './experience-profil.component';
import { ExperienceProfilDetailComponent } from './experience-profil-detail.component';
import { ExperienceProfilPopupComponent } from './experience-profil-dialog.component';
import { ExperienceProfilDeletePopupComponent } from './experience-profil-delete-dialog.component';

export const experienceProfilRoute: Routes = [
    {
        path: 'experience-profil',
        component: ExperienceProfilComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'ExperienceProfils'
        },
        canActivate: [UserRouteAccessService]
    }, {
        path: 'experience-profil/:id',
        component: ExperienceProfilDetailComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'ExperienceProfils'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const experienceProfilPopupRoute: Routes = [
    {
        path: 'experience-profil-new',
        component: ExperienceProfilPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'ExperienceProfils'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'experience-profil/:id/edit',
        component: ExperienceProfilPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'ExperienceProfils'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'experience-profil/:id/delete',
        component: ExperienceProfilDeletePopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'ExperienceProfils'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
