import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs/Subscription';
import { JhiEventManager } from 'ng-jhipster';

import { EtudeProfil } from './etude-profil.model';
import { EtudeProfilService } from './etude-profil.service';

@Component({
    selector: 'jhi-etude-profil-detail',
    templateUrl: './etude-profil-detail.component.html'
})
export class EtudeProfilDetailComponent implements OnInit, OnDestroy {

    etudeProfil: EtudeProfil;
    private subscription: Subscription;
    private eventSubscriber: Subscription;

    constructor(
        private eventManager: JhiEventManager,
        private etudeProfilService: EtudeProfilService,
        private route: ActivatedRoute
    ) {
    }

    ngOnInit() {
        this.subscription = this.route.params.subscribe((params) => {
            this.load(params['id']);
        });
        this.registerChangeInEtudeProfils();
    }

    load(id) {
        this.etudeProfilService.find(id)
            .subscribe((etudeProfilResponse: HttpResponse<EtudeProfil>) => {
                this.etudeProfil = etudeProfilResponse.body;
            });
    }
    previousState() {
        window.history.back();
    }

    ngOnDestroy() {
        this.subscription.unsubscribe();
        this.eventManager.destroy(this.eventSubscriber);
    }

    registerChangeInEtudeProfils() {
        this.eventSubscriber = this.eventManager.subscribe(
            'etudeProfilListModification',
            (response) => this.load(this.etudeProfil.id)
        );
    }
}
