import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs/Subscription';
import { JhiEventManager } from 'ng-jhipster';

import { JobSkill } from './job-skill.model';
import { JobSkillService } from './job-skill.service';

@Component({
    selector: 'jhi-job-skill-detail',
    templateUrl: './job-skill-detail.component.html'
})
export class JobSkillDetailComponent implements OnInit, OnDestroy {

    jobSkill: JobSkill;
    private subscription: Subscription;
    private eventSubscriber: Subscription;

    constructor(
        private eventManager: JhiEventManager,
        private jobSkillService: JobSkillService,
        private route: ActivatedRoute
    ) {
    }

    ngOnInit() {
        this.subscription = this.route.params.subscribe((params) => {
            this.load(params['id']);
        });
        this.registerChangeInJobSkills();
    }

    load(id) {
        this.jobSkillService.find(id)
            .subscribe((jobSkillResponse: HttpResponse<JobSkill>) => {
                this.jobSkill = jobSkillResponse.body;
            });
    }
    previousState() {
        window.history.back();
    }

    ngOnDestroy() {
        this.subscription.unsubscribe();
        this.eventManager.destroy(this.eventSubscriber);
    }

    registerChangeInJobSkills() {
        this.eventSubscriber = this.eventManager.subscribe(
            'jobSkillListModification',
            (response) => this.load(this.jobSkill.id)
        );
    }
}
