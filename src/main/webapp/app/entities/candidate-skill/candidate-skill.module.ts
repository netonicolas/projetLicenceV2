import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { VerifyMyCandidateSharedModule } from '../../shared';
import {
    CandidateSkillService,
    CandidateSkillPopupService,
    CandidateSkillComponent,
    CandidateSkillDetailComponent,
    CandidateSkillDialogComponent,
    CandidateSkillPopupComponent,
    CandidateSkillDeletePopupComponent,
    CandidateSkillDeleteDialogComponent,
    candidateSkillRoute,
    candidateSkillPopupRoute,
} from './';

const ENTITY_STATES = [
    ...candidateSkillRoute,
    ...candidateSkillPopupRoute,
];

@NgModule({
    imports: [
        VerifyMyCandidateSharedModule,
        RouterModule.forChild(ENTITY_STATES)
    ],
    declarations: [
        CandidateSkillComponent,
        CandidateSkillDetailComponent,
        CandidateSkillDialogComponent,
        CandidateSkillDeleteDialogComponent,
        CandidateSkillPopupComponent,
        CandidateSkillDeletePopupComponent,
    ],
    entryComponents: [
        CandidateSkillComponent,
        CandidateSkillDialogComponent,
        CandidateSkillPopupComponent,
        CandidateSkillDeleteDialogComponent,
        CandidateSkillDeletePopupComponent,
    ],
    providers: [
        CandidateSkillService,
        CandidateSkillPopupService,
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class VerifyMyCandidateCandidateSkillModule {}
