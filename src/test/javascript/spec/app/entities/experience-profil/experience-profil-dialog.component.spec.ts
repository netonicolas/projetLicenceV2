/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async, inject, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { Observable } from 'rxjs/Observable';
import { JhiEventManager } from 'ng-jhipster';

import { VerifyMyCandidateTestModule } from '../../../test.module';
import { ExperienceProfilDialogComponent } from '../../../../../../main/webapp/app/entities/experience-profil/experience-profil-dialog.component';
import { ExperienceProfilService } from '../../../../../../main/webapp/app/entities/experience-profil/experience-profil.service';
import { ExperienceProfil } from '../../../../../../main/webapp/app/entities/experience-profil/experience-profil.model';
import { ProfilService } from '../../../../../../main/webapp/app/entities/profil';
import { ExperienceService } from '../../../../../../main/webapp/app/entities/experience';

describe('Component Tests', () => {

    describe('ExperienceProfil Management Dialog Component', () => {
        let comp: ExperienceProfilDialogComponent;
        let fixture: ComponentFixture<ExperienceProfilDialogComponent>;
        let service: ExperienceProfilService;
        let mockEventManager: any;
        let mockActiveModal: any;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [VerifyMyCandidateTestModule],
                declarations: [ExperienceProfilDialogComponent],
                providers: [
                    ProfilService,
                    ExperienceService,
                    ExperienceProfilService
                ]
            })
            .overrideTemplate(ExperienceProfilDialogComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(ExperienceProfilDialogComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(ExperienceProfilService);
            mockEventManager = fixture.debugElement.injector.get(JhiEventManager);
            mockActiveModal = fixture.debugElement.injector.get(NgbActiveModal);
        });

        describe('save', () => {
            it('Should call update service on save for existing entity',
                inject([],
                    fakeAsync(() => {
                        // GIVEN
                        const entity = new ExperienceProfil(123);
                        spyOn(service, 'update').and.returnValue(Observable.of(new HttpResponse({body: entity})));
                        comp.experienceProfil = entity;
                        // WHEN
                        comp.save();
                        tick(); // simulate async

                        // THEN
                        expect(service.update).toHaveBeenCalledWith(entity);
                        expect(comp.isSaving).toEqual(false);
                        expect(mockEventManager.broadcastSpy).toHaveBeenCalledWith({ name: 'experienceProfilListModification', content: 'OK'});
                        expect(mockActiveModal.dismissSpy).toHaveBeenCalled();
                    })
                )
            );

            it('Should call create service on save for new entity',
                inject([],
                    fakeAsync(() => {
                        // GIVEN
                        const entity = new ExperienceProfil();
                        spyOn(service, 'create').and.returnValue(Observable.of(new HttpResponse({body: entity})));
                        comp.experienceProfil = entity;
                        // WHEN
                        comp.save();
                        tick(); // simulate async

                        // THEN
                        expect(service.create).toHaveBeenCalledWith(entity);
                        expect(comp.isSaving).toEqual(false);
                        expect(mockEventManager.broadcastSpy).toHaveBeenCalledWith({ name: 'experienceProfilListModification', content: 'OK'});
                        expect(mockActiveModal.dismissSpy).toHaveBeenCalled();
                    })
                )
            );
        });
    });

});
