import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { ActivatedRoute } from '@angular/router';
import { Subscription } from 'rxjs/Subscription';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { EtudeProfil } from './etude-profil.model';
import { EtudeProfilService } from './etude-profil.service';
import { Principal } from '../../shared';

@Component({
    selector: 'jhi-etude-profil',
    templateUrl: './etude-profil.component.html'
})
export class EtudeProfilComponent implements OnInit, OnDestroy {
etudeProfils: EtudeProfil[];
    currentAccount: any;
    eventSubscriber: Subscription;
    currentSearch: string;

    constructor(
        private etudeProfilService: EtudeProfilService,
        private jhiAlertService: JhiAlertService,
        private eventManager: JhiEventManager,
        private activatedRoute: ActivatedRoute,
        private principal: Principal
    ) {
        this.currentSearch = this.activatedRoute.snapshot && this.activatedRoute.snapshot.params['search'] ?
            this.activatedRoute.snapshot.params['search'] : '';
    }

    loadAll() {
        if (this.currentSearch) {
            this.etudeProfilService.search({
                query: this.currentSearch,
                }).subscribe(
                    (res: HttpResponse<EtudeProfil[]>) => this.etudeProfils = res.body,
                    (res: HttpErrorResponse) => this.onError(res.message)
                );
            return;
       }
        this.etudeProfilService.query().subscribe(
            (res: HttpResponse<EtudeProfil[]>) => {
                this.etudeProfils = res.body;
                this.currentSearch = '';
            },
            (res: HttpErrorResponse) => this.onError(res.message)
        );
    }

    search(query) {
        if (!query) {
            return this.clear();
        }
        this.currentSearch = query;
        this.loadAll();
    }

    clear() {
        this.currentSearch = '';
        this.loadAll();
    }
    ngOnInit() {
        this.loadAll();
        this.principal.identity().then((account) => {
            this.currentAccount = account;
        });
        this.registerChangeInEtudeProfils();
    }

    ngOnDestroy() {
        this.eventManager.destroy(this.eventSubscriber);
    }

    trackId(index: number, item: EtudeProfil) {
        return item.id;
    }
    registerChangeInEtudeProfils() {
        this.eventSubscriber = this.eventManager.subscribe('etudeProfilListModification', (response) => this.loadAll());
    }

    private onError(error) {
        this.jhiAlertService.error(error.message, null, null);
    }
}
