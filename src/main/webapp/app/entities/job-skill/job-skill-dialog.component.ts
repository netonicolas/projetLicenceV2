import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';

import { Observable } from 'rxjs/Observable';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { JobSkill } from './job-skill.model';
import { JobSkillPopupService } from './job-skill-popup.service';
import { JobSkillService } from './job-skill.service';
import { Skill, SkillService } from '../skill';
import { JobOffer, JobOfferService } from '../job-offer';

@Component({
    selector: 'jhi-job-skill-dialog',
    templateUrl: './job-skill-dialog.component.html'
})
export class JobSkillDialogComponent implements OnInit {

    jobSkill: JobSkill;
    isSaving: boolean;

    skills: Skill[];

    joboffers: JobOffer[];

    constructor(
        public activeModal: NgbActiveModal,
        private jhiAlertService: JhiAlertService,
        private jobSkillService: JobSkillService,
        private skillService: SkillService,
        private jobOfferService: JobOfferService,
        private eventManager: JhiEventManager
    ) {
    }

    ngOnInit() {
        this.isSaving = false;
        this.skillService.query()
            .subscribe((res: HttpResponse<Skill[]>) => { this.skills = res.body; }, (res: HttpErrorResponse) => this.onError(res.message));
        this.jobOfferService.query()
            .subscribe((res: HttpResponse<JobOffer[]>) => { this.joboffers = res.body; }, (res: HttpErrorResponse) => this.onError(res.message));
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    save() {
        this.isSaving = true;
        if (this.jobSkill.id !== undefined) {
            this.subscribeToSaveResponse(
                this.jobSkillService.update(this.jobSkill));
        } else {
            this.subscribeToSaveResponse(
                this.jobSkillService.create(this.jobSkill));
        }
    }

    private subscribeToSaveResponse(result: Observable<HttpResponse<JobSkill>>) {
        result.subscribe((res: HttpResponse<JobSkill>) =>
            this.onSaveSuccess(res.body), (res: HttpErrorResponse) => this.onSaveError());
    }

    private onSaveSuccess(result: JobSkill) {
        this.eventManager.broadcast({ name: 'jobSkillListModification', content: 'OK'});
        this.isSaving = false;
        this.activeModal.dismiss(result);
    }

    private onSaveError() {
        this.isSaving = false;
    }

    private onError(error: any) {
        this.jhiAlertService.error(error.message, null, null);
    }

    trackSkillById(index: number, item: Skill) {
        return item.id;
    }

    trackJobOfferById(index: number, item: JobOffer) {
        return item.id;
    }
}

@Component({
    selector: 'jhi-job-skill-popup',
    template: ''
})
export class JobSkillPopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private jobSkillPopupService: JobSkillPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            if ( params['id'] ) {
                this.jobSkillPopupService
                    .open(JobSkillDialogComponent as Component, params['id']);
            } else {
                this.jobSkillPopupService
                    .open(JobSkillDialogComponent as Component);
            }
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
