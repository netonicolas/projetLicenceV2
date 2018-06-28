import './vendor.ts';

import { NgModule, Injector } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';
import { HTTP_INTERCEPTORS } from '@angular/common/http';
import { Ng2Webstorage, LocalStorageService, SessionStorageService  } from 'ngx-webstorage';
import { JhiEventManager } from 'ng-jhipster';

import { AuthInterceptor } from './blocks/interceptor/auth.interceptor';
import { AuthExpiredInterceptor } from './blocks/interceptor/auth-expired.interceptor';
import { ErrorHandlerInterceptor } from './blocks/interceptor/errorhandler.interceptor';
import { NotificationInterceptor } from './blocks/interceptor/notification.interceptor';
import { VerifyMyCandidateSharedModule, UserRouteAccessService } from './shared';
import { VerifyMyCandidateAppRoutingModule} from './app-routing.module';
import { VerifyMyCandidateHomeModule } from './home/home.module';
import { VerifyMyCandidateAdminModule } from './admin/admin.module';
import { VerifyMyCandidateAccountModule } from './account/account.module';
import { VerifyMyCandidateEntityModule } from './entities/entity.module';
import { PaginationConfig } from './blocks/config/uib-pagination.config';
import {ApplyModule} from './collective/applyJobOffer/apply.module'

import {CandidateJobOfferModule} from './collective/job-offer-list/candidate_job-offer.module';
import {DetailJobOfferModule} from './collective/detail-job-offer/detail_job-offer.module';
import {CompanyJobOfferModule} from "./collective/company-job-offer-list/company-job-offer-list.module";
import {CompanyCandidatOfferModule} from "./collective/company-candidat-offer-List/company-candidat-offer-List.module";

// jhipster-needle-angular-add-module-import JHipster will add new module here
import {
    JhiMainComponent,
    NavbarComponent,
    FooterComponent,
    ProfileService,
    PageRibbonComponent,
    ErrorComponent
} from './layouts';
import {ApplyComponent} from "./collective/applyJobOffer";



@NgModule({
    imports: [
        BrowserModule,
        VerifyMyCandidateAppRoutingModule,
        Ng2Webstorage.forRoot({ prefix: 'jhi', separator: '-'}),
        VerifyMyCandidateSharedModule,
        VerifyMyCandidateHomeModule,
        VerifyMyCandidateAdminModule,
        VerifyMyCandidateAccountModule,
        VerifyMyCandidateEntityModule,
        CandidateJobOfferModule,
        DetailJobOfferModule,
        ApplyModule,
        CompanyJobOfferModule,
        CompanyCandidatOfferModule
        // jhipster-needle-angular-add-module JHipster will add new module here
    ],
    declarations: [
        JhiMainComponent,
        NavbarComponent,
        ErrorComponent,
        PageRibbonComponent,
        FooterComponent
    ],
    providers: [
        ProfileService,
        PaginationConfig,
        UserRouteAccessService,
        {
            provide: HTTP_INTERCEPTORS,
            useClass: AuthInterceptor,
            multi: true,
            deps: [
                LocalStorageService,
                SessionStorageService
            ]
        },
        {
            provide: HTTP_INTERCEPTORS,
            useClass: AuthExpiredInterceptor,
            multi: true,
            deps: [
                Injector
            ]
        },
        {
            provide: HTTP_INTERCEPTORS,
            useClass: ErrorHandlerInterceptor,
            multi: true,
            deps: [
                JhiEventManager
            ]
        },
        {
            provide: HTTP_INTERCEPTORS,
            useClass: NotificationInterceptor,
            multi: true,
            deps: [
                Injector
            ]
        }
    ],
    bootstrap: [ JhiMainComponent ]
})
export class VerifyMyCandidateAppModule {}
