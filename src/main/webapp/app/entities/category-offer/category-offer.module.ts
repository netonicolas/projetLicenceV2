import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { VerifyMyCandidateSharedModule } from '../../shared';
import {
    CategoryOfferService,
    CategoryOfferPopupService,
    CategoryOfferComponent,
    CategoryOfferDetailComponent,
    CategoryOfferDialogComponent,
    CategoryOfferPopupComponent,
    CategoryOfferDeletePopupComponent,
    CategoryOfferDeleteDialogComponent,
    categoryOfferRoute,
    categoryOfferPopupRoute,
} from './';

const ENTITY_STATES = [
    ...categoryOfferRoute,
    ...categoryOfferPopupRoute,
];

@NgModule({
    imports: [
        VerifyMyCandidateSharedModule,
        RouterModule.forChild(ENTITY_STATES)
    ],
    declarations: [
        CategoryOfferComponent,
        CategoryOfferDetailComponent,
        CategoryOfferDialogComponent,
        CategoryOfferDeleteDialogComponent,
        CategoryOfferPopupComponent,
        CategoryOfferDeletePopupComponent,
    ],
    entryComponents: [
        CategoryOfferComponent,
        CategoryOfferDialogComponent,
        CategoryOfferPopupComponent,
        CategoryOfferDeleteDialogComponent,
        CategoryOfferDeletePopupComponent,
    ],
    providers: [
        CategoryOfferService,
        CategoryOfferPopupService,
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class VerifyMyCandidateCategoryOfferModule {}
