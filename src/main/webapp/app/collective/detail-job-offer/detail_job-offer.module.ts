import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { VerifyMyCandidateSharedModule } from '../../shared';

    import { DETAIL_JOB_OFFER_ROUTE } from './';
import {DetailJobOfferComponent} from './detail_home.component';
@NgModule({
    imports: [
        VerifyMyCandidateSharedModule,
        RouterModule.forChild(DETAIL_JOB_OFFER_ROUTE)
    ],
    declarations: [
        DetailJobOfferComponent,
    ],
    entryComponents: [

    ],
    providers: [
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class DetailJobOfferModule {}
