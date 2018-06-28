import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { VerifyMyCandidateSharedModule } from '../../shared';
import {
    ProfilService,
    ProfilPopupService,
    ProfilComponent,
    ProfilDetailComponent,
    ProfilDialogComponent,
    ProfilPopupComponent,
    ProfilDeletePopupComponent,
    ProfilDeleteDialogComponent,
    profilRoute,
    profilPopupRoute,
} from './';

const ENTITY_STATES = [
    ...profilRoute,
    ...profilPopupRoute,
];

@NgModule({
    imports: [
        VerifyMyCandidateSharedModule,
        RouterModule.forChild(ENTITY_STATES)
    ],
    declarations: [
        ProfilComponent,
        ProfilDetailComponent,
        ProfilDialogComponent,
        ProfilDeleteDialogComponent,
        ProfilPopupComponent,
        ProfilDeletePopupComponent,
    ],
    entryComponents: [
        ProfilComponent,
        ProfilDialogComponent,
        ProfilPopupComponent,
        ProfilDeleteDialogComponent,
        ProfilDeletePopupComponent,
    ],
    providers: [
        ProfilService,
        ProfilPopupService,
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class VerifyMyCandidateProfilModule {}
