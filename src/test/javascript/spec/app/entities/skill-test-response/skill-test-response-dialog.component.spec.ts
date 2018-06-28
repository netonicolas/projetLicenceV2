/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async, inject, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { Observable } from 'rxjs/Observable';
import { JhiEventManager } from 'ng-jhipster';

import { VerifyMyCandidateTestModule } from '../../../test.module';
import { SkillTestResponseDialogComponent } from '../../../../../../main/webapp/app/entities/skill-test-response/skill-test-response-dialog.component';
import { SkillTestResponseService } from '../../../../../../main/webapp/app/entities/skill-test-response/skill-test-response.service';
import { SkillTestResponse } from '../../../../../../main/webapp/app/entities/skill-test-response/skill-test-response.model';
import { SkillTestService } from '../../../../../../main/webapp/app/entities/skill-test';
import { ProfilService } from '../../../../../../main/webapp/app/entities/profil';

describe('Component Tests', () => {

    describe('SkillTestResponse Management Dialog Component', () => {
        let comp: SkillTestResponseDialogComponent;
        let fixture: ComponentFixture<SkillTestResponseDialogComponent>;
        let service: SkillTestResponseService;
        let mockEventManager: any;
        let mockActiveModal: any;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [VerifyMyCandidateTestModule],
                declarations: [SkillTestResponseDialogComponent],
                providers: [
                    SkillTestService,
                    ProfilService,
                    SkillTestResponseService
                ]
            })
            .overrideTemplate(SkillTestResponseDialogComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(SkillTestResponseDialogComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(SkillTestResponseService);
            mockEventManager = fixture.debugElement.injector.get(JhiEventManager);
            mockActiveModal = fixture.debugElement.injector.get(NgbActiveModal);
        });

        describe('save', () => {
            it('Should call update service on save for existing entity',
                inject([],
                    fakeAsync(() => {
                        // GIVEN
                        const entity = new SkillTestResponse(123);
                        spyOn(service, 'update').and.returnValue(Observable.of(new HttpResponse({body: entity})));
                        comp.skillTestResponse = entity;
                        // WHEN
                        comp.save();
                        tick(); // simulate async

                        // THEN
                        expect(service.update).toHaveBeenCalledWith(entity);
                        expect(comp.isSaving).toEqual(false);
                        expect(mockEventManager.broadcastSpy).toHaveBeenCalledWith({ name: 'skillTestResponseListModification', content: 'OK'});
                        expect(mockActiveModal.dismissSpy).toHaveBeenCalled();
                    })
                )
            );

            it('Should call create service on save for new entity',
                inject([],
                    fakeAsync(() => {
                        // GIVEN
                        const entity = new SkillTestResponse();
                        spyOn(service, 'create').and.returnValue(Observable.of(new HttpResponse({body: entity})));
                        comp.skillTestResponse = entity;
                        // WHEN
                        comp.save();
                        tick(); // simulate async

                        // THEN
                        expect(service.create).toHaveBeenCalledWith(entity);
                        expect(comp.isSaving).toEqual(false);
                        expect(mockEventManager.broadcastSpy).toHaveBeenCalledWith({ name: 'skillTestResponseListModification', content: 'OK'});
                        expect(mockActiveModal.dismissSpy).toHaveBeenCalled();
                    })
                )
            );
        });
    });

});
