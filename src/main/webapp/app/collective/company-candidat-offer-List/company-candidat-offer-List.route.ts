import { Routes } from '@angular/router';
import { CompanyCandidatOfferComponent } from './';

export const COMPANY_CANDIDAT_JOB_OFFER_ROUTE: Routes = [{
    path: 'Company-Candidat-List-Job-offer/:idJobOffer',
    component: CompanyCandidatOfferComponent,
    data: {
        authorities: [],
        pageTitle: 'home.title'
    }
}];
