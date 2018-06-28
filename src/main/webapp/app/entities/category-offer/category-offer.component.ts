import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { ActivatedRoute } from '@angular/router';
import { Subscription } from 'rxjs/Subscription';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { CategoryOffer } from './category-offer.model';
import { CategoryOfferService } from './category-offer.service';
import { Principal } from '../../shared';

@Component({
    selector: 'jhi-category-offer',
    templateUrl: './category-offer.component.html'
})
export class CategoryOfferComponent implements OnInit, OnDestroy {
categoryOffers: CategoryOffer[];
    currentAccount: any;
    eventSubscriber: Subscription;
    currentSearch: string;

    constructor(
        private categoryOfferService: CategoryOfferService,
        private jhiAlertService: JhiAlertService,
        private eventManager: JhiEventManager,
        private activatedRoute: ActivatedRoute,
        private principal: Principal
    ) {
        this.currentSearch = this.activatedRoute.snapshot && this.activatedRoute.snapshot.params['search'] ?
            this.activatedRoute.snapshot.params['search'] : '';
    }

    loadAll() {
        if (this.currentSearch) {
            this.categoryOfferService.search({
                query: this.currentSearch,
                }).subscribe(
                    (res: HttpResponse<CategoryOffer[]>) => this.categoryOffers = res.body,
                    (res: HttpErrorResponse) => this.onError(res.message)
                );
            return;
       }
        this.categoryOfferService.query().subscribe(
            (res: HttpResponse<CategoryOffer[]>) => {
                this.categoryOffers = res.body;
                this.currentSearch = '';
            },
            (res: HttpErrorResponse) => this.onError(res.message)
        );
    }

    search(query) {
        if (!query) {
            return this.clear();
        }
        this.currentSearch = query;
        this.loadAll();
    }

    clear() {
        this.currentSearch = '';
        this.loadAll();
    }
    ngOnInit() {
        this.loadAll();
        this.principal.identity().then((account) => {
            this.currentAccount = account;
        });
        this.registerChangeInCategoryOffers();
    }

    ngOnDestroy() {
        this.eventManager.destroy(this.eventSubscriber);
    }

    trackId(index: number, item: CategoryOffer) {
        return item.id;
    }
    registerChangeInCategoryOffers() {
        this.eventSubscriber = this.eventManager.subscribe('categoryOfferListModification', (response) => this.loadAll());
    }

    private onError(error) {
        this.jhiAlertService.error(error.message, null, null);
    }
}
