import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';

import { Observable } from 'rxjs/Observable';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { CandidateSkill } from './candidate-skill.model';
import { CandidateSkillPopupService } from './candidate-skill-popup.service';
import { CandidateSkillService } from './candidate-skill.service';
import { Profil, ProfilService } from '../profil';
import { Skill, SkillService } from '../skill';

@Component({
    selector: 'jhi-candidate-skill-dialog',
    templateUrl: './candidate-skill-dialog.component.html'
})
export class CandidateSkillDialogComponent implements OnInit {

    candidateSkill: CandidateSkill;
    isSaving: boolean;

    profils: Profil[];

    skills: Skill[];

    constructor(
        public activeModal: NgbActiveModal,
        private jhiAlertService: JhiAlertService,
        private candidateSkillService: CandidateSkillService,
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
        if (this.candidateSkill.id !== undefined) {
            this.subscribeToSaveResponse(
                this.candidateSkillService.update(this.candidateSkill));
        } else {
            this.subscribeToSaveResponse(
                this.candidateSkillService.create(this.candidateSkill));
        }
    }

    private subscribeToSaveResponse(result: Observable<HttpResponse<CandidateSkill>>) {
        result.subscribe((res: HttpResponse<CandidateSkill>) =>
            this.onSaveSuccess(res.body), (res: HttpErrorResponse) => this.onSaveError());
    }

    private onSaveSuccess(result: CandidateSkill) {
        this.eventManager.broadcast({ name: 'candidateSkillListModification', content: 'OK'});
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
    selector: 'jhi-candidate-skill-popup',
    template: ''
})
export class CandidateSkillPopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private candidateSkillPopupService: CandidateSkillPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            if ( params['id'] ) {
                this.candidateSkillPopupService
                    .open(CandidateSkillDialogComponent as Component, params['id']);
            } else {
                this.candidateSkillPopupService
                    .open(CandidateSkillDialogComponent as Component);
            }
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
