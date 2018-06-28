/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs/Observable';

import { VerifyMyCandidateTestModule } from '../../../test.module';
import { JobSkillDetailComponent } from '../../../../../../main/webapp/app/entities/job-skill/job-skill-detail.component';
import { JobSkillService } from '../../../../../../main/webapp/app/entities/job-skill/job-skill.service';
import { JobSkill } from '../../../../../../main/webapp/app/entities/job-skill/job-skill.model';

describe('Component Tests', () => {

    describe('JobSkill Management Detail Component', () => {
        let comp: JobSkillDetailComponent;
        let fixture: ComponentFixture<JobSkillDetailComponent>;
        let service: JobSkillService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [VerifyMyCandidateTestModule],
                declarations: [JobSkillDetailComponent],
                providers: [
                    JobSkillService
                ]
            })
            .overrideTemplate(JobSkillDetailComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(JobSkillDetailComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(JobSkillService);
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN

                spyOn(service, 'find').and.returnValue(Observable.of(new HttpResponse({
                    body: new JobSkill(123)
                })));

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(service.find).toHaveBeenCalledWith(123);
                expect(comp.jobSkill).toEqual(jasmine.objectContaining({id: 123}));
            });
        });
    });

});
