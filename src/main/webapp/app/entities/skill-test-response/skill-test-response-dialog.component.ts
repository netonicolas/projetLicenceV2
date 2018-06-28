import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';

import { Observable } from 'rxjs/Observable';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { SkillTestResponse } from './skill-test-response.model';
import { SkillTestResponsePopupService } from './skill-test-response-popup.service';
import { SkillTestResponseService } from './skill-test-response.service';
import { SkillTest, SkillTestService } from '../skill-test';
import { Profil, ProfilService } from '../profil';

@Component({
    selector: 'jhi-skill-test-response-dialog',
    templateUrl: './skill-test-response-dialog.component.html'
})
export class SkillTestResponseDialogComponent implements OnInit {

    skillTestResponse: SkillTestResponse;
    isSaving: boolean;

    skilltests: SkillTest[];

    profils: Profil[];
    dateDp: any;

    constructor(
        public activeModal: NgbActiveModal,
        private jhiAlertService: JhiAlertService,
        private skillTestResponseService: SkillTestResponseService,
        private skillTestService: SkillTestService,
        private profilService: ProfilService,
        private eventManager: JhiEventManager
    ) {
    }

    ngOnInit() {
        this.isSaving = false;
        this.skillTestService.query()
            .subscribe((res: HttpResponse<SkillTest[]>) => { this.skilltests = res.body; }, (res: HttpErrorResponse) => this.onError(res.message));
        this.profilService.query()
            .subscribe((res: HttpResponse<Profil[]>) => { this.profils = res.body; }, (res: HttpErrorResponse) => this.onError(res.message));
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    save() {
        this.isSaving = true;
        if (this.skillTestResponse.id !== undefined) {
            this.subscribeToSaveResponse(
                this.skillTestResponseService.update(this.skillTestResponse));
        } else {
            this.subscribeToSaveResponse(
                this.skillTestResponseService.create(this.skillTestResponse));
        }
    }

    private subscribeToSaveResponse(result: Observable<HttpResponse<SkillTestResponse>>) {
        result.subscribe((res: HttpResponse<SkillTestResponse>) =>
            this.onSaveSuccess(res.body), (res: HttpErrorResponse) => this.onSaveError());
    }

    private onSaveSuccess(result: SkillTestResponse) {
        this.eventManager.broadcast({ name: 'skillTestResponseListModification', content: 'OK'});
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

    trackProfilById(index: number, item: Profil) {
        return item.id;
    }
}

@Component({
    selector: 'jhi-skill-test-response-popup',
    template: ''
})
export class SkillTestResponsePopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private skillTestResponsePopupService: SkillTestResponsePopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            if ( params['id'] ) {
                this.skillTestResponsePopupService
                    .open(SkillTestResponseDialogComponent as Component, params['id']);
            } else {
                this.skillTestResponsePopupService
                    .open(SkillTestResponseDialogComponent as Component);
            }
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
