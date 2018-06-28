import { Routes } from '@angular/router';
import { CandidateJobOfferComponent } from './';

export const CANDIDATE_JOB_OFFER_ROUTE: Routes = [{
    path: 'List-Job-offer/:limite/:page',
    component: CandidateJobOfferComponent,
    data: {
        authorities: [],
        pageTitle: 'home.title'
    }
}];
