import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs/Subscription';
import { JhiEventManager } from 'ng-jhipster';

import { SkillTestResponse } from './skill-test-response.model';
import { SkillTestResponseService } from './skill-test-response.service';

@Component({
    selector: 'jhi-skill-test-response-detail',
    templateUrl: './skill-test-response-detail.component.html'
})
export class SkillTestResponseDetailComponent implements OnInit, OnDestroy {

    skillTestResponse: SkillTestResponse;
    private subscription: Subscription;
    private eventSubscriber: Subscription;

    constructor(
        private eventManager: JhiEventManager,
        private skillTestResponseService: SkillTestResponseService,
        private route: ActivatedRoute
    ) {
    }

    ngOnInit() {
        this.subscription = this.route.params.subscribe((params) => {
            this.load(params['id']);
        });
        this.registerChangeInSkillTestResponses();
    }

    load(id) {
        this.skillTestResponseService.find(id)
            .subscribe((skillTestResponseResponse: HttpResponse<SkillTestResponse>) => {
                this.skillTestResponse = skillTestResponseResponse.body;
            });
    }
    previousState() {
        window.history.back();
    }

    ngOnDestroy() {
        this.subscription.unsubscribe();
        this.eventManager.destroy(this.eventSubscriber);
    }

    registerChangeInSkillTestResponses() {
        this.eventSubscriber = this.eventManager.subscribe(
            'skillTestResponseListModification',
            (response) => this.load(this.skillTestResponse.id)
        );
    }
}
