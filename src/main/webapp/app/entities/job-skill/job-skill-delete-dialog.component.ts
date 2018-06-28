import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { JobSkill } from './job-skill.model';
import { JobSkillPopupService } from './job-skill-popup.service';
import { JobSkillService } from './job-skill.service';

@Component({
    selector: 'jhi-job-skill-delete-dialog',
    templateUrl: './job-skill-delete-dialog.component.html'
})
export class JobSkillDeleteDialogComponent {

    jobSkill: JobSkill;

    constructor(
        private jobSkillService: JobSkillService,
        public activeModal: NgbActiveModal,
        private eventManager: JhiEventManager
    ) {
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.jobSkillService.delete(id).subscribe((response) => {
            this.eventManager.broadcast({
                name: 'jobSkillListModification',
                content: 'Deleted an jobSkill'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-job-skill-delete-popup',
    template: ''
})
export class JobSkillDeletePopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private jobSkillPopupService: JobSkillPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            this.jobSkillPopupService
                .open(JobSkillDeleteDialogComponent as Component, params['id']);
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
