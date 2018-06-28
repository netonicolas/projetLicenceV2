import { Routes } from '@angular/router';
import { DetailJobOfferComponent } from './';
import {CandidateJobOfferComponent} from "../job-offer-list";

export const DETAIL_JOB_OFFER_ROUTE: Routes = [{
    path: 'Detail-Job-offer/:id',
    component: DetailJobOfferComponent,
    data: {
        authorities: [],
        pageTitle: 'home.title'
    }
},{
    path: 'List-Job-offer/:limite/:page/',
    component: CandidateJobOfferComponent,
    data: {
        authorities: [],
        pageTitle: 'home.title'
    }}];
