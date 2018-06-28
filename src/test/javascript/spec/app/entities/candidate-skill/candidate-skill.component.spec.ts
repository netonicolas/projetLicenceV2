/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async } from '@angular/core/testing';
import { Observable } from 'rxjs/Observable';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { VerifyMyCandidateTestModule } from '../../../test.module';
import { CandidateSkillComponent } from '../../../../../../main/webapp/app/entities/candidate-skill/candidate-skill.component';
import { CandidateSkillService } from '../../../../../../main/webapp/app/entities/candidate-skill/candidate-skill.service';
import { CandidateSkill } from '../../../../../../main/webapp/app/entities/candidate-skill/candidate-skill.model';

describe('Component Tests', () => {

    describe('CandidateSkill Management Component', () => {
        let comp: CandidateSkillComponent;
        let fixture: ComponentFixture<CandidateSkillComponent>;
        let service: CandidateSkillService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [VerifyMyCandidateTestModule],
                declarations: [CandidateSkillComponent],
                providers: [
                    CandidateSkillService
                ]
            })
            .overrideTemplate(CandidateSkillComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(CandidateSkillComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(CandidateSkillService);
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN
                const headers = new HttpHeaders().append('link', 'link;link');
                spyOn(service, 'query').and.returnValue(Observable.of(new HttpResponse({
                    body: [new CandidateSkill(123)],
                    headers
                })));

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(service.query).toHaveBeenCalled();
                expect(comp.candidateSkills[0]).toEqual(jasmine.objectContaining({id: 123}));
            });
        });
    });

});
