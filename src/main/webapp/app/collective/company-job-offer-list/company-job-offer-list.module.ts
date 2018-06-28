import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { VerifyMyCandidateSharedModule } from '../../shared';

import { COMPANY_JOB_OFFER_ROUTE } from './';
import {CompanyJobOfferComponent} from './company-job-offer-list.component';

@NgModule({
    imports: [
        VerifyMyCandidateSharedModule,
        RouterModule.forChild(COMPANY_JOB_OFFER_ROUTE)
    ],
    declarations: [
        CompanyJobOfferComponent,
    ],
    entryComponents: [

    ],
    providers: [
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class CompanyJobOfferModule {}
