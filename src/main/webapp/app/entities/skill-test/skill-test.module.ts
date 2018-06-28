import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { VerifyMyCandidateSharedModule } from '../../shared';
import {
    SkillTestService,
    SkillTestPopupService,
    SkillTestComponent,
    SkillTestDetailComponent,
    SkillTestDialogComponent,
    SkillTestPopupComponent,
    SkillTestDeletePopupComponent,
    SkillTestDeleteDialogComponent,
    skillTestRoute,
    skillTestPopupRoute,
} from './';

const ENTITY_STATES = [
    ...skillTestRoute,
    ...skillTestPopupRoute,
];

@NgModule({
    imports: [
        VerifyMyCandidateSharedModule,
        RouterModule.forChild(ENTITY_STATES)
    ],
    declarations: [
        SkillTestComponent,
        SkillTestDetailComponent,
        SkillTestDialogComponent,
        SkillTestDeleteDialogComponent,
        SkillTestPopupComponent,
        SkillTestDeletePopupComponent,
    ],
    entryComponents: [
        SkillTestComponent,
        SkillTestDialogComponent,
        SkillTestPopupComponent,
        SkillTestDeleteDialogComponent,
        SkillTestDeletePopupComponent,
    ],
    providers: [
        SkillTestService,
        SkillTestPopupService,
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class VerifyMyCandidateSkillTestModule {}
