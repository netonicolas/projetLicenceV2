import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';

import { Observable } from 'rxjs/Observable';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { SkillTest } from './skill-test.model';
import { SkillTestPopupService } from './skill-test-popup.service';
import { SkillTestService } from './skill-test.service';
import { JobOffer, JobOfferService } from '../job-offer';

@Component({
    selector: 'jhi-skill-test-dialog',
    templateUrl: './skill-test-dialog.component.html'
})
export class SkillTestDialogComponent implements OnInit {

    skillTest: SkillTest;
    isSaving: boolean;

    joboffers: JobOffer[];
    dateDp: any;

    constructor(
        public activeModal: NgbActiveModal,
        private jhiAlertService: JhiAlertService,
        private skillTestService: SkillTestService,
        private jobOfferService: JobOfferService,
        private eventManager: JhiEventManager
    ) {
    }

    ngOnInit() {
        this.isSaving = false;
        this.jobOfferService.query()
            .subscribe((res: HttpResponse<JobOffer[]>) => { this.joboffers = res.body; }, (res: HttpErrorResponse) => this.onError(res.message));
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    save() {
        this.isSaving = true;
        if (this.skillTest.id !== undefined) {
            this.subscribeToSaveResponse(
                this.skillTestService.update(this.skillTest));
        } else {
            this.subscribeToSaveResponse(
                this.skillTestService.create(this.skillTest));
        }
    }

    private subscribeToSaveResponse(result: Observable<HttpResponse<SkillTest>>) {
        result.subscribe((res: HttpResponse<SkillTest>) =>
            this.onSaveSuccess(res.body), (res: HttpErrorResponse) => this.onSaveError());
    }

    private onSaveSuccess(result: SkillTest) {
        this.eventManager.broadcast({ name: 'skillTestListModification', content: 'OK'});
        this.isSaving = false;
        this.activeModal.dismiss(result);
    }

    private onSaveError() {
        this.isSaving = false;
    }

    private onError(error: any) {
        this.jhiAlertService.error(error.message, null, null);
    }

    trackJobOfferById(index: number, item: JobOffer) {
        return item.id;
    }
}

@Component({
    selector: 'jhi-skill-test-popup',
    template: ''
})
export class SkillTestPopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private skillTestPopupService: SkillTestPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            if ( params['id'] ) {
                this.skillTestPopupService
                    .open(SkillTestDialogComponent as Component, params['id']);
            } else {
                this.skillTestPopupService
                    .open(SkillTestDialogComponent as Component);
            }
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
