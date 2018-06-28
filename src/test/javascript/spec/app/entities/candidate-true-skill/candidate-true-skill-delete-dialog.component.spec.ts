/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async, inject, fakeAsync, tick } from '@angular/core/testing';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { Observable } from 'rxjs/Observable';
import { JhiEventManager } from 'ng-jhipster';

import { VerifyMyCandidateTestModule } from '../../../test.module';
import { CandidateTrueSkillDeleteDialogComponent } from '../../../../../../main/webapp/app/entities/candidate-true-skill/candidate-true-skill-delete-dialog.component';
import { CandidateTrueSkillService } from '../../../../../../main/webapp/app/entities/candidate-true-skill/candidate-true-skill.service';

describe('Component Tests', () => {

    describe('CandidateTrueSkill Management Delete Component', () => {
        let comp: CandidateTrueSkillDeleteDialogComponent;
        let fixture: ComponentFixture<CandidateTrueSkillDeleteDialogComponent>;
        let service: CandidateTrueSkillService;
        let mockEventManager: any;
        let mockActiveModal: any;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [VerifyMyCandidateTestModule],
                declarations: [CandidateTrueSkillDeleteDialogComponent],
                providers: [
                    CandidateTrueSkillService
                ]
            })
            .overrideTemplate(CandidateTrueSkillDeleteDialogComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(CandidateTrueSkillDeleteDialogComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(CandidateTrueSkillService);
            mockEventManager = fixture.debugElement.injector.get(JhiEventManager);
            mockActiveModal = fixture.debugElement.injector.get(NgbActiveModal);
        });

        describe('confirmDelete', () => {
            it('Should call delete service on confirmDelete',
                inject([],
                    fakeAsync(() => {
                        // GIVEN
                        spyOn(service, 'delete').and.returnValue(Observable.of({}));

                        // WHEN
                        comp.confirmDelete(123);
                        tick();

                        // THEN
                        expect(service.delete).toHaveBeenCalledWith(123);
                        expect(mockActiveModal.dismissSpy).toHaveBeenCalled();
                        expect(mockEventManager.broadcastSpy).toHaveBeenCalled();
                    })
                )
            );
        });
    });

});
