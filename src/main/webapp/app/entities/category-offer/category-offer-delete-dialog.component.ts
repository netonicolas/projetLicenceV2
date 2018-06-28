import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { CategoryOffer } from './category-offer.model';
import { CategoryOfferPopupService } from './category-offer-popup.service';
import { CategoryOfferService } from './category-offer.service';

@Component({
    selector: 'jhi-category-offer-delete-dialog',
    templateUrl: './category-offer-delete-dialog.component.html'
})
export class CategoryOfferDeleteDialogComponent {

    categoryOffer: CategoryOffer;

    constructor(
        private categoryOfferService: CategoryOfferService,
        public activeModal: NgbActiveModal,
        private eventManager: JhiEventManager
    ) {
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.categoryOfferService.delete(id).subscribe((response) => {
            this.eventManager.broadcast({
                name: 'categoryOfferListModification',
                content: 'Deleted an categoryOffer'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-category-offer-delete-popup',
    template: ''
})
export class CategoryOfferDeletePopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private categoryOfferPopupService: CategoryOfferPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            this.categoryOfferPopupService
                .open(CategoryOfferDeleteDialogComponent as Component, params['id']);
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
