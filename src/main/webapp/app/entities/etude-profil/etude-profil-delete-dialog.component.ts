import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { EtudeProfil } from './etude-profil.model';
import { EtudeProfilPopupService } from './etude-profil-popup.service';
import { EtudeProfilService } from './etude-profil.service';

@Component({
    selector: 'jhi-etude-profil-delete-dialog',
    templateUrl: './etude-profil-delete-dialog.component.html'
})
export class EtudeProfilDeleteDialogComponent {

    etudeProfil: EtudeProfil;

    constructor(
        private etudeProfilService: EtudeProfilService,
        public activeModal: NgbActiveModal,
        private eventManager: JhiEventManager
    ) {
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.etudeProfilService.delete(id).subscribe((response) => {
            this.eventManager.broadcast({
                name: 'etudeProfilListModification',
                content: 'Deleted an etudeProfil'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-etude-profil-delete-popup',
    template: ''
})
export class EtudeProfilDeletePopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private etudeProfilPopupService: EtudeProfilPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            this.etudeProfilPopupService
                .open(EtudeProfilDeleteDialogComponent as Component, params['id']);
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
