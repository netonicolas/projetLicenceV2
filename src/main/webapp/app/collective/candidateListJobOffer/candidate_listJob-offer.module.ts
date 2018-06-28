import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { VerifyMyCandidateSharedModule } from '../../shared';

import {CANDIDATE_JOB_OFFER_ROUTE} from './';
import {CandidateJobOfferComponent} from './candidate_ListJobOffer.component';

@NgModule({
    imports: [
        VerifyMyCandidateSharedModule,
        RouterModule.forChild(CANDIDATE_JOB_OFFER_ROUTE)
    ],
    declarations: [
        CandidateJobOfferComponent,
    ],
    entryComponents: [

    ],
    providers: [
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class CandidateJobOfferModule {}
