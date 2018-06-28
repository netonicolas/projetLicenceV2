import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { VerifyMyCandidateSharedModule } from '../../shared';
import {
    JobResponseService,
    JobResponsePopupService,
    JobResponseComponent,
    JobResponseDetailComponent,
    JobResponseDialogComponent,
    JobResponsePopupComponent,
    JobResponseDeletePopupComponent,
    JobResponseDeleteDialogComponent,
    jobResponseRoute,
    jobResponsePopupRoute,
} from './';

const ENTITY_STATES = [
    ...jobResponseRoute,
    ...jobResponsePopupRoute,
];

@NgModule({
    imports: [
        VerifyMyCandidateSharedModule,
        RouterModule.forChild(ENTITY_STATES)
    ],
    declarations: [
        JobResponseComponent,
        JobResponseDetailComponent,
        JobResponseDialogComponent,
        JobResponseDeleteDialogComponent,
        JobResponsePopupComponent,
        JobResponseDeletePopupComponent,
    ],
    entryComponents: [
        JobResponseComponent,
        JobResponseDialogComponent,
        JobResponsePopupComponent,
        JobResponseDeleteDialogComponent,
        JobResponseDeletePopupComponent,
    ],
    providers: [
        JobResponseService,
        JobResponsePopupService,
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class VerifyMyCandidateJobResponseModule {}
