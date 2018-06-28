import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { SkillTestResponse } from './skill-test-response.model';
import { SkillTestResponsePopupService } from './skill-test-response-popup.service';
import { SkillTestResponseService } from './skill-test-response.service';

@Component({
    selector: 'jhi-skill-test-response-delete-dialog',
    templateUrl: './skill-test-response-delete-dialog.component.html'
})
export class SkillTestResponseDeleteDialogComponent {

    skillTestResponse: SkillTestResponse;

    constructor(
        private skillTestResponseService: SkillTestResponseService,
        public activeModal: NgbActiveModal,
        private eventManager: JhiEventManager
    ) {
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.skillTestResponseService.delete(id).subscribe((response) => {
            this.eventManager.broadcast({
                name: 'skillTestResponseListModification',
                content: 'Deleted an skillTestResponse'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-skill-test-response-delete-popup',
    template: ''
})
export class SkillTestResponseDeletePopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private skillTestResponsePopupService: SkillTestResponsePopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            this.skillTestResponsePopupService
                .open(SkillTestResponseDeleteDialogComponent as Component, params['id']);
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
