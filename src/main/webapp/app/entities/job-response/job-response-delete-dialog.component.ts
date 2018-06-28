import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { JobResponse } from './job-response.model';
import { JobResponsePopupService } from './job-response-popup.service';
import { JobResponseService } from './job-response.service';

@Component({
    selector: 'jhi-job-response-delete-dialog',
    templateUrl: './job-response-delete-dialog.component.html'
})
export class JobResponseDeleteDialogComponent {

    jobResponse: JobResponse;

    constructor(
        private jobResponseService: JobResponseService,
        public activeModal: NgbActiveModal,
        private eventManager: JhiEventManager
    ) {
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.jobResponseService.delete(id).subscribe((response) => {
            this.eventManager.broadcast({
                name: 'jobResponseListModification',
                content: 'Deleted an jobResponse'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-job-response-delete-popup',
    template: ''
})
export class JobResponseDeletePopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private jobResponsePopupService: JobResponsePopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            this.jobResponsePopupService
                .open(JobResponseDeleteDialogComponent as Component, params['id']);
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
