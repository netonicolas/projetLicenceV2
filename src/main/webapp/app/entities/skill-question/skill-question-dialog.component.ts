import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';

import { Observable } from 'rxjs/Observable';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { SkillQuestion } from './skill-question.model';
import { SkillQuestionPopupService } from './skill-question-popup.service';
import { SkillQuestionService } from './skill-question.service';
import { SkillTest, SkillTestService } from '../skill-test';
import { Skill, SkillService } from '../skill';

@Component({
    selector: 'jhi-skill-question-dialog',
    templateUrl: './skill-question-dialog.component.html'
})
export class SkillQuestionDialogComponent implements OnInit {

    skillQuestion: SkillQuestion;
    isSaving: boolean;

    skilltests: SkillTest[];

    skills: Skill[];

    constructor(
        public activeModal: NgbActiveModal,
        private jhiAlertService: JhiAlertService,
        private skillQuestionService: SkillQuestionService,
        private skillTestService: SkillTestService,
        private skillService: SkillService,
        private eventManager: JhiEventManager
    ) {
    }

    ngOnInit() {
        this.isSaving = false;
        this.skillTestService.query()
            .subscribe((res: HttpResponse<SkillTest[]>) => { this.skilltests = res.body; }, (res: HttpErrorResponse) => this.onError(res.message));
        this.skillService.query()
            .subscribe((res: HttpResponse<Skill[]>) => { this.skills = res.body; }, (res: HttpErrorResponse) => this.onError(res.message));
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    save() {
        this.isSaving = true;
        if (this.skillQuestion.id !== undefined) {
            this.subscribeToSaveResponse(
                this.skillQuestionService.update(this.skillQuestion));
        } else {
            this.subscribeToSaveResponse(
                this.skillQuestionService.create(this.skillQuestion));
        }
    }

    private subscribeToSaveResponse(result: Observable<HttpResponse<SkillQuestion>>) {
        result.subscribe((res: HttpResponse<SkillQuestion>) =>
            this.onSaveSuccess(res.body), (res: HttpErrorResponse) => this.onSaveError());
    }

    private onSaveSuccess(result: SkillQuestion) {
        this.eventManager.broadcast({ name: 'skillQuestionListModification', content: 'OK'});
        this.isSaving = false;
        this.activeModal.dismiss(result);
    }

    private onSaveError() {
        this.isSaving = false;
    }

    private onError(error: any) {
        this.jhiAlertService.error(error.message, null, null);
    }

    trackSkillTestById(index: number, item: SkillTest) {
        return item.id;
    }

    trackSkillById(index: number, item: Skill) {
        return item.id;
    }
}

@Component({
    selector: 'jhi-skill-question-popup',
    template: ''
})
export class SkillQuestionPopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private skillQuestionPopupService: SkillQuestionPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            if ( params['id'] ) {
                this.skillQuestionPopupService
                    .open(SkillQuestionDialogComponent as Component, params['id']);
            } else {
                this.skillQuestionPopupService
                    .open(SkillQuestionDialogComponent as Component);
            }
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
