import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { VerifyMyCandidateSharedModule } from '../../shared';
import {
    SkillService,
    SkillPopupService,
    SkillComponent,
    SkillDetailComponent,
    SkillDialogComponent,
    SkillPopupComponent,
    SkillDeletePopupComponent,
    SkillDeleteDialogComponent,
    skillRoute,
    skillPopupRoute,
} from './';

const ENTITY_STATES = [
    ...skillRoute,
    ...skillPopupRoute,
];

@NgModule({
    imports: [
        VerifyMyCandidateSharedModule,
        RouterModule.forChild(ENTITY_STATES)
    ],
    declarations: [
        SkillComponent,
        SkillDetailComponent,
        SkillDialogComponent,
        SkillDeleteDialogComponent,
        SkillPopupComponent,
        SkillDeletePopupComponent,
    ],
    entryComponents: [
        SkillComponent,
        SkillDialogComponent,
        SkillPopupComponent,
        SkillDeleteDialogComponent,
        SkillDeletePopupComponent,
    ],
    providers: [
        SkillService,
        SkillPopupService,
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class VerifyMyCandidateSkillModule {}
