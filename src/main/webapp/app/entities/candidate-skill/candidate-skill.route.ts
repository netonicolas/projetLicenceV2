import { Routes } from '@angular/router';

import { UserRouteAccessService } from '../../shared';
import { CandidateSkillComponent } from './candidate-skill.component';
import { CandidateSkillDetailComponent } from './candidate-skill-detail.component';
import { CandidateSkillPopupComponent } from './candidate-skill-dialog.component';
import { CandidateSkillDeletePopupComponent } from './candidate-skill-delete-dialog.component';

export const candidateSkillRoute: Routes = [
    {
        path: 'candidate-skill',
        component: CandidateSkillComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'CandidateSkills'
        },
        canActivate: [UserRouteAccessService]
    }, {
        path: 'candidate-skill/:id',
        component: CandidateSkillDetailComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'CandidateSkills'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const candidateSkillPopupRoute: Routes = [
    {
        path: 'candidate-skill-new',
        component: CandidateSkillPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'CandidateSkills'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'candidate-skill/:id/edit',
        component: CandidateSkillPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'CandidateSkills'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'candidate-skill/:id/delete',
        component: CandidateSkillDeletePopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'CandidateSkills'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
