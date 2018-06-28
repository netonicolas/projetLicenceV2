import { Component, OnInit } from '@angular/core';
import { NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';
import { JobOffer } from '../../entities/job-offer/job-offer.model';
import { JobOfferService } from '../../entities/job-offer/job-offer.service';
import {Company} from '../../entities/company/company.model';
import {CompanyService} from '../../entities/company/company.service';

import { Principal } from '../../shared';
import { ActivatedRoute } from '@angular/router';
import {HttpErrorResponse, HttpResponse} from '@angular/common/http';

@Component({
    selector: 'jhi-candidate-company-job-offer',
    templateUrl: './company-job-offer-list.component.html',
    styleUrls: [
        'company-job-offer-list.css'
    ]

})
export class CompanyJobOfferComponent implements OnInit {
    account: Account;
    jobOffers: JobOffer[];
    currentSearch: string;
    companies: Company[];
    idCompany : number;
    constructor(
        private companyService: CompanyService,
        private jobOfferService: JobOfferService,
        private principal: Principal,
        private eventManager: JhiEventManager,
        private activatedRoute: ActivatedRoute,
    ) {

        this.currentSearch = this.activatedRoute.snapshot && this.activatedRoute.snapshot.params['search'] ?
            this.activatedRoute.snapshot.params['search'] : '';
    }

    ngOnInit() {
        this.activatedRoute.params.subscribe((params) => {
            this.idCompany=params['idCompany'];
            this.registerAuthenticationSuccess();
            this.loadAll();
        });
    }

    registerAuthenticationSuccess() {
        this.eventManager.subscribe('authenticationSuccess', (message) => {
            this.principal.identity().then((account) => {
                this.account = account;
            });
        });
    }

    loadAll() {
        this.jobOfferService.queryCompany(this.idCompany).subscribe(
            (res: HttpResponse<JobOffer[]>) => {
                this.jobOffers = res.body;
                console.log(this.jobOffers);
                this.currentSearch = '';
            },
            (res: HttpErrorResponse) => this.onError(res.message)
        );

    }
    counter(i:number){
        return new  Array(i);
    }


    isAuthenticated() {
        return this.principal.isAuthenticated();
    }

    private onError(error) {

    }
}
