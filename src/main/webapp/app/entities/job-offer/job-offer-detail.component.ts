import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs/Subscription';
import { JhiEventManager } from 'ng-jhipster';

import { JobOffer } from './job-offer.model';
import { JobOfferService } from './job-offer.service';

@Component({
    selector: 'jhi-job-offer-detail',
    templateUrl: './job-offer-detail.component.html'
})
export class JobOfferDetailComponent implements OnInit, OnDestroy {

    jobOffer: JobOffer;
    private subscription: Subscription;
    private eventSubscriber: Subscription;

    constructor(
        private eventManager: JhiEventManager,
        private jobOfferService: JobOfferService,
        private route: ActivatedRoute
    ) {
    }

    ngOnInit() {
        this.subscription = this.route.params.subscribe((params) => {
            this.load(params['id']);
        });
        this.registerChangeInJobOffers();
    }

    load(id) {
        this.jobOfferService.find(id)
            .subscribe((jobOfferResponse: HttpResponse<JobOffer>) => {
                this.jobOffer = jobOfferResponse.body;
            });
    }
    previousState() {
        window.history.back();
    }

    ngOnDestroy() {
        this.subscription.unsubscribe();
        this.eventManager.destroy(this.eventSubscriber);
    }

    registerChangeInJobOffers() {
        this.eventSubscriber = this.eventManager.subscribe(
            'jobOfferListModification',
            (response) => this.load(this.jobOffer.id)
        );
    }
}
