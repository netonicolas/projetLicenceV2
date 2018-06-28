/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async, inject, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { Observable } from 'rxjs/Observable';
import { JhiEventManager } from 'ng-jhipster';

import { VerifyMyCandidateTestModule } from '../../../test.module';
import { SkillQuestionDialogComponent } from '../../../../../../main/webapp/app/entities/skill-question/skill-question-dialog.component';
import { SkillQuestionService } from '../../../../../../main/webapp/app/entities/skill-question/skill-question.service';
import { SkillQuestion } from '../../../../../../main/webapp/app/entities/skill-question/skill-question.model';
import { SkillTestService } from '../../../../../../main/webapp/app/entities/skill-test';
import { SkillService } from '../../../../../../main/webapp/app/entities/skill';

describe('Component Tests', () => {

    describe('SkillQuestion Management Dialog Component', () => {
        let comp: SkillQuestionDialogComponent;
        let fixture: ComponentFixture<SkillQuestionDialogComponent>;
        let service: SkillQuestionService;
        let mockEventManager: any;
        let mockActiveModal: any;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [VerifyMyCandidateTestModule],
                declarations: [SkillQuestionDialogComponent],
                providers: [
                    SkillTestService,
                    SkillService,
                    SkillQuestionService
                ]
            })
            .overrideTemplate(SkillQuestionDialogComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(SkillQuestionDialogComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(SkillQuestionService);
            mockEventManager = fixture.debugElement.injector.get(JhiEventManager);
            mockActiveModal = fixture.debugElement.injector.get(NgbActiveModal);
        });

        describe('save', () => {
            it('Should call update service on save for existing entity',
                inject([],
                    fakeAsync(() => {
                        // GIVEN
                        const entity = new SkillQuestion(123);
                        spyOn(service, 'update').and.returnValue(Observable.of(new HttpResponse({body: entity})));
                        comp.skillQuestion = entity;
                        // WHEN
                        comp.save();
                        tick(); // simulate async

                        // THEN
                        expect(service.update).toHaveBeenCalledWith(entity);
                        expect(comp.isSaving).toEqual(false);
                        expect(mockEventManager.broadcastSpy).toHaveBeenCalledWith({ name: 'skillQuestionListModification', content: 'OK'});
                        expect(mockActiveModal.dismissSpy).toHaveBeenCalled();
                    })
                )
            );

            it('Should call create service on save for new entity',
                inject([],
                    fakeAsync(() => {
                        // GIVEN
                        const entity = new SkillQuestion();
                        spyOn(service, 'create').and.returnValue(Observable.of(new HttpResponse({body: entity})));
                        comp.skillQuestion = entity;
                        // WHEN
                        comp.save();
                        tick(); // simulate async

                        // THEN
                        expect(service.create).toHaveBeenCalledWith(entity);
                        expect(comp.isSaving).toEqual(false);
                        expect(mockEventManager.broadcastSpy).toHaveBeenCalledWith({ name: 'skillQuestionListModification', content: 'OK'});
                        expect(mockActiveModal.dismissSpy).toHaveBeenCalled();
                    })
                )
            );
        });
    });

});
