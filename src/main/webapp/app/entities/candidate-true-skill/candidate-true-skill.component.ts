import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { ActivatedRoute } from '@angular/router';
import { Subscription } from 'rxjs/Subscription';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { CandidateTrueSkill } from './candidate-true-skill.model';
import { CandidateTrueSkillService } from './candidate-true-skill.service';
import { Principal } from '../../shared';

@Component({
    selector: 'jhi-candidate-true-skill',
    templateUrl: './candidate-true-skill.component.html'
})
export class CandidateTrueSkillComponent implements OnInit, OnDestroy {
candidateTrueSkills: CandidateTrueSkill[];
    currentAccount: any;
    eventSubscriber: Subscription;
    currentSearch: string;

    constructor(
        private candidateTrueSkillService: CandidateTrueSkillService,
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
            this.candidateTrueSkillService.search({
                query: this.currentSearch,
                }).subscribe(
                    (res: HttpResponse<CandidateTrueSkill[]>) => this.candidateTrueSkills = res.body,
                    (res: HttpErrorResponse) => this.onError(res.message)
                );
            return;
       }
        this.candidateTrueSkillService.query().subscribe(
            (res: HttpResponse<CandidateTrueSkill[]>) => {
                this.candidateTrueSkills = res.body;
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
        this.registerChangeInCandidateTrueSkills();
    }

    ngOnDestroy() {
        this.eventManager.destroy(this.eventSubscriber);
    }

    trackId(index: number, item: CandidateTrueSkill) {
        return item.id;
    }
    registerChangeInCandidateTrueSkills() {
        this.eventSubscriber = this.eventManager.subscribe('candidateTrueSkillListModification', (response) => this.loadAll());
    }

    private onError(error) {
        this.jhiAlertService.error(error.message, null, null);
    }
}
