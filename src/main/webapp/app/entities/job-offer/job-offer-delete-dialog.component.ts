import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { JobOffer } from './job-offer.model';
import { JobOfferPopupService } from './job-offer-popup.service';
import { JobOfferService } from './job-offer.service';

@Component({
    selector: 'jhi-job-offer-delete-dialog',
    templateUrl: './job-offer-delete-dialog.component.html'
})
export class JobOfferDeleteDialogComponent {

    jobOffer: JobOffer;

    constructor(
        private jobOfferService: JobOfferService,
        public activeModal: NgbActiveModal,
        private eventManager: JhiEventManager
    ) {
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.jobOfferService.delete(id).subscribe((response) => {
            this.eventManager.broadcast({
                name: 'jobOfferListModification',
                content: 'Deleted an jobOffer'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-job-offer-delete-popup',
    template: ''
})
export class JobOfferDeletePopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private jobOfferPopupService: JobOfferPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            this.jobOfferPopupService
                .open(JobOfferDeleteDialogComponent as Component, params['id']);
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
