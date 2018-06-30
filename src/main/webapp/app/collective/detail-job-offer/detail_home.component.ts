import { Component, OnInit } from '@angular/core';
import { NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';
import { JobOffer } from '../../entities/job-offer/job-offer.model';
import { JobOfferService } from '../../entities/job-offer/job-offer.service';
import { JobSkill } from '../../entities/job-skill/job-skill.model';
import { JobSkillService } from '../../entities/job-skill/job-skill.service';
import { Principal } from '../../shared';
import {ActivatedRoute, Params} from '@angular/router';
import {HttpErrorResponse, HttpResponse} from '@angular/common/http';

@Component({
    selector: 'jhi-detail-job-offer',
    templateUrl: './detail_job-offer.component.html',

})
export class DetailJobOfferComponent implements OnInit {
    account: Account;
    jobOffer: JobOffer;
    idJobOffer:number;
    jobSkills:JobSkill[];
    constructor(
        private jobOfferService: JobOfferService,
        private jobSkillService: JobSkillService,
        private principal: Principal,
        private eventManager: JhiEventManager,
        private activatedRoute: ActivatedRoute,
    ) {

    }

    registerAuthenticationSuccess() {
        this.eventManager.subscribe('authenticationSuccess', (message) => {
            this.principal.identity().then((account) => {
                this.account = account;
            });
        });
    }

    ngOnInit() {
        this.activatedRoute.params.subscribe((params) => {
            this.idJobOffer=params['id'];
            this.load(this.idJobOffer);
        });


    }

    load(id) {

        this.jobSkillService.findJobSkillByJobOffer(id).subscribe((res: HttpResponse<JobOffer[]>) => {
            console.log(res.body);
            this.jobSkills=res.body;
        });

        this.jobOfferService.find(id)
            .subscribe((jobOfferResponse: HttpResponse<JobOffer>) => {
                this.jobOffer = jobOfferResponse.body;
            })
    }

    isAuthenticated() {
        return this.principal.isAuthenticated();
    }

      private onError(error) {

    }
}
