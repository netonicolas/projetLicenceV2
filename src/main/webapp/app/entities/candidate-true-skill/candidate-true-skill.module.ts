import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { VerifyMyCandidateSharedModule } from '../../shared';
import {
    CandidateTrueSkillService,
    CandidateTrueSkillPopupService,
    CandidateTrueSkillComponent,
    CandidateTrueSkillDetailComponent,
    CandidateTrueSkillDialogComponent,
    CandidateTrueSkillPopupComponent,
    CandidateTrueSkillDeletePopupComponent,
    CandidateTrueSkillDeleteDialogComponent,
    candidateTrueSkillRoute,
    candidateTrueSkillPopupRoute,
} from './';

const ENTITY_STATES = [
    ...candidateTrueSkillRoute,
    ...candidateTrueSkillPopupRoute,
];

@NgModule({
    imports: [
        VerifyMyCandidateSharedModule,
        RouterModule.forChild(ENTITY_STATES)
    ],
    declarations: [
        CandidateTrueSkillComponent,
        CandidateTrueSkillDetailComponent,
        CandidateTrueSkillDialogComponent,
        CandidateTrueSkillDeleteDialogComponent,
        CandidateTrueSkillPopupComponent,
        CandidateTrueSkillDeletePopupComponent,
    ],
    entryComponents: [
        CandidateTrueSkillComponent,
        CandidateTrueSkillDialogComponent,
        CandidateTrueSkillPopupComponent,
        CandidateTrueSkillDeleteDialogComponent,
        CandidateTrueSkillDeletePopupComponent,
    ],
    providers: [
        CandidateTrueSkillService,
        CandidateTrueSkillPopupService,
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class VerifyMyCandidateCandidateTrueSkillModule {}
