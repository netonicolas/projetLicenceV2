import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { ActivatedRoute } from '@angular/router';
import { Subscription } from 'rxjs/Subscription';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { SkillTestResponse } from './skill-test-response.model';
import { SkillTestResponseService } from './skill-test-response.service';
import { Principal } from '../../shared';

@Component({
    selector: 'jhi-skill-test-response',
    templateUrl: './skill-test-response.component.html'
})
export class SkillTestResponseComponent implements OnInit, OnDestroy {
skillTestResponses: SkillTestResponse[];
    currentAccount: any;
    eventSubscriber: Subscription;
    currentSearch: string;

    constructor(
        private skillTestResponseService: SkillTestResponseService,
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
            this.skillTestResponseService.search({
                query: this.currentSearch,
                }).subscribe(
                    (res: HttpResponse<SkillTestResponse[]>) => this.skillTestResponses = res.body,
                    (res: HttpErrorResponse) => this.onError(res.message)
                );
            return;
       }
        this.skillTestResponseService.query().subscribe(
            (res: HttpResponse<SkillTestResponse[]>) => {
                this.skillTestResponses = res.body;
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
        this.registerChangeInSkillTestResponses();
    }

    ngOnDestroy() {
        this.eventManager.destroy(this.eventSubscriber);
    }

    trackId(index: number, item: SkillTestResponse) {
        return item.id;
    }
    registerChangeInSkillTestResponses() {
        this.eventSubscriber = this.eventManager.subscribe('skillTestResponseListModification', (response) => this.loadAll());
    }

    private onError(error) {
        this.jhiAlertService.error(error.message, null, null);
    }
}
