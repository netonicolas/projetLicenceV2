import { Routes } from '@angular/router';

import { UserRouteAccessService } from '../../shared';
import { SkillTestComponent } from './skill-test.component';
import { SkillTestDetailComponent } from './skill-test-detail.component';
import { SkillTestPopupComponent } from './skill-test-dialog.component';
import { SkillTestDeletePopupComponent } from './skill-test-delete-dialog.component';

export const skillTestRoute: Routes = [
    {
        path: 'skill-test',
        component: SkillTestComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'SkillTests'
        },
        canActivate: [UserRouteAccessService]
    }, {
        path: 'skill-test/:id',
        component: SkillTestDetailComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'SkillTests'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const skillTestPopupRoute: Routes = [
    {
        path: 'skill-test-new',
        component: SkillTestPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'SkillTests'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'skill-test/:id/edit',
        component: SkillTestPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'SkillTests'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'skill-test/:id/delete',
        component: SkillTestDeletePopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'SkillTests'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
