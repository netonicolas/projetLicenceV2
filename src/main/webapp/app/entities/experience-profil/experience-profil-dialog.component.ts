import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';

import { Observable } from 'rxjs/Observable';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { ExperienceProfil } from './experience-profil.model';
import { ExperienceProfilPopupService } from './experience-profil-popup.service';
import { ExperienceProfilService } from './experience-profil.service';
import { Profil, ProfilService } from '../profil';
import { Experience, ExperienceService } from '../experience';

@Component({
    selector: 'jhi-experience-profil-dialog',
    templateUrl: './experience-profil-dialog.component.html'
})
export class ExperienceProfilDialogComponent implements OnInit {

    experienceProfil: ExperienceProfil;
    isSaving: boolean;

    profils: Profil[];

    experiences: Experience[];
    anneExperienceDebutDp: any;
    anneExperienceFinDp: any;

    constructor(
        public activeModal: NgbActiveModal,
        private jhiAlertService: JhiAlertService,
        private experienceProfilService: ExperienceProfilService,
        private profilService: ProfilService,
        private experienceService: ExperienceService,
        private eventManager: JhiEventManager
    ) {
    }

    ngOnInit() {
        this.isSaving = false;
        this.profilService.query()
            .subscribe((res: HttpResponse<Profil[]>) => { this.profils = res.body; }, (res: HttpErrorResponse) => this.onError(res.message));
        this.experienceService.query()
            .subscribe((res: HttpResponse<Experience[]>) => { this.experiences = res.body; }, (res: HttpErrorResponse) => this.onError(res.message));
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    save() {
        this.isSaving = true;
        if (this.experienceProfil.id !== undefined) {
            this.subscribeToSaveResponse(
                this.experienceProfilService.update(this.experienceProfil));
        } else {
            this.subscribeToSaveResponse(
                this.experienceProfilService.create(this.experienceProfil));
        }
    }

    private subscribeToSaveResponse(result: Observable<HttpResponse<ExperienceProfil>>) {
        result.subscribe((res: HttpResponse<ExperienceProfil>) =>
            this.onSaveSuccess(res.body), (res: HttpErrorResponse) => this.onSaveError());
    }

    private onSaveSuccess(result: ExperienceProfil) {
        this.eventManager.broadcast({ name: 'experienceProfilListModification', content: 'OK'});
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

    trackExperienceById(index: number, item: Experience) {
        return item.id;
    }
}

@Component({
    selector: 'jhi-experience-profil-popup',
    template: ''
})
export class ExperienceProfilPopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private experienceProfilPopupService: ExperienceProfilPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            if ( params['id'] ) {
                this.experienceProfilPopupService
                    .open(ExperienceProfilDialogComponent as Component, params['id']);
            } else {
                this.experienceProfilPopupService
                    .open(ExperienceProfilDialogComponent as Component);
            }
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
