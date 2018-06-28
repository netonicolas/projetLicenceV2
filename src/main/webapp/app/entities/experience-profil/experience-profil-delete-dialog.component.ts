import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { ExperienceProfil } from './experience-profil.model';
import { ExperienceProfilPopupService } from './experience-profil-popup.service';
import { ExperienceProfilService } from './experience-profil.service';

@Component({
    selector: 'jhi-experience-profil-delete-dialog',
    templateUrl: './experience-profil-delete-dialog.component.html'
})
export class ExperienceProfilDeleteDialogComponent {

    experienceProfil: ExperienceProfil;

    constructor(
        private experienceProfilService: ExperienceProfilService,
        public activeModal: NgbActiveModal,
        private eventManager: JhiEventManager
    ) {
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.experienceProfilService.delete(id).subscribe((response) => {
            this.eventManager.broadcast({
                name: 'experienceProfilListModification',
                content: 'Deleted an experienceProfil'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-experience-profil-delete-popup',
    template: ''
})
export class ExperienceProfilDeletePopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private experienceProfilPopupService: ExperienceProfilPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            this.experienceProfilPopupService
                .open(ExperienceProfilDeleteDialogComponent as Component, params['id']);
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
