import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';

import { VerifyMyCandidateJobOfferModule } from './job-offer/job-offer.module';
import { VerifyMyCandidateCompanyModule } from './company/company.module';
import { VerifyMyCandidateSkillTestModule } from './skill-test/skill-test.module';
import { VerifyMyCandidateSkillTestResponseModule } from './skill-test-response/skill-test-response.module';
import { VerifyMyCandidateSkillQuestionModule } from './skill-question/skill-question.module';
import { VerifyMyCandidateSkillModule } from './skill/skill.module';
import { VerifyMyCandidateJobSkillModule } from './job-skill/job-skill.module';
import { VerifyMyCandidateJobResponseModule } from './job-response/job-response.module';
import { VerifyMyCandidateCategoryOfferModule } from './category-offer/category-offer.module';
import { VerifyMyCandidateProfilModule } from './profil/profil.module';
import { VerifyMyCandidateEtudeProfilModule } from './etude-profil/etude-profil.module';
import { VerifyMyCandidateEtudeModule } from './etude/etude.module';
import { VerifyMyCandidateExperienceProfilModule } from './experience-profil/experience-profil.module';
import { VerifyMyCandidateExperienceModule } from './experience/experience.module';
import { VerifyMyCandidateCandidateSkillModule } from './candidate-skill/candidate-skill.module';
import { VerifyMyCandidateCandidateTrueSkillModule } from './candidate-true-skill/candidate-true-skill.module';
/* jhipster-needle-add-entity-module-import - JHipster will add entity modules imports here */

@NgModule({
    imports: [
        VerifyMyCandidateJobOfferModule,
        VerifyMyCandidateCompanyModule,
        VerifyMyCandidateSkillTestModule,
        VerifyMyCandidateSkillTestResponseModule,
        VerifyMyCandidateSkillQuestionModule,
        VerifyMyCandidateSkillModule,
        VerifyMyCandidateJobSkillModule,
        VerifyMyCandidateJobResponseModule,
        VerifyMyCandidateCategoryOfferModule,
        VerifyMyCandidateProfilModule,
        VerifyMyCandidateEtudeProfilModule,
        VerifyMyCandidateEtudeModule,
        VerifyMyCandidateExperienceProfilModule,
        VerifyMyCandidateExperienceModule,
        VerifyMyCandidateCandidateSkillModule,
        VerifyMyCandidateCandidateTrueSkillModule,
        /* jhipster-needle-add-entity-module - JHipster will add entity modules here */
    ],
    declarations: [],
    entryComponents: [],
    providers: [],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class VerifyMyCandidateEntityModule {}
