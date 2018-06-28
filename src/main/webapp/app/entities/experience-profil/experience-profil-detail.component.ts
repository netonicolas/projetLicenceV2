import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs/Subscription';
import { JhiEventManager } from 'ng-jhipster';

import { ExperienceProfil } from './experience-profil.model';
import { ExperienceProfilService } from './experience-profil.service';

@Component({
    selector: 'jhi-experience-profil-detail',
    templateUrl: './experience-profil-detail.component.html'
})
export class ExperienceProfilDetailComponent implements OnInit, OnDestroy {

    experienceProfil: ExperienceProfil;
    private subscription: Subscription;
    private eventSubscriber: Subscription;

    constructor(
        private eventManager: JhiEventManager,
        private experienceProfilService: ExperienceProfilService,
        private route: ActivatedRoute
    ) {
    }

    ngOnInit() {
        this.subscription = this.route.params.subscribe((params) => {
            this.load(params['id']);
        });
        this.registerChangeInExperienceProfils();
    }

    load(id) {
        this.experienceProfilService.find(id)
            .subscribe((experienceProfilResponse: HttpResponse<ExperienceProfil>) => {
                this.experienceProfil = experienceProfilResponse.body;
            });
    }
    previousState() {
        window.history.back();
    }

    ngOnDestroy() {
        this.subscription.unsubscribe();
        this.eventManager.destroy(this.eventSubscriber);
    }

    registerChangeInExperienceProfils() {
        this.eventSubscriber = this.eventManager.subscribe(
            'experienceProfilListModification',
            (response) => this.load(this.experienceProfil.id)
        );
    }
}
