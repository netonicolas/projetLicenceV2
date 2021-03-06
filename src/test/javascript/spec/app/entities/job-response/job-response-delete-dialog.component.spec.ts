/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async, inject, fakeAsync, tick } from '@angular/core/testing';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { Observable } from 'rxjs/Observable';
import { JhiEventManager } from 'ng-jhipster';

import { VerifyMyCandidateTestModule } from '../../../test.module';
import { JobResponseDeleteDialogComponent } from '../../../../../../main/webapp/app/entities/job-response/job-response-delete-dialog.component';
import { JobResponseService } from '../../../../../../main/webapp/app/entities/job-response/job-response.service';

describe('Component Tests', () => {

    describe('JobResponse Management Delete Component', () => {
        let comp: JobResponseDeleteDialogComponent;
        let fixture: ComponentFixture<JobResponseDeleteDialogComponent>;
        let service: JobResponseService;
        let mockEventManager: any;
        let mockActiveModal: any;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [VerifyMyCandidateTestModule],
                declarations: [JobResponseDeleteDialogComponent],
                providers: [
                    JobResponseService
                ]
            })
            .overrideTemplate(JobResponseDeleteDialogComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(JobResponseDeleteDialogComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(JobResponseService);
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
