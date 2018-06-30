import { Component, OnInit } from '@angular/core';
import { NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';
import { JobOffer } from '../../entities/job-offer/job-offer.model';
import { JobOfferService } from '../../entities/job-offer/job-offer.service';
import {Company} from '../../entities/company/company.model';
import {CompanyService} from '../../entities/company/company.service';

import { Principal } from '../../shared';
import {ActivatedRoute, Router} from '@angular/router';
import {HttpErrorResponse, HttpResponse} from '@angular/common/http';
import {Profil, ProfilService} from "../../entities/profil";
import {ProfileService} from "../../layouts";
import {JobResponseService} from "../../entities/job-response";

import {JobResponse} from "../../entities/job-response";


@Component({
    selector: 'jhi-candidate-apply',
    templateUrl: './apply.component.html',
    styleUrls: [
        'apply.css'
    ]

})
export class ApplyComponent implements OnInit {
    account: Account;
    jobOffer: JobOffer;
    profil: Profil;
    currentSearch: string;
    idJobOffer:number;
    jobResponse:JobResponse;

    constructor(
        private companyService: CompanyService,
        private jobOfferService: JobOfferService,
        private principal: Principal,
        private eventManager: JhiEventManager,
        private activatedRoute: ActivatedRoute,
        private profilService: ProfilService,
        private jobResponseService: JobResponseService,
        private router: Router,
    ) {

        this.currentSearch = this.activatedRoute.snapshot && this.activatedRoute.snapshot.params['search'] ?
            this.activatedRoute.snapshot.params['search'] : '';
    }

    ngOnInit() {
        this.jobResponse=new JobResponse();
        this.activatedRoute.params.subscribe((params) => {
            this.idJobOffer=params['idJobOffer'];

            this.registerAuthenticationSuccess();

        });
    }

    registerAuthenticationSuccess() {
        this.principal.identity().then((account) => {
            this.account = account;
            this.getProfil();
        });

    }

    getJobOffer(){
        this.jobOfferService.find(this.idJobOffer).subscribe(
            (res:HttpResponse<JobOffer>)=>{
                this.jobOffer=res.body;
            },
            (res: HttpErrorResponse) => this.onError(res.message)
        );
    }

    getProfil(){
        console.log("acount:",this.account);
        this.profilService.findByUserId(parseInt(this.account.id)).subscribe(
            (res: HttpResponse<Profil>) => {
                this.profil = res.body;
                this.currentSearch = '';
                this.getJobOffer();
                },
            (res: HttpErrorResponse) => this.onError(res.message)
        );
    }

    addJobResponse(){

        this.jobResponse.candidat=this.profil;
        console.log('candidat:',this.jobResponse.candidat);
        this.jobResponse.jobOffer=this.jobOffer;
        let today= new Date();
        this.jobResponse.dateResponse= today.getFullYear()+"-"+("0"+today.getMonth()).slice(-2)+"-"+("0"+today.getDate()).slice(-2) ;

        this.jobResponseService.create(this.jobResponse).subscribe(
            (res: HttpResponse<Profil>) => {
                let response = res.body;
                console.log('response addJobResponse:',response);
                this.router.navigate( ['/']);
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
