import { Routes } from '@angular/router';

import { UserRouteAccessService } from '../../shared';
import { SkillTestResponseComponent } from './skill-test-response.component';
import { SkillTestResponseDetailComponent } from './skill-test-response-detail.component';
import { SkillTestResponsePopupComponent } from './skill-test-response-dialog.component';
import { SkillTestResponseDeletePopupComponent } from './skill-test-response-delete-dialog.component';

export const skillTestResponseRoute: Routes = [
    {
        path: 'skill-test-response',
        component: SkillTestResponseComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'SkillTestResponses'
        },
        canActivate: [UserRouteAccessService]
    }, {
        path: 'skill-test-response/:id',
        component: SkillTestResponseDetailComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'SkillTestResponses'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const skillTestResponsePopupRoute: Routes = [
    {
        path: 'skill-test-response-new',
        component: SkillTestResponsePopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'SkillTestResponses'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'skill-test-response/:id/edit',
        component: SkillTestResponsePopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'SkillTestResponses'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'skill-test-response/:id/delete',
        component: SkillTestResponseDeletePopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'SkillTestResponses'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
