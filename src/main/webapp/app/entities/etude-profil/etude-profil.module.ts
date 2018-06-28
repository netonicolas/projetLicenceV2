import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { VerifyMyCandidateSharedModule } from '../../shared';
import {
    EtudeProfilService,
    EtudeProfilPopupService,
    EtudeProfilComponent,
    EtudeProfilDetailComponent,
    EtudeProfilDialogComponent,
    EtudeProfilPopupComponent,
    EtudeProfilDeletePopupComponent,
    EtudeProfilDeleteDialogComponent,
    etudeProfilRoute,
    etudeProfilPopupRoute,
} from './';

const ENTITY_STATES = [
    ...etudeProfilRoute,
    ...etudeProfilPopupRoute,
];

@NgModule({
    imports: [
        VerifyMyCandidateSharedModule,
        RouterModule.forChild(ENTITY_STATES)
    ],
    declarations: [
        EtudeProfilComponent,
        EtudeProfilDetailComponent,
        EtudeProfilDialogComponent,
        EtudeProfilDeleteDialogComponent,
        EtudeProfilPopupComponent,
        EtudeProfilDeletePopupComponent,
    ],
    entryComponents: [
        EtudeProfilComponent,
        EtudeProfilDialogComponent,
        EtudeProfilPopupComponent,
        EtudeProfilDeleteDialogComponent,
        EtudeProfilDeletePopupComponent,
    ],
    providers: [
        EtudeProfilService,
        EtudeProfilPopupService,
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class VerifyMyCandidateEtudeProfilModule {}
