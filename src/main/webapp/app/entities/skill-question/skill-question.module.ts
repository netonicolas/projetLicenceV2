import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { VerifyMyCandidateSharedModule } from '../../shared';
import {
    SkillQuestionService,
    SkillQuestionPopupService,
    SkillQuestionComponent,
    SkillQuestionDetailComponent,
    SkillQuestionDialogComponent,
    SkillQuestionPopupComponent,
    SkillQuestionDeletePopupComponent,
    SkillQuestionDeleteDialogComponent,
    skillQuestionRoute,
    skillQuestionPopupRoute,
} from './';

const ENTITY_STATES = [
    ...skillQuestionRoute,
    ...skillQuestionPopupRoute,
];

@NgModule({
    imports: [
        VerifyMyCandidateSharedModule,
        RouterModule.forChild(ENTITY_STATES)
    ],
    declarations: [
        SkillQuestionComponent,
        SkillQuestionDetailComponent,
        SkillQuestionDialogComponent,
        SkillQuestionDeleteDialogComponent,
        SkillQuestionPopupComponent,
        SkillQuestionDeletePopupComponent,
    ],
    entryComponents: [
        SkillQuestionComponent,
        SkillQuestionDialogComponent,
        SkillQuestionPopupComponent,
        SkillQuestionDeleteDialogComponent,
        SkillQuestionDeletePopupComponent,
    ],
    providers: [
        SkillQuestionService,
        SkillQuestionPopupService,
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class VerifyMyCandidateSkillQuestionModule {}
