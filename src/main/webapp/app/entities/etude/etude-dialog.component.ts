import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';

import { Observable } from 'rxjs/Observable';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { Etude } from './etude.model';
import { EtudePopupService } from './etude-popup.service';
import { EtudeService } from './etude.service';

@Component({
    selector: 'jhi-etude-dialog',
    templateUrl: './etude-dialog.component.html'
})
export class EtudeDialogComponent implements OnInit {

    etude: Etude;
    isSaving: boolean;

    constructor(
        public activeModal: NgbActiveModal,
        private etudeService: EtudeService,
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
        if (this.etude.id !== undefined) {
            this.subscribeToSaveResponse(
                this.etudeService.update(this.etude));
        } else {
            this.subscribeToSaveResponse(
                this.etudeService.create(this.etude));
        }
    }

    private subscribeToSaveResponse(result: Observable<HttpResponse<Etude>>) {
        result.subscribe((res: HttpResponse<Etude>) =>
            this.onSaveSuccess(res.body), (res: HttpErrorResponse) => this.onSaveError());
    }

    private onSaveSuccess(result: Etude) {
        this.eventManager.broadcast({ name: 'etudeListModification', content: 'OK'});
        this.isSaving = false;
        this.activeModal.dismiss(result);
    }

    private onSaveError() {
        this.isSaving = false;
    }
}

@Component({
    selector: 'jhi-etude-popup',
    template: ''
})
export class EtudePopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private etudePopupService: EtudePopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            if ( params['id'] ) {
                this.etudePopupService
                    .open(EtudeDialogComponent as Component, params['id']);
            } else {
                this.etudePopupService
                    .open(EtudeDialogComponent as Component);
            }
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
