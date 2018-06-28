import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs/Subscription';
import { JhiEventManager } from 'ng-jhipster';

import { Experience } from './experience.model';
import { ExperienceService } from './experience.service';

@Component({
    selector: 'jhi-experience-detail',
    templateUrl: './experience-detail.component.html'
})
export class ExperienceDetailComponent implements OnInit, OnDestroy {

    experience: Experience;
    private subscription: Subscription;
    private eventSubscriber: Subscription;

    constructor(
        private eventManager: JhiEventManager,
        private experienceService: ExperienceService,
        private route: ActivatedRoute
    ) {
    }

    ngOnInit() {
        this.subscription = this.route.params.subscribe((params) => {
            this.load(params['id']);
        });
        this.registerChangeInExperiences();
    }

    load(id) {
        this.experienceService.find(id)
            .subscribe((experienceResponse: HttpResponse<Experience>) => {
                this.experience = experienceResponse.body;
            });
    }
    previousState() {
        window.history.back();
    }

    ngOnDestroy() {
        this.subscription.unsubscribe();
        this.eventManager.destroy(this.eventSubscriber);
    }

    registerChangeInExperiences() {
        this.eventSubscriber = this.eventManager.subscribe(
            'experienceListModification',
            (response) => this.load(this.experience.id)
        );
    }
}
