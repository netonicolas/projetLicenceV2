import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { VerifyMyCandidateSharedModule } from '../../shared';
import {
    SkillTestResponseService,
    SkillTestResponsePopupService,
    SkillTestResponseComponent,
    SkillTestResponseDetailComponent,
    SkillTestResponseDialogComponent,
    SkillTestResponsePopupComponent,
    SkillTestResponseDeletePopupComponent,
    SkillTestResponseDeleteDialogComponent,
    skillTestResponseRoute,
    skillTestResponsePopupRoute,
} from './';

const ENTITY_STATES = [
    ...skillTestResponseRoute,
    ...skillTestResponsePopupRoute,
];

@NgModule({
    imports: [
        VerifyMyCandidateSharedModule,
        RouterModule.forChild(ENTITY_STATES)
    ],
    declarations: [
        SkillTestResponseComponent,
        SkillTestResponseDetailComponent,
        SkillTestResponseDialogComponent,
        SkillTestResponseDeleteDialogComponent,
        SkillTestResponsePopupComponent,
        SkillTestResponseDeletePopupComponent,
    ],
    entryComponents: [
        SkillTestResponseComponent,
        SkillTestResponseDialogComponent,
        SkillTestResponsePopupComponent,
        SkillTestResponseDeleteDialogComponent,
        SkillTestResponseDeletePopupComponent,
    ],
    providers: [
        SkillTestResponseService,
        SkillTestResponsePopupService,
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class VerifyMyCandidateSkillTestResponseModule {}
