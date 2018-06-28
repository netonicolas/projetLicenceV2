import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { CandidateTrueSkill } from './candidate-true-skill.model';
import { CandidateTrueSkillPopupService } from './candidate-true-skill-popup.service';
import { CandidateTrueSkillService } from './candidate-true-skill.service';

@Component({
    selector: 'jhi-candidate-true-skill-delete-dialog',
    templateUrl: './candidate-true-skill-delete-dialog.component.html'
})
export class CandidateTrueSkillDeleteDialogComponent {

    candidateTrueSkill: CandidateTrueSkill;

    constructor(
        private candidateTrueSkillService: CandidateTrueSkillService,
        public activeModal: NgbActiveModal,
        private eventManager: JhiEventManager
    ) {
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.candidateTrueSkillService.delete(id).subscribe((response) => {
            this.eventManager.broadcast({
                name: 'candidateTrueSkillListModification',
                content: 'Deleted an candidateTrueSkill'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-candidate-true-skill-delete-popup',
    template: ''
})
export class CandidateTrueSkillDeletePopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private candidateTrueSkillPopupService: CandidateTrueSkillPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            this.candidateTrueSkillPopupService
                .open(CandidateTrueSkillDeleteDialogComponent as Component, params['id']);
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
