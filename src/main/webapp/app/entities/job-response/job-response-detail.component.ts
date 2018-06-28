import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs/Subscription';
import { JhiEventManager } from 'ng-jhipster';

import { JobResponse } from './job-response.model';
import { JobResponseService } from './job-response.service';

@Component({
    selector: 'jhi-job-response-detail',
    templateUrl: './job-response-detail.component.html'
})
export class JobResponseDetailComponent implements OnInit, OnDestroy {

    jobResponse: JobResponse;
    private subscription: Subscription;
    private eventSubscriber: Subscription;

    constructor(
        private eventManager: JhiEventManager,
        private jobResponseService: JobResponseService,
        private route: ActivatedRoute
    ) {
    }

    ngOnInit() {
        this.subscription = this.route.params.subscribe((params) => {
            this.load(params['id']);
        });
        this.registerChangeInJobResponses();
    }

    load(id) {
        this.jobResponseService.find(id)
            .subscribe((jobResponseResponse: HttpResponse<JobResponse>) => {
                this.jobResponse = jobResponseResponse.body;
            });
    }
    previousState() {
        window.history.back();
    }

    ngOnDestroy() {
        this.subscription.unsubscribe();
        this.eventManager.destroy(this.eventSubscriber);
    }

    registerChangeInJobResponses() {
        this.eventSubscriber = this.eventManager.subscribe(
            'jobResponseListModification',
            (response) => this.load(this.jobResponse.id)
        );
    }
}
