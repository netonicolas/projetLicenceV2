import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs/Subscription';
import { JhiEventManager } from 'ng-jhipster';

import { CandidateSkill } from './candidate-skill.model';
import { CandidateSkillService } from './candidate-skill.service';

@Component({
    selector: 'jhi-candidate-skill-detail',
    templateUrl: './candidate-skill-detail.component.html'
})
export class CandidateSkillDetailComponent implements OnInit, OnDestroy {

    candidateSkill: CandidateSkill;
    private subscription: Subscription;
    private eventSubscriber: Subscription;

    constructor(
        private eventManager: JhiEventManager,
        private candidateSkillService: CandidateSkillService,
        private route: ActivatedRoute
    ) {
    }

    ngOnInit() {
        this.subscription = this.route.params.subscribe((params) => {
            this.load(params['id']);
        });
        this.registerChangeInCandidateSkills();
    }

    load(id) {
        this.candidateSkillService.find(id)
            .subscribe((candidateSkillResponse: HttpResponse<CandidateSkill>) => {
                this.candidateSkill = candidateSkillResponse.body;
            });
    }
    previousState() {
        window.history.back();
    }

    ngOnDestroy() {
        this.subscription.unsubscribe();
        this.eventManager.destroy(this.eventSubscriber);
    }

    registerChangeInCandidateSkills() {
        this.eventSubscriber = this.eventManager.subscribe(
            'candidateSkillListModification',
            (response) => this.load(this.candidateSkill.id)
        );
    }
}
