import { Component, OnInit } from '@angular/core';
import { NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import {JobOffer} from "../entities/job-offer";
import{JobOfferService} from "../entities/job-offer";

import { Account, LoginModalService, Principal } from '../shared';
import {HttpErrorResponse, HttpResponse} from "@angular/common/http";

@Component({
    selector: 'jhi-home',
    templateUrl: './home.component.html',
    styleUrls: [
        'home.css'
    ]

})
export class HomeComponent implements OnInit {
    account: Account;
    modalRef: NgbModalRef;
    jobOffers: JobOffer[];
    constructor(
        private principal: Principal,
        private loginModalService: LoginModalService,
        private eventManager: JhiEventManager,
        private jobOfferService: JobOfferService
    ) {
    }

    ngOnInit() {
        this.principal.identity().then((account) => {
            this.account = account;
        });
        this.registerAuthenticationSuccess();
        this.getJobOffer();
    }

    registerAuthenticationSuccess() {
        this.eventManager.subscribe('authenticationSuccess', (message) => {
            this.principal.identity().then((account) => {
                this.account = account;
            });
        });
    }

    isAuthenticated() {
        return this.principal.isAuthenticated();
    }

    login() {
        this.modalRef = this.loginModalService.open();
    }

    getJobOffer(){
        this.jobOfferService.queryLimite(3,0).subscribe(
            (res: HttpResponse<JobOffer[]>) => {
                this.jobOffers = res.body;
                console.log(this.jobOffers);
            },
            (res: HttpErrorResponse) => this.onError(res.message)
        );
    }

    private onError(error) {

    }
}
