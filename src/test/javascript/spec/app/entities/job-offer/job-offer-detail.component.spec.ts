/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs/Observable';

import { VerifyMyCandidateTestModule } from '../../../test.module';
import { JobOfferDetailComponent } from '../../../../../../main/webapp/app/entities/job-offer/job-offer-detail.component';
import { JobOfferService } from '../../../../../../main/webapp/app/entities/job-offer/job-offer.service';
import { JobOffer } from '../../../../../../main/webapp/app/entities/job-offer/job-offer.model';

describe('Component Tests', () => {

    describe('JobOffer Management Detail Component', () => {
        let comp: JobOfferDetailComponent;
        let fixture: ComponentFixture<JobOfferDetailComponent>;
        let service: JobOfferService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [VerifyMyCandidateTestModule],
                declarations: [JobOfferDetailComponent],
                providers: [
                    JobOfferService
                ]
            })
            .overrideTemplate(JobOfferDetailComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(JobOfferDetailComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(JobOfferService);
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN

                spyOn(service, 'find').and.returnValue(Observable.of(new HttpResponse({
                    body: new JobOffer(123)
                })));

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(service.find).toHaveBeenCalledWith(123);
                expect(comp.jobOffer).toEqual(jasmine.objectContaining({id: 123}));
            });
        });
    });

});
