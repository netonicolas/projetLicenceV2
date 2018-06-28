/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async, inject, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { Observable } from 'rxjs/Observable';
import { JhiEventManager } from 'ng-jhipster';

import { VerifyMyCandidateTestModule } from '../../../test.module';
import { JobResponseDialogComponent } from '../../../../../../main/webapp/app/entities/job-response/job-response-dialog.component';
import { JobResponseService } from '../../../../../../main/webapp/app/entities/job-response/job-response.service';
import { JobResponse } from '../../../../../../main/webapp/app/entities/job-response/job-response.model';
import { ProfilService } from '../../../../../../main/webapp/app/entities/profil';
import { JobOfferService } from '../../../../../../main/webapp/app/entities/job-offer';

describe('Component Tests', () => {

    describe('JobResponse Management Dialog Component', () => {
        let comp: JobResponseDialogComponent;
        let fixture: ComponentFixture<JobResponseDialogComponent>;
        let service: JobResponseService;
        let mockEventManager: any;
        let mockActiveModal: any;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [VerifyMyCandidateTestModule],
                declarations: [JobResponseDialogComponent],
                providers: [
                    ProfilService,
                    JobOfferService,
                    JobResponseService
                ]
            })
            .overrideTemplate(JobResponseDialogComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(JobResponseDialogComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(JobResponseService);
            mockEventManager = fixture.debugElement.injector.get(JhiEventManager);
            mockActiveModal = fixture.debugElement.injector.get(NgbActiveModal);
        });

        describe('save', () => {
            it('Should call update service on save for existing entity',
                inject([],
                    fakeAsync(() => {
                        // GIVEN
                        const entity = new JobResponse(123);
                        spyOn(service, 'update').and.returnValue(Observable.of(new HttpResponse({body: entity})));
                        comp.jobResponse = entity;
                        // WHEN
                        comp.save();
                        tick(); // simulate async

                        // THEN
                        expect(service.update).toHaveBeenCalledWith(entity);
                        expect(comp.isSaving).toEqual(false);
                        expect(mockEventManager.broadcastSpy).toHaveBeenCalledWith({ name: 'jobResponseListModification', content: 'OK'});
                        expect(mockActiveModal.dismissSpy).toHaveBeenCalled();
                    })
                )
            );

            it('Should call create service on save for new entity',
                inject([],
                    fakeAsync(() => {
                        // GIVEN
                        const entity = new JobResponse();
                        spyOn(service, 'create').and.returnValue(Observable.of(new HttpResponse({body: entity})));
                        comp.jobResponse = entity;
                        // WHEN
                        comp.save();
                        tick(); // simulate async

                        // THEN
                        expect(service.create).toHaveBeenCalledWith(entity);
                        expect(comp.isSaving).toEqual(false);
                        expect(mockEventManager.broadcastSpy).toHaveBeenCalledWith({ name: 'jobResponseListModification', content: 'OK'});
                        expect(mockActiveModal.dismissSpy).toHaveBeenCalled();
                    })
                )
            );
        });
    });

});
