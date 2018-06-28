import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';

import { Observable } from 'rxjs/Observable';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { EtudeProfil } from './etude-profil.model';
import { EtudeProfilPopupService } from './etude-profil-popup.service';
import { EtudeProfilService } from './etude-profil.service';
import { Profil, ProfilService } from '../profil';
import { Etude, EtudeService } from '../etude';

@Component({
    selector: 'jhi-etude-profil-dialog',
    templateUrl: './etude-profil-dialog.component.html'
})
export class EtudeProfilDialogComponent implements OnInit {

    etudeProfil: EtudeProfil;
    isSaving: boolean;

    profils: Profil[];

    etudes: Etude[];
    anneeEtudeDebutDp: any;
    anneEtudeFinDp: any;

    constructor(
        public activeModal: NgbActiveModal,
        private jhiAlertService: JhiAlertService,
        private etudeProfilService: EtudeProfilService,
        private profilService: ProfilService,
        private etudeService: EtudeService,
        private eventManager: JhiEventManager
    ) {
    }

    ngOnInit() {
        this.isSaving = false;
        this.profilService.query()
            .subscribe((res: HttpResponse<Profil[]>) => { this.profils = res.body; }, (res: HttpErrorResponse) => this.onError(res.message));
        this.etudeService.query()
            .subscribe((res: HttpResponse<Etude[]>) => { this.etudes = res.body; }, (res: HttpErrorResponse) => this.onError(res.message));
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    save() {
        this.isSaving = true;
        if (this.etudeProfil.id !== undefined) {
            this.subscribeToSaveResponse(
                this.etudeProfilService.update(this.etudeProfil));
        } else {
            this.subscribeToSaveResponse(
                this.etudeProfilService.create(this.etudeProfil));
        }
    }

    private subscribeToSaveResponse(result: Observable<HttpResponse<EtudeProfil>>) {
        result.subscribe((res: HttpResponse<EtudeProfil>) =>
            this.onSaveSuccess(res.body), (res: HttpErrorResponse) => this.onSaveError());
    }

    private onSaveSuccess(result: EtudeProfil) {
        this.eventManager.broadcast({ name: 'etudeProfilListModification', content: 'OK'});
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

    trackEtudeById(index: number, item: Etude) {
        return item.id;
    }
}

@Component({
    selector: 'jhi-etude-profil-popup',
    template: ''
})
export class EtudeProfilPopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private etudeProfilPopupService: EtudeProfilPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            if ( params['id'] ) {
                this.etudeProfilPopupService
                    .open(EtudeProfilDialogComponent as Component, params['id']);
            } else {
                this.etudeProfilPopupService
                    .open(EtudeProfilDialogComponent as Component);
            }
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
