import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';

import { Observable } from 'rxjs/Observable';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { JobResponse } from './job-response.model';
import { JobResponsePopupService } from './job-response-popup.service';
import { JobResponseService } from './job-response.service';
import { Profil, ProfilService } from '../profil';
import { JobOffer, JobOfferService } from '../job-offer';

@Component({
    selector: 'jhi-job-response-dialog',
    templateUrl: './job-response-dialog.component.html'
})
export class JobResponseDialogComponent implements OnInit {

    jobResponse: JobResponse;
    isSaving: boolean;

    profils: Profil[];

    joboffers: JobOffer[];

    constructor(
        public activeModal: NgbActiveModal,
        private jhiAlertService: JhiAlertService,
        private jobResponseService: JobResponseService,
        private profilService: ProfilService,
        private jobOfferService: JobOfferService,
        private eventManager: JhiEventManager
    ) {
    }

    ngOnInit() {
        this.isSaving = false;
        this.profilService.query()
            .subscribe((res: HttpResponse<Profil[]>) => { this.profils = res.body; }, (res: HttpErrorResponse) => this.onError(res.message));
        this.jobOfferService.query()
            .subscribe((res: HttpResponse<JobOffer[]>) => { this.joboffers = res.body; }, (res: HttpErrorResponse) => this.onError(res.message));
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    save() {
        this.isSaving = true;
        if (this.jobResponse.id !== undefined) {
            this.subscribeToSaveResponse(
                this.jobResponseService.update(this.jobResponse));
        } else {
            this.subscribeToSaveResponse(
                this.jobResponseService.create(this.jobResponse));
        }
    }

    private subscribeToSaveResponse(result: Observable<HttpResponse<JobResponse>>) {
        result.subscribe((res: HttpResponse<JobResponse>) =>
            this.onSaveSuccess(res.body), (res: HttpErrorResponse) => this.onSaveError());
    }

    private onSaveSuccess(result: JobResponse) {
        this.eventManager.broadcast({ name: 'jobResponseListModification', content: 'OK'});
        this.isSaving = false;
        this.activeModal.dismiss(result);
    }

    private onSaveError() {
        this.isSaving = false;
    }

    private onError(error: any) {
        this.jhiAlertService.error(error.message, null, null);
    }

    trackProfilById(index: number, item: Profil) {
        return item.id;
    }

    trackJobOfferById(index: number, item: JobOffer) {
        return item.id;
    }
}

@Component({
    selector: 'jhi-job-response-popup',
    template: ''
})
export class JobResponsePopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private jobResponsePopupService: JobResponsePopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            if ( params['id'] ) {
                this.jobResponsePopupService
                    .open(JobResponseDialogComponent as Component, params['id']);
            } else {
                this.jobResponsePopupService
                    .open(JobResponseDialogComponent as Component);
            }
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
