/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async } from '@angular/core/testing';
import { Observable } from 'rxjs/Observable';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { VerifyMyCandidateTestModule } from '../../../test.module';
import { SkillQuestionComponent } from '../../../../../../main/webapp/app/entities/skill-question/skill-question.component';
import { SkillQuestionService } from '../../../../../../main/webapp/app/entities/skill-question/skill-question.service';
import { SkillQuestion } from '../../../../../../main/webapp/app/entities/skill-question/skill-question.model';

describe('Component Tests', () => {

    describe('SkillQuestion Management Component', () => {
        let comp: SkillQuestionComponent;
        let fixture: ComponentFixture<SkillQuestionComponent>;
        let service: SkillQuestionService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [VerifyMyCandidateTestModule],
                declarations: [SkillQuestionComponent],
                providers: [
                    SkillQuestionService
                ]
            })
            .overrideTemplate(SkillQuestionComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(SkillQuestionComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(SkillQuestionService);
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN
                const headers = new HttpHeaders().append('link', 'link;link');
                spyOn(service, 'query').and.returnValue(Observable.of(new HttpResponse({
                    body: [new SkillQuestion(123)],
                    headers
                })));

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(service.query).toHaveBeenCalled();
                expect(comp.skillQuestions[0]).toEqual(jasmine.objectContaining({id: 123}));
            });
        });
    });

});
