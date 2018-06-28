import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs/Subscription';
import { JhiEventManager } from 'ng-jhipster';

import { SkillTest } from './skill-test.model';
import { SkillTestService } from './skill-test.service';

@Component({
    selector: 'jhi-skill-test-detail',
    templateUrl: './skill-test-detail.component.html'
})
export class SkillTestDetailComponent implements OnInit, OnDestroy {

    skillTest: SkillTest;
    private subscription: Subscription;
    private eventSubscriber: Subscription;

    constructor(
        private eventManager: JhiEventManager,
        private skillTestService: SkillTestService,
        private route: ActivatedRoute
    ) {
    }

    ngOnInit() {
        this.subscription = this.route.params.subscribe((params) => {
            this.load(params['id']);
        });
        this.registerChangeInSkillTests();
    }

    load(id) {
        this.skillTestService.find(id)
            .subscribe((skillTestResponse: HttpResponse<SkillTest>) => {
                this.skillTest = skillTestResponse.body;
            });
    }
    previousState() {
        window.history.back();
    }

    ngOnDestroy() {
        this.subscription.unsubscribe();
        this.eventManager.destroy(this.eventSubscriber);
    }

    registerChangeInSkillTests() {
        this.eventSubscriber = this.eventManager.subscribe(
            'skillTestListModification',
            (response) => this.load(this.skillTest.id)
        );
    }
}
