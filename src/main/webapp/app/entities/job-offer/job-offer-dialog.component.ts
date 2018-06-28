import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';

import { Observable } from 'rxjs/Observable';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { JobOffer } from './job-offer.model';
import { JobOfferPopupService } from './job-offer-popup.service';
import { JobOfferService } from './job-offer.service';
import { Company, CompanyService } from '../company';
import { CategoryOffer, CategoryOfferService } from '../category-offer';
import { Etude, EtudeService } from '../etude';

@Component({
    selector: 'jhi-job-offer-dialog',
    templateUrl: './job-offer-dialog.component.html'
})
export class JobOfferDialogComponent implements OnInit {

    jobOffer: JobOffer;
    isSaving: boolean;

    companies: Company[];

    categoryoffers: CategoryOffer[];

    etudes: Etude[];
    dateOfferDp: any;

    constructor(
        public activeModal: NgbActiveModal,
        private jhiAlertService: JhiAlertService,
        private jobOfferService: JobOfferService,
        private companyService: CompanyService,
        private categoryOfferService: CategoryOfferService,
        private etudeService: EtudeService,
        private eventManager: JhiEventManager
    ) {
    }

    ngOnInit() {
        this.isSaving = false;
        this.companyService.query()
            .subscribe((res: HttpResponse<Company[]>) => { this.companies = res.body; }, (res: HttpErrorResponse) => this.onError(res.message));
        this.categoryOfferService.query()
            .subscribe((res: HttpResponse<CategoryOffer[]>) => { this.categoryoffers = res.body; }, (res: HttpErrorResponse) => this.onError(res.message));
        this.etudeService.query()
            .subscribe((res: HttpResponse<Etude[]>) => { this.etudes = res.body; }, (res: HttpErrorResponse) => this.onError(res.message));
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    save() {
        this.isSaving = true;
        if (this.jobOffer.id !== undefined) {
            this.subscribeToSaveResponse(
                this.jobOfferService.update(this.jobOffer));
        } else {
            this.subscribeToSaveResponse(
                this.jobOfferService.create(this.jobOffer));
        }
    }

    private subscribeToSaveResponse(result: Observable<HttpResponse<JobOffer>>) {
        result.subscribe((res: HttpResponse<JobOffer>) =>
            this.onSaveSuccess(res.body), (res: HttpErrorResponse) => this.onSaveError());
    }

    private onSaveSuccess(result: JobOffer) {
        this.eventManager.broadcast({ name: 'jobOfferListModification', content: 'OK'});
        this.isSaving = false;
        this.activeModal.dismiss(result);
    }

    private onSaveError() {
        this.isSaving = false;
    }

    private onError(error: any) {
        this.jhiAlertService.error(error.message, null, null);
    }

    trackCompanyById(index: number, item: Company) {
        return item.id;
    }

    trackCategoryOfferById(index: number, item: CategoryOffer) {
        return item.id;
    }

    trackEtudeById(index: number, item: Etude) {
        return item.id;
    }
}

@Component({
    selector: 'jhi-job-offer-popup',
    template: ''
})
export class JobOfferPopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private jobOfferPopupService: JobOfferPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            if ( params['id'] ) {
                this.jobOfferPopupService
                    .open(JobOfferDialogComponent as Component, params['id']);
            } else {
                this.jobOfferPopupService
                    .open(JobOfferDialogComponent as Component);
            }
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
