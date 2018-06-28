import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';

import { Observable } from 'rxjs/Observable';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { Experience } from './experience.model';
import { ExperiencePopupService } from './experience-popup.service';
import { ExperienceService } from './experience.service';

@Component({
    selector: 'jhi-experience-dialog',
    templateUrl: './experience-dialog.component.html'
})
export class ExperienceDialogComponent implements OnInit {

    experience: Experience;
    isSaving: boolean;

    constructor(
        public activeModal: NgbActiveModal,
        private experienceService: ExperienceService,
        private eventManager: JhiEventManager
    ) {
    }

    ngOnInit() {
        this.isSaving = false;
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    save() {
        this.isSaving = true;
        if (this.experience.id !== undefined) {
            this.subscribeToSaveResponse(
                this.experienceService.update(this.experience));
        } else {
            this.subscribeToSaveResponse(
                this.experienceService.create(this.experience));
        }
    }

    private subscribeToSaveResponse(result: Observable<HttpResponse<Experience>>) {
        result.subscribe((res: HttpResponse<Experience>) =>
            this.onSaveSuccess(res.body), (res: HttpErrorResponse) => this.onSaveError());
    }

    private onSaveSuccess(result: Experience) {
        this.eventManager.broadcast({ name: 'experienceListModification', content: 'OK'});
        this.isSaving = false;
        this.activeModal.dismiss(result);
    }

    private onSaveError() {
        this.isSaving = false;
    }
}

@Component({
    selector: 'jhi-experience-popup',
    template: ''
})
export class ExperiencePopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private experiencePopupService: ExperiencePopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            if ( params['id'] ) {
                this.experiencePopupService
                    .open(ExperienceDialogComponent as Component, params['id']);
            } else {
                this.experiencePopupService
                    .open(ExperienceDialogComponent as Component);
            }
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
