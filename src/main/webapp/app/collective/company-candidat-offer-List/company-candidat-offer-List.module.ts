import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { VerifyMyCandidateSharedModule } from '../../shared';

import { COMPANY_CANDIDAT_JOB_OFFER_ROUTE } from './';
import {CompanyCandidatOfferComponent} from './company-candidat-offer-List.component';

@NgModule({
    imports: [
        VerifyMyCandidateSharedModule,
        RouterModule.forChild(COMPANY_CANDIDAT_JOB_OFFER_ROUTE)
    ],
    declarations: [
       CompanyCandidatOfferComponent,
    ],
    entryComponents: [

    ],
    providers: [
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class CompanyCandidatOfferModule {}
