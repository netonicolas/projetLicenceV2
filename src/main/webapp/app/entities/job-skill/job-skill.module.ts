import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { VerifyMyCandidateSharedModule } from '../../shared';
import {
    JobSkillService,
    JobSkillPopupService,
    JobSkillComponent,
    JobSkillDetailComponent,
    JobSkillDialogComponent,
    JobSkillPopupComponent,
    JobSkillDeletePopupComponent,
    JobSkillDeleteDialogComponent,
    jobSkillRoute,
    jobSkillPopupRoute,
} from './';

const ENTITY_STATES = [
    ...jobSkillRoute,
    ...jobSkillPopupRoute,
];

@NgModule({
    imports: [
        VerifyMyCandidateSharedModule,
        RouterModule.forChild(ENTITY_STATES)
    ],
    declarations: [
        JobSkillComponent,
        JobSkillDetailComponent,
        JobSkillDialogComponent,
        JobSkillDeleteDialogComponent,
        JobSkillPopupComponent,
        JobSkillDeletePopupComponent,
    ],
    entryComponents: [
        JobSkillComponent,
        JobSkillDialogComponent,
        JobSkillPopupComponent,
        JobSkillDeleteDialogComponent,
        JobSkillDeletePopupComponent,
    ],
    providers: [
        JobSkillService,
        JobSkillPopupService,
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class VerifyMyCandidateJobSkillModule {}
