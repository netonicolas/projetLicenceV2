import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { ActivatedRoute } from '@angular/router';
import { Subscription } from 'rxjs/Subscription';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { CandidateSkill } from './candidate-skill.model';
import { CandidateSkillService } from './candidate-skill.service';
import { Principal } from '../../shared';

@Component({
    selector: 'jhi-candidate-skill',
    templateUrl: './candidate-skill.component.html'
})
export class CandidateSkillComponent implements OnInit, OnDestroy {
candidateSkills: CandidateSkill[];
    currentAccount: any;
    eventSubscriber: Subscription;
    currentSearch: string;

    constructor(
        private candidateSkillService: CandidateSkillService,
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
            this.candidateSkillService.search({
                query: this.currentSearch,
                }).subscribe(
                    (res: HttpResponse<CandidateSkill[]>) => this.candidateSkills = res.body,
                    (res: HttpErrorResponse) => this.onError(res.message)
                );
            return;
       }
        this.candidateSkillService.query().subscribe(
            (res: HttpResponse<CandidateSkill[]>) => {
                this.candidateSkills = res.body;
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
        this.registerChangeInCandidateSkills();
    }

    ngOnDestroy() {
        this.eventManager.destroy(this.eventSubscriber);
    }

    trackId(index: number, item: CandidateSkill) {
        return item.id;
    }
    registerChangeInCandidateSkills() {
        this.eventSubscriber = this.eventManager.subscribe('candidateSkillListModification', (response) => this.loadAll());
    }

    private onError(error) {
        this.jhiAlertService.error(error.message, null, null);
    }
}
