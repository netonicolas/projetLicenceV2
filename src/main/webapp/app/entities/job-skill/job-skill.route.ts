import { Routes } from '@angular/router';

import { UserRouteAccessService } from '../../shared';
import { JobSkillComponent } from './job-skill.component';
import { JobSkillDetailComponent } from './job-skill-detail.component';
import { JobSkillPopupComponent } from './job-skill-dialog.component';
import { JobSkillDeletePopupComponent } from './job-skill-delete-dialog.component';

export const jobSkillRoute: Routes = [
    {
        path: 'job-skill',
        component: JobSkillComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'JobSkills'
        },
        canActivate: [UserRouteAccessService]
    }, {
        path: 'job-skill/:id',
        component: JobSkillDetailComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'JobSkills'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const jobSkillPopupRoute: Routes = [
    {
        path: 'job-skill-new',
        component: JobSkillPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'JobSkills'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'job-skill/:id/edit',
        component: JobSkillPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'JobSkills'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'job-skill/:id/delete',
        component: JobSkillDeletePopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'JobSkills'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
