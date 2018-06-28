import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { VerifyMyCandidateSharedModule } from '../../shared';
import {
    EtudeService,
    EtudePopupService,
    EtudeComponent,
    EtudeDetailComponent,
    EtudeDialogComponent,
    EtudePopupComponent,
    EtudeDeletePopupComponent,
    EtudeDeleteDialogComponent,
    etudeRoute,
    etudePopupRoute,
} from './';

const ENTITY_STATES = [
    ...etudeRoute,
    ...etudePopupRoute,
];

@NgModule({
    imports: [
        VerifyMyCandidateSharedModule,
        RouterModule.forChild(ENTITY_STATES)
    ],
    declarations: [
        EtudeComponent,
        EtudeDetailComponent,
        EtudeDialogComponent,
        EtudeDeleteDialogComponent,
        EtudePopupComponent,
        EtudeDeletePopupComponent,
    ],
    entryComponents: [
        EtudeComponent,
        EtudeDialogComponent,
        EtudePopupComponent,
        EtudeDeleteDialogComponent,
        EtudeDeletePopupComponent,
    ],
    providers: [
        EtudeService,
        EtudePopupService,
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class VerifyMyCandidateEtudeModule {}
