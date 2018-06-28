import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { VerifyMyCandidateSharedModule } from '../../shared';
import {
    ExperienceProfilService,
    ExperienceProfilPopupService,
    ExperienceProfilComponent,
    ExperienceProfilDetailComponent,
    ExperienceProfilDialogComponent,
    ExperienceProfilPopupComponent,
    ExperienceProfilDeletePopupComponent,
    ExperienceProfilDeleteDialogComponent,
    experienceProfilRoute,
    experienceProfilPopupRoute,
} from './';

const ENTITY_STATES = [
    ...experienceProfilRoute,
    ...experienceProfilPopupRoute,
];

@NgModule({
    imports: [
        VerifyMyCandidateSharedModule,
        RouterModule.forChild(ENTITY_STATES)
    ],
    declarations: [
        ExperienceProfilComponent,
        ExperienceProfilDetailComponent,
        ExperienceProfilDialogComponent,
        ExperienceProfilDeleteDialogComponent,
        ExperienceProfilPopupComponent,
        ExperienceProfilDeletePopupComponent,
    ],
    entryComponents: [
        ExperienceProfilComponent,
        ExperienceProfilDialogComponent,
        ExperienceProfilPopupComponent,
        ExperienceProfilDeleteDialogComponent,
        ExperienceProfilDeletePopupComponent,
    ],
    providers: [
        ExperienceProfilService,
        ExperienceProfilPopupService,
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class VerifyMyCandidateExperienceProfilModule {}
