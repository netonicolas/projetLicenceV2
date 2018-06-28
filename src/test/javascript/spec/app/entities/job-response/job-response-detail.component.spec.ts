/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs/Observable';

import { VerifyMyCandidateTestModule } from '../../../test.module';
import { JobResponseDetailComponent } from '../../../../../../main/webapp/app/entities/job-response/job-response-detail.component';
import { JobResponseService } from '../../../../../../main/webapp/app/entities/job-response/job-response.service';
import { JobResponse } from '../../../../../../main/webapp/app/entities/job-response/job-response.model';

describe('Component Tests', () => {

    describe('JobResponse Management Detail Component', () => {
        let comp: JobResponseDetailComponent;
        let fixture: ComponentFixture<JobResponseDetailComponent>;
        let service: JobResponseService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [VerifyMyCandidateTestModule],
                declarations: [JobResponseDetailComponent],
                providers: [
                    JobResponseService
                ]
            })
            .overrideTemplate(JobResponseDetailComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(JobResponseDetailComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(JobResponseService);
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN

                spyOn(service, 'find').and.returnValue(Observable.of(new HttpResponse({
                    body: new JobResponse(123)
                })));

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(service.find).toHaveBeenCalledWith(123);
                expect(comp.jobResponse).toEqual(jasmine.objectContaining({id: 123}));
            });
        });
    });

});
