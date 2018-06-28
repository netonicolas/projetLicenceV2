/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async } from '@angular/core/testing';
import { Observable } from 'rxjs/Observable';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { VerifyMyCandidateTestModule } from '../../../test.module';
import { JobSkillComponent } from '../../../../../../main/webapp/app/entities/job-skill/job-skill.component';
import { JobSkillService } from '../../../../../../main/webapp/app/entities/job-skill/job-skill.service';
import { JobSkill } from '../../../../../../main/webapp/app/entities/job-skill/job-skill.model';

describe('Component Tests', () => {

    describe('JobSkill Management Component', () => {
        let comp: JobSkillComponent;
        let fixture: ComponentFixture<JobSkillComponent>;
        let service: JobSkillService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [VerifyMyCandidateTestModule],
                declarations: [JobSkillComponent],
                providers: [
                    JobSkillService
                ]
            })
            .overrideTemplate(JobSkillComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(JobSkillComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(JobSkillService);
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN
                const headers = new HttpHeaders().append('link', 'link;link');
                spyOn(service, 'query').and.returnValue(Observable.of(new HttpResponse({
                    body: [new JobSkill(123)],
                    headers
                })));

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(service.query).toHaveBeenCalled();
                expect(comp.jobSkills[0]).toEqual(jasmine.objectContaining({id: 123}));
            });
        });
    });

});
