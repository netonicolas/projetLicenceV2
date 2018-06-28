/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async, inject, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { Observable } from 'rxjs/Observable';
import { JhiEventManager } from 'ng-jhipster';

import { VerifyMyCandidateTestModule } from '../../../test.module';
import { CandidateTrueSkillDialogComponent } from '../../../../../../main/webapp/app/entities/candidate-true-skill/candidate-true-skill-dialog.component';
import { CandidateTrueSkillService } from '../../../../../../main/webapp/app/entities/candidate-true-skill/candidate-true-skill.service';
import { CandidateTrueSkill } from '../../../../../../main/webapp/app/entities/candidate-true-skill/candidate-true-skill.model';
import { ProfilService } from '../../../../../../main/webapp/app/entities/profil';
import { SkillService } from '../../../../../../main/webapp/app/entities/skill';

describe('Component Tests', () => {

    describe('CandidateTrueSkill Management Dialog Component', () => {
        let comp: CandidateTrueSkillDialogComponent;
        let fixture: ComponentFixture<CandidateTrueSkillDialogComponent>;
        let service: CandidateTrueSkillService;
        let mockEventManager: any;
        let mockActiveModal: any;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [VerifyMyCandidateTestModule],
                declarations: [CandidateTrueSkillDialogComponent],
                providers: [
                    ProfilService,
                    SkillService,
                    CandidateTrueSkillService
                ]
            })
            .overrideTemplate(CandidateTrueSkillDialogComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(CandidateTrueSkillDialogComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(CandidateTrueSkillService);
            mockEventManager = fixture.debugElement.injector.get(JhiEventManager);
            mockActiveModal = fixture.debugElement.injector.get(NgbActiveModal);
        });

        describe('save', () => {
            it('Should call update service on save for existing entity',
                inject([],
                    fakeAsync(() => {
                        // GIVEN
                        const entity = new CandidateTrueSkill(123);
                        spyOn(service, 'update').and.returnValue(Observable.of(new HttpResponse({body: entity})));
                        comp.candidateTrueSkill = entity;
                        // WHEN
                        comp.save();
                        tick(); // simulate async

                        // THEN
                        expect(service.update).toHaveBeenCalledWith(entity);
                        expect(comp.isSaving).toEqual(false);
                        expect(mockEventManager.broadcastSpy).toHaveBeenCalledWith({ name: 'candidateTrueSkillListModification', content: 'OK'});
                        expect(mockActiveModal.dismissSpy).toHaveBeenCalled();
                    })
                )
            );

            it('Should call create service on save for new entity',
                inject([],
                    fakeAsync(() => {
                        // GIVEN
                        const entity = new CandidateTrueSkill();
                        spyOn(service, 'create').and.returnValue(Observable.of(new HttpResponse({body: entity})));
                        comp.candidateTrueSkill = entity;
                        // WHEN
                        comp.save();
                        tick(); // simulate async

                        // THEN
                        expect(service.create).toHaveBeenCalledWith(entity);
                        expect(comp.isSaving).toEqual(false);
                        expect(mockEventManager.broadcastSpy).toHaveBeenCalledWith({ name: 'candidateTrueSkillListModification', content: 'OK'});
                        expect(mockActiveModal.dismissSpy).toHaveBeenCalled();
                    })
                )
            );
        });
    });

});
