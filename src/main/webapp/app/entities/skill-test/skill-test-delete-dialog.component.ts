import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { SkillTest } from './skill-test.model';
import { SkillTestPopupService } from './skill-test-popup.service';
import { SkillTestService } from './skill-test.service';

@Component({
    selector: 'jhi-skill-test-delete-dialog',
    templateUrl: './skill-test-delete-dialog.component.html'
})
export class SkillTestDeleteDialogComponent {

    skillTest: SkillTest;

    constructor(
        private skillTestService: SkillTestService,
        public activeModal: NgbActiveModal,
        private eventManager: JhiEventManager
    ) {
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.skillTestService.delete(id).subscribe((response) => {
            this.eventManager.broadcast({
                name: 'skillTestListModification',
                content: 'Deleted an skillTest'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-skill-test-delete-popup',
    template: ''
})
export class SkillTestDeletePopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private skillTestPopupService: SkillTestPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            this.skillTestPopupService
                .open(SkillTestDeleteDialogComponent as Component, params['id']);
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
