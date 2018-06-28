import { Routes } from '@angular/router';

import { UserRouteAccessService } from '../../shared';
import { CandidateTrueSkillComponent } from './candidate-true-skill.component';
import { CandidateTrueSkillDetailComponent } from './candidate-true-skill-detail.component';
import { CandidateTrueSkillPopupComponent } from './candidate-true-skill-dialog.component';
import { CandidateTrueSkillDeletePopupComponent } from './candidate-true-skill-delete-dialog.component';

export const candidateTrueSkillRoute: Routes = [
    {
        path: 'candidate-true-skill',
        component: CandidateTrueSkillComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'CandidateTrueSkills'
        },
        canActivate: [UserRouteAccessService]
    }, {
        path: 'candidate-true-skill/:id',
        component: CandidateTrueSkillDetailComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'CandidateTrueSkills'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const candidateTrueSkillPopupRoute: Routes = [
    {
        path: 'candidate-true-skill-new',
        component: CandidateTrueSkillPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'CandidateTrueSkills'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'candidate-true-skill/:id/edit',
        component: CandidateTrueSkillPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'CandidateTrueSkills'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'candidate-true-skill/:id/delete',
        component: CandidateTrueSkillDeletePopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'CandidateTrueSkills'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
