/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async } from '@angular/core/testing';
import { Observable } from 'rxjs/Observable';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { VerifyMyCandidateTestModule } from '../../../test.module';
import { CandidateTrueSkillComponent } from '../../../../../../main/webapp/app/entities/candidate-true-skill/candidate-true-skill.component';
import { CandidateTrueSkillService } from '../../../../../../main/webapp/app/entities/candidate-true-skill/candidate-true-skill.service';
import { CandidateTrueSkill } from '../../../../../../main/webapp/app/entities/candidate-true-skill/candidate-true-skill.model';

describe('Component Tests', () => {

    describe('CandidateTrueSkill Management Component', () => {
        let comp: CandidateTrueSkillComponent;
        let fixture: ComponentFixture<CandidateTrueSkillComponent>;
        let service: CandidateTrueSkillService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [VerifyMyCandidateTestModule],
                declarations: [CandidateTrueSkillComponent],
                providers: [
                    CandidateTrueSkillService
                ]
            })
            .overrideTemplate(CandidateTrueSkillComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(CandidateTrueSkillComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(CandidateTrueSkillService);
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN
                const headers = new HttpHeaders().append('link', 'link;link');
                spyOn(service, 'query').and.returnValue(Observable.of(new HttpResponse({
                    body: [new CandidateTrueSkill(123)],
                    headers
                })));

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(service.query).toHaveBeenCalled();
                expect(comp.candidateTrueSkills[0]).toEqual(jasmine.objectContaining({id: 123}));
            });
        });
    });

});
