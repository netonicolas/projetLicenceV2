import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs/Subscription';
import { JhiEventManager } from 'ng-jhipster';

import { CandidateTrueSkill } from './candidate-true-skill.model';
import { CandidateTrueSkillService } from './candidate-true-skill.service';

@Component({
    selector: 'jhi-candidate-true-skill-detail',
    templateUrl: './candidate-true-skill-detail.component.html'
})
export class CandidateTrueSkillDetailComponent implements OnInit, OnDestroy {

    candidateTrueSkill: CandidateTrueSkill;
    private subscription: Subscription;
    private eventSubscriber: Subscription;

    constructor(
        private eventManager: JhiEventManager,
        private candidateTrueSkillService: CandidateTrueSkillService,
        private route: ActivatedRoute
    ) {
    }

    ngOnInit() {
        this.subscription = this.route.params.subscribe((params) => {
            this.load(params['id']);
        });
        this.registerChangeInCandidateTrueSkills();
    }

    load(id) {
        this.candidateTrueSkillService.find(id)
            .subscribe((candidateTrueSkillResponse: HttpResponse<CandidateTrueSkill>) => {
                this.candidateTrueSkill = candidateTrueSkillResponse.body;
            });
    }
    previousState() {
        window.history.back();
    }

    ngOnDestroy() {
        this.subscription.unsubscribe();
        this.eventManager.destroy(this.eventSubscriber);
    }

    registerChangeInCandidateTrueSkills() {
        this.eventSubscriber = this.eventManager.subscribe(
            'candidateTrueSkillListModification',
            (response) => this.load(this.candidateTrueSkill.id)
        );
    }
}
