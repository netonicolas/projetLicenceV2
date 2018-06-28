/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async, inject, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { Observable } from 'rxjs/Observable';
import { JhiEventManager } from 'ng-jhipster';

import { VerifyMyCandidateTestModule } from '../../../test.module';
import { CandidateSkillDialogComponent } from '../../../../../../main/webapp/app/entities/candidate-skill/candidate-skill-dialog.component';
import { CandidateSkillService } from '../../../../../../main/webapp/app/entities/candidate-skill/candidate-skill.service';
import { CandidateSkill } from '../../../../../../main/webapp/app/entities/candidate-skill/candidate-skill.model';
import { ProfilService } from '../../../../../../main/webapp/app/entities/profil';
import { SkillService } from '../../../../../../main/webapp/app/entities/skill';

describe('Component Tests', () => {

    describe('CandidateSkill Management Dialog Component', () => {
        let comp: CandidateSkillDialogComponent;
        let fixture: ComponentFixture<CandidateSkillDialogComponent>;
        let service: CandidateSkillService;
        let mockEventManager: any;
        let mockActiveModal: any;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [VerifyMyCandidateTestModule],
                declarations: [CandidateSkillDialogComponent],
                providers: [
                    ProfilService,
                    SkillService,
                    CandidateSkillService
                ]
            })
            .overrideTemplate(CandidateSkillDialogComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(CandidateSkillDialogComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(CandidateSkillService);
            mockEventManager = fixture.debugElement.injector.get(JhiEventManager);
            mockActiveModal = fixture.debugElement.injector.get(NgbActiveModal);
        });

        describe('save', () => {
            it('Should call update service on save for existing entity',
                inject([],
                    fakeAsync(() => {
                        // GIVEN
                        const entity = new CandidateSkill(123);
                        spyOn(service, 'update').and.returnValue(Observable.of(new HttpResponse({body: entity})));
                        comp.candidateSkill = entity;
                        // WHEN
                        comp.save();
                        tick(); // simulate async

                        // THEN
                        expect(service.update).toHaveBeenCalledWith(entity);
                        expect(comp.isSaving).toEqual(false);
                        expect(mockEventManager.broadcastSpy).toHaveBeenCalledWith({ name: 'candidateSkillListModification', content: 'OK'});
                        expect(mockActiveModal.dismissSpy).toHaveBeenCalled();
                    })
                )
            );

            it('Should call create service on save for new entity',
                inject([],
                    fakeAsync(() => {
                        // GIVEN
                        const entity = new CandidateSkill();
                        spyOn(service, 'create').and.returnValue(Observable.of(new HttpResponse({body: entity})));
                        comp.candidateSkill = entity;
                        // WHEN
                        comp.save();
                        tick(); // simulate async

                        // THEN
                        expect(service.create).toHaveBeenCalledWith(entity);
                        expect(comp.isSaving).toEqual(false);
                        expect(mockEventManager.broadcastSpy).toHaveBeenCalledWith({ name: 'candidateSkillListModification', content: 'OK'});
                        expect(mockActiveModal.dismissSpy).toHaveBeenCalled();
                    })
                )
            );
        });
    });

});
