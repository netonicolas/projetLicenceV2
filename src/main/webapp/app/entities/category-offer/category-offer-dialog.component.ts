import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';

import { Observable } from 'rxjs/Observable';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { CategoryOffer } from './category-offer.model';
import { CategoryOfferPopupService } from './category-offer-popup.service';
import { CategoryOfferService } from './category-offer.service';

@Component({
    selector: 'jhi-category-offer-dialog',
    templateUrl: './category-offer-dialog.component.html'
})
export class CategoryOfferDialogComponent implements OnInit {

    categoryOffer: CategoryOffer;
    isSaving: boolean;

    constructor(
        public activeModal: NgbActiveModal,
        private categoryOfferService: CategoryOfferService,
        private eventManager: JhiEventManager
    ) {
    }

    ngOnInit() {
        this.isSaving = false;
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    save() {
        this.isSaving = true;
        if (this.categoryOffer.id !== undefined) {
            this.subscribeToSaveResponse(
                this.categoryOfferService.update(this.categoryOffer));
        } else {
            this.subscribeToSaveResponse(
                this.categoryOfferService.create(this.categoryOffer));
        }
    }

    private subscribeToSaveResponse(result: Observable<HttpResponse<CategoryOffer>>) {
        result.subscribe((res: HttpResponse<CategoryOffer>) =>
            this.onSaveSuccess(res.body), (res: HttpErrorResponse) => this.onSaveError());
    }

    private onSaveSuccess(result: CategoryOffer) {
        this.eventManager.broadcast({ name: 'categoryOfferListModification', content: 'OK'});
        this.isSaving = false;
        this.activeModal.dismiss(result);
    }

    private onSaveError() {
        this.isSaving = false;
    }
}

@Component({
    selector: 'jhi-category-offer-popup',
    template: ''
})
export class CategoryOfferPopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private categoryOfferPopupService: CategoryOfferPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            if ( params['id'] ) {
                this.categoryOfferPopupService
                    .open(CategoryOfferDialogComponent as Component, params['id']);
            } else {
                this.categoryOfferPopupService
                    .open(CategoryOfferDialogComponent as Component);
            }
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
