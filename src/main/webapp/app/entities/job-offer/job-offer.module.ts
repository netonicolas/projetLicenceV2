import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { VerifyMyCandidateSharedModule } from '../../shared';
import {
    JobOfferService,
    JobOfferPopupService,
    JobOfferComponent,
    JobOfferDetailComponent,
    JobOfferDialogComponent,
    JobOfferPopupComponent,
    JobOfferDeletePopupComponent,
    JobOfferDeleteDialogComponent,
    jobOfferRoute,
    jobOfferPopupRoute,
} from './';

const ENTITY_STATES = [
    ...jobOfferRoute,
    ...jobOfferPopupRoute,
];

@NgModule({
    imports: [
        VerifyMyCandidateSharedModule,
        RouterModule.forChild(ENTITY_STATES)
    ],
    declarations: [
        JobOfferComponent,
        JobOfferDetailComponent,
        JobOfferDialogComponent,
        JobOfferDeleteDialogComponent,
        JobOfferPopupComponent,
        JobOfferDeletePopupComponent,
    ],
    entryComponents: [
        JobOfferComponent,
        JobOfferDialogComponent,
        JobOfferPopupComponent,
        JobOfferDeleteDialogComponent,
        JobOfferDeletePopupComponent,
    ],
    providers: [
        JobOfferService,
        JobOfferPopupService,
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class VerifyMyCandidateJobOfferModule {}
