import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs/Subscription';
import { JhiEventManager } from 'ng-jhipster';

import { CategoryOffer } from './category-offer.model';
import { CategoryOfferService } from './category-offer.service';

@Component({
    selector: 'jhi-category-offer-detail',
    templateUrl: './category-offer-detail.component.html'
})
export class CategoryOfferDetailComponent implements OnInit, OnDestroy {

    categoryOffer: CategoryOffer;
    private subscription: Subscription;
    private eventSubscriber: Subscription;

    constructor(
        private eventManager: JhiEventManager,
        private categoryOfferService: CategoryOfferService,
        private route: ActivatedRoute
    ) {
    }

    ngOnInit() {
        this.subscription = this.route.params.subscribe((params) => {
            this.load(params['id']);
        });
        this.registerChangeInCategoryOffers();
    }

    load(id) {
        this.categoryOfferService.find(id)
            .subscribe((categoryOfferResponse: HttpResponse<CategoryOffer>) => {
                this.categoryOffer = categoryOfferResponse.body;
            });
    }
    previousState() {
        window.history.back();
    }

    ngOnDestroy() {
        this.subscription.unsubscribe();
        this.eventManager.destroy(this.eventSubscriber);
    }

    registerChangeInCategoryOffers() {
        this.eventSubscriber = this.eventManager.subscribe(
            'categoryOfferListModification',
            (response) => this.load(this.categoryOffer.id)
        );
    }
}
