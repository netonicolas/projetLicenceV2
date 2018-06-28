import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { SkillQuestion } from './skill-question.model';
import { SkillQuestionPopupService } from './skill-question-popup.service';
import { SkillQuestionService } from './skill-question.service';

@Component({
    selector: 'jhi-skill-question-delete-dialog',
    templateUrl: './skill-question-delete-dialog.component.html'
})
export class SkillQuestionDeleteDialogComponent {

    skillQuestion: SkillQuestion;

    constructor(
        private skillQuestionService: SkillQuestionService,
        public activeModal: NgbActiveModal,
        private eventManager: JhiEventManager
    ) {
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.skillQuestionService.delete(id).subscribe((response) => {
            this.eventManager.broadcast({
                name: 'skillQuestionListModification',
                content: 'Deleted an skillQuestion'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-skill-question-delete-popup',
    template: ''
})
export class SkillQuestionDeletePopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private skillQuestionPopupService: SkillQuestionPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            this.skillQuestionPopupService
                .open(SkillQuestionDeleteDialogComponent as Component, params['id']);
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
