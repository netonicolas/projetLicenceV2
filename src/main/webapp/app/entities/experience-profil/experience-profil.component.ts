import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { ActivatedRoute } from '@angular/router';
import { Subscription } from 'rxjs/Subscription';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { ExperienceProfil } from './experience-profil.model';
import { ExperienceProfilService } from './experience-profil.service';
import { Principal } from '../../shared';

@Component({
    selector: 'jhi-experience-profil',
    templateUrl: './experience-profil.component.html'
})
export class ExperienceProfilComponent implements OnInit, OnDestroy {
experienceProfils: ExperienceProfil[];
    currentAccount: any;
    eventSubscriber: Subscription;
    currentSearch: string;

    constructor(
        private experienceProfilService: ExperienceProfilService,
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
            this.experienceProfilService.search({
                query: this.currentSearch,
                }).subscribe(
                    (res: HttpResponse<ExperienceProfil[]>) => this.experienceProfils = res.body,
                    (res: HttpErrorResponse) => this.onError(res.message)
                );
            return;
       }
        this.experienceProfilService.query().subscribe(
            (res: HttpResponse<ExperienceProfil[]>) => {
                this.experienceProfils = res.body;
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
        this.registerChangeInExperienceProfils();
    }

    ngOnDestroy() {
        this.eventManager.destroy(this.eventSubscriber);
    }

    trackId(index: number, item: ExperienceProfil) {
        return item.id;
    }
    registerChangeInExperienceProfils() {
        this.eventSubscriber = this.eventManager.subscribe('experienceProfilListModification', (response) => this.loadAll());
    }

    private onError(error) {
        this.jhiAlertService.error(error.message, null, null);
    }
}
