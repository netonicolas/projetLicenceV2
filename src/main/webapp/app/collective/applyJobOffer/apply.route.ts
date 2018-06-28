import { Routes } from '@angular/router';
import { ApplyComponent } from './';

export const APPLY_ROUTE: Routes = [{
    path: 'apply/:idJobOffer',
    component: ApplyComponent,
    data: {
        authorities: ['ROLE_CANDIDATE'],
        pageTitle: 'home.title'
    }
}];
