import { Routes } from '@angular/router';
import { CompanyJobOfferComponent } from './';

export const COMPANY_JOB_OFFER_ROUTE: Routes = [{
    path: 'Company-List-Job-offer/:idCompany',
    component: CompanyJobOfferComponent,
    data: {
        authorities: ["ROLE_COMPANY"],
        pageTitle: 'home.title'
    }
}];
