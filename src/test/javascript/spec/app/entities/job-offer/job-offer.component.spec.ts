/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async } from '@angular/core/testing';
import { Observable } from 'rxjs/Observable';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { VerifyMyCandidateTestModule } from '../../../test.module';
import { JobOfferComponent } from '../../../../../../main/webapp/app/entities/job-offer/job-offer.component';
import { JobOfferService } from '../../../../../../main/webapp/app/entities/job-offer/job-offer.service';
import { JobOffer } from '../../../../../../main/webapp/app/entities/job-offer/job-offer.model';

describe('Component Tests', () => {

    describe('JobOffer Management Component', () => {
        let comp: JobOfferComponent;
        let fixture: ComponentFixture<JobOfferComponent>;
        let service: JobOfferService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [VerifyMyCandidateTestModule],
                declarations: [JobOfferComponent],
                providers: [
                    JobOfferService
                ]
            })
            .overrideTemplate(JobOfferComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(JobOfferComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(JobOfferService);
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN
                const headers = new HttpHeaders().append('link', 'link;link');
                spyOn(service, 'query').and.returnValue(Observable.of(new HttpResponse({
                    body: [new JobOffer(123)],
                    headers
                })));

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(service.query).toHaveBeenCalled();
                expect(comp.jobOffers[0]).toEqual(jasmine.objectContaining({id: 123}));
            });
        });
    });

});
