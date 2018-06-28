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
import {Profil} from "../../entities/profil";
import {ProfilService} from "../../entities/profil";
import {JobResponse} from "../../entities/job-response";
import {JobResponseService} from "../../entities/job-response";
import {User} from "../../shared";
import {UserService} from "../../shared";
import {forEach} from "@angular/router/src/utils/collection";

@Component({
    selector: 'jhi-candidate-company-job-offer',
    templateUrl: './company-candidat-offer-List.component.html',
    styleUrls: [
        'company-candidat-offer-List.css'
    ]
})


export class CompanyCandidatOfferComponent implements OnInit {
    account: Account;
    jobResponce: JobResponse[];
    currentSearch: string;
    idJobOffer : number;
    users:User[];
    lapin: string;
    profils: Profil[];
    uTab = new Array();
    constructor(
        private companyService: CompanyService,
        private jobOfferService: JobOfferService,
        private principal: Principal,
        private eventManager: JhiEventManager,
        private activatedRoute: ActivatedRoute,
        private jobResponseService: JobResponseService,
        private userService: UserService,
        private profilService: ProfilService
    ) {
        this.uTab= new Array();
        this.currentSearch = this.activatedRoute.snapshot && this.activatedRoute.snapshot.params['search'] ?
            this.activatedRoute.snapshot.params['search'] : '';
    }

    ngOnInit() {
        this.activatedRoute.params.subscribe((params) => {

            this.idJobOffer=params['idJobOffer'];
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
        this.jobResponseService.findByJob(this.idJobOffer).subscribe(
            (res: HttpResponse<JobResponse[]>) => {
                this.jobResponce = res.body;
                //console.log(this.jobResponce);
                this.currentSearch = '';
                this.jobResponce.forEach( j =>{
                    this.getCandidats(j.candidat.id);
                });

            },
            (res: HttpErrorResponse) => this.onError(res.message)
        );

    }

    getCandidats(id) {
        this.profilService.find(id).subscribe(
            (res: HttpResponse<Profil>) => {
                    let profil = res.body;
                    console.log("az"+profil.userId);
                    this.getUser(profil.userId,this.users);
            });
    }

    getUser(id,users){

        this.userService.findById(id).subscribe(
            (res: HttpResponse<Profil>) => {
                this.uTab.push(res.body);
                console.log(users);
                this.users=this.uTab;
            });
    };

    counter(i:number){
        return new  Array(i);
    }


    isAuthenticated() {
        return this.principal.isAuthenticated();
    }

    private onError(error) {

    }
}
