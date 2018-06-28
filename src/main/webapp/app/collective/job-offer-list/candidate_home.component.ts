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
import {JobResponse} from "../../entities/job-response";
import {JobResponseService} from "../../entities/job-response";

@Component({
    selector: 'jhi-candidate-job-offer',
    templateUrl: './candidate_job-offer.component.html',
    styleUrls: [
        'candidate_job-offer.css'
    ]

})

export class CandidateJobOfferComponent implements OnInit {
    account: Account;
    jobOffers: JobOffer[];
    jobResponse:JobResponse[];
    currentSearch: string;
    companies: Company[];
    page:number;
    limite:number;
    nbPage:number;

    constructor(
        private companyService: CompanyService,
        private jobOfferService: JobOfferService,
        private principal: Principal,
        private eventManager: JhiEventManager,
        private activatedRoute: ActivatedRoute,
        private jobResponseService:JobResponseService,
    ) {

        this.currentSearch = this.activatedRoute.snapshot && this.activatedRoute.snapshot.params['search'] ?
            this.activatedRoute.snapshot.params['search'] : '';
    }

    ngOnInit() {
        this.activatedRoute.params.subscribe((params) => {
            this.page=params['page'];
            this.limite=params['limite'];
            this.registerAuthenticationSuccess();
            this.loadAll();
            this.getJobResponse();
        });
    }

    registerAuthenticationSuccess() {
        this.principal.identity().then((account) => {
            this.account = account;
        });
        this.eventManager.subscribe('authenticationSuccess', (message) => {
            this.principal.identity().then((account) => {
                this.account = account;
            });
        });
        this.getJobResponse();
    }

    loadAll() {
        this.jobOfferService.queryLimite(this.limite,this.page).subscribe(
            (res: HttpResponse<JobOffer[]>) => {
                this.jobOffers = res.body;
                console.log(this.jobOffers);
                this.currentSearch = '';
            },
            (res: HttpErrorResponse) => this.onError(res.message)
        );
        this.jobOfferService.count().subscribe(
            (res:HttpResponse<number>)=>{
                this.nbPage=res.body/this.limite;
                console.log(this.nbPage%10);
                if(this.nbPage%10!=0){
                 this.nbPage=(Math.floor(this.nbPage));
                }
                console.log(this.nbPage);
            },
            (res: HttpErrorResponse) => this.onError(res.message)
        );
    }

    getJobResponse(){
        this.jobResponseService.findByUser(parseInt(this.account.id)).subscribe(
            (res:HttpResponse<JobResponse[]>)=>{
                this.jobResponse=res.body;
                console.log(this.nbPage);
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
