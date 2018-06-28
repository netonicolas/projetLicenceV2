import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';

import { Observable } from 'rxjs/Observable';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { CandidateTrueSkill } from './candidate-true-skill.model';
import { CandidateTrueSkillPopupService } from './candidate-true-skill-popup.service';
import { CandidateTrueSkillService } from './candidate-true-skill.service';
import { Profil, ProfilService } from '../profil';
import { Skill, SkillService } from '../skill';

@Component({
    selector: 'jhi-candidate-true-skill-dialog',
    templateUrl: './candidate-true-skill-dialog.component.html'
})
export class CandidateTrueSkillDialogComponent implements OnInit {

    candidateTrueSkill: CandidateTrueSkill;
    isSaving: boolean;

    profils: Profil[];

    skills: Skill[];

    constructor(
        public activeModal: NgbActiveModal,
        private jhiAlertService: JhiAlertService,
        private candidateTrueSkillService: CandidateTrueSkillService,
        private profilService: ProfilService,
        private skillService: SkillService,
        private eventManager: JhiEventManager
    ) {
    }

    ngOnInit() {
        this.isSaving = false;
        this.profilService.query()
            .subscribe((res: HttpResponse<Profil[]>) => { this.profils = res.body; }, (res: HttpErrorResponse) => this.onError(res.message));
        this.skillService.query()
            .subscribe((res: HttpResponse<Skill[]>) => { this.skills = res.body; }, (res: HttpErrorResponse) => this.onError(res.message));
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    save() {
        this.isSaving = true;
        if (this.candidateTrueSkill.id !== undefined) {
            this.subscribeToSaveResponse(
                this.candidateTrueSkillService.update(this.candidateTrueSkill));
        } else {
            this.subscribeToSaveResponse(
                this.candidateTrueSkillService.create(this.candidateTrueSkill));
        }
    }

    private subscribeToSaveResponse(result: Observable<HttpResponse<CandidateTrueSkill>>) {
        result.subscribe((res: HttpResponse<CandidateTrueSkill>) =>
            this.onSaveSuccess(res.body), (res: HttpErrorResponse) => this.onSaveError());
    }

    private onSaveSuccess(result: CandidateTrueSkill) {
        this.eventManager.broadcast({ name: 'candidateTrueSkillListModification', content: 'OK'});
        this.isSaving = false;
        this.activeModal.dismiss(result);
    }

    private onSaveError() {
        this.isSaving = false;
    }

    private onError(error: any) {
        this.jhiAlertService.error(error.message, null, null);
    }

    trackProfilById(index: number, item: Profil) {
        return item.id;
    }

    trackSkillById(index: number, item: Skill) {
        return item.id;
    }
}

@Component({
    selector: 'jhi-candidate-true-skill-popup',
    template: ''
})
export class CandidateTrueSkillPopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private candidateTrueSkillPopupService: CandidateTrueSkillPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            if ( params['id'] ) {
                this.candidateTrueSkillPopupService
                    .open(CandidateTrueSkillDialogComponent as Component, params['id']);
            } else {
                this.candidateTrueSkillPopupService
                    .open(CandidateTrueSkillDialogComponent as Component);
            }
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
