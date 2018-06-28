import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';

import { Observable } from 'rxjs/Observable';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { Profil } from './profil.model';
import { ProfilPopupService } from './profil-popup.service';
import { ProfilService } from './profil.service';

@Component({
    selector: 'jhi-profil-dialog',
    templateUrl: './profil-dialog.component.html'
})
export class ProfilDialogComponent implements OnInit {

    profil: Profil;
    isSaving: boolean;

    constructor(
        public activeModal: NgbActiveModal,
        private profilService: ProfilService,
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
        if (this.profil.id !== undefined) {
            this.subscribeToSaveResponse(
                this.profilService.update(this.profil));
        } else {
            this.subscribeToSaveResponse(
                this.profilService.create(this.profil));
        }
    }

    private subscribeToSaveResponse(result: Observable<HttpResponse<Profil>>) {
        result.subscribe((res: HttpResponse<Profil>) =>
            this.onSaveSuccess(res.body), (res: HttpErrorResponse) => this.onSaveError());
    }

    private onSaveSuccess(result: Profil) {
        this.eventManager.broadcast({ name: 'profilListModification', content: 'OK'});
        this.isSaving = false;
        this.activeModal.dismiss(result);
    }

    private onSaveError() {
        this.isSaving = false;
    }
}

@Component({
    selector: 'jhi-profil-popup',
    template: ''
})
export class ProfilPopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private profilPopupService: ProfilPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            if ( params['id'] ) {
                this.profilPopupService
                    .open(ProfilDialogComponent as Component, params['id']);
            } else {
                this.profilPopupService
                    .open(ProfilDialogComponent as Component);
            }
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
