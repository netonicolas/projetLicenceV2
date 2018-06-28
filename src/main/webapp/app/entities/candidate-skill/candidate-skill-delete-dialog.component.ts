import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { CandidateSkill } from './candidate-skill.model';
import { CandidateSkillPopupService } from './candidate-skill-popup.service';
import { CandidateSkillService } from './candidate-skill.service';

@Component({
    selector: 'jhi-candidate-skill-delete-dialog',
    templateUrl: './candidate-skill-delete-dialog.component.html'
})
export class CandidateSkillDeleteDialogComponent {

    candidateSkill: CandidateSkill;

    constructor(
        private candidateSkillService: CandidateSkillService,
        public activeModal: NgbActiveModal,
        private eventManager: JhiEventManager
    ) {
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.candidateSkillService.delete(id).subscribe((response) => {
            this.eventManager.broadcast({
                name: 'candidateSkillListModification',
                content: 'Deleted an candidateSkill'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-candidate-skill-delete-popup',
    template: ''
})
export class CandidateSkillDeletePopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private candidateSkillPopupService: CandidateSkillPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            this.candidateSkillPopupService
                .open(CandidateSkillDeleteDialogComponent as Component, params['id']);
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
