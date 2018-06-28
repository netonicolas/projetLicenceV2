import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs/Subscription';
import { JhiEventManager } from 'ng-jhipster';

import { SkillQuestion } from './skill-question.model';
import { SkillQuestionService } from './skill-question.service';

@Component({
    selector: 'jhi-skill-question-detail',
    templateUrl: './skill-question-detail.component.html'
})
export class SkillQuestionDetailComponent implements OnInit, OnDestroy {

    skillQuestion: SkillQuestion;
    private subscription: Subscription;
    private eventSubscriber: Subscription;

    constructor(
        private eventManager: JhiEventManager,
        private skillQuestionService: SkillQuestionService,
        private route: ActivatedRoute
    ) {
    }

    ngOnInit() {
        this.subscription = this.route.params.subscribe((params) => {
            this.load(params['id']);
        });
        this.registerChangeInSkillQuestions();
    }

    load(id) {
        this.skillQuestionService.find(id)
            .subscribe((skillQuestionResponse: HttpResponse<SkillQuestion>) => {
                this.skillQuestion = skillQuestionResponse.body;
            });
    }
    previousState() {
        window.history.back();
    }

    ngOnDestroy() {
        this.subscription.unsubscribe();
        this.eventManager.destroy(this.eventSubscriber);
    }

    registerChangeInSkillQuestions() {
        this.eventSubscriber = this.eventManager.subscribe(
            'skillQuestionListModification',
            (response) => this.load(this.skillQuestion.id)
        );
    }
}
