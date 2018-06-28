import { Routes } from '@angular/router';

import { UserRouteAccessService } from '../../shared';
import { SkillQuestionComponent } from './skill-question.component';
import { SkillQuestionDetailComponent } from './skill-question-detail.component';
import { SkillQuestionPopupComponent } from './skill-question-dialog.component';
import { SkillQuestionDeletePopupComponent } from './skill-question-delete-dialog.component';

export const skillQuestionRoute: Routes = [
    {
        path: 'skill-question',
        component: SkillQuestionComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'SkillQuestions'
        },
        canActivate: [UserRouteAccessService]
    }, {
        path: 'skill-question/:id',
        component: SkillQuestionDetailComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'SkillQuestions'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const skillQuestionPopupRoute: Routes = [
    {
        path: 'skill-question-new',
        component: SkillQuestionPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'SkillQuestions'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'skill-question/:id/edit',
        component: SkillQuestionPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'SkillQuestions'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'skill-question/:id/delete',
        component: SkillQuestionDeletePopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'SkillQuestions'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
