/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs/Observable';

import { VerifyMyCandidateTestModule } from '../../../test.module';
import { SkillQuestionDetailComponent } from '../../../../../../main/webapp/app/entities/skill-question/skill-question-detail.component';
import { SkillQuestionService } from '../../../../../../main/webapp/app/entities/skill-question/skill-question.service';
import { SkillQuestion } from '../../../../../../main/webapp/app/entities/skill-question/skill-question.model';

describe('Component Tests', () => {

    describe('SkillQuestion Management Detail Component', () => {
        let comp: SkillQuestionDetailComponent;
        let fixture: ComponentFixture<SkillQuestionDetailComponent>;
        let service: SkillQuestionService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [VerifyMyCandidateTestModule],
                declarations: [SkillQuestionDetailComponent],
                providers: [
                    SkillQuestionService
                ]
            })
            .overrideTemplate(SkillQuestionDetailComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(SkillQuestionDetailComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(SkillQuestionService);
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN

                spyOn(service, 'find').and.returnValue(Observable.of(new HttpResponse({
                    body: new SkillQuestion(123)
                })));

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(service.find).toHaveBeenCalledWith(123);
                expect(comp.skillQuestion).toEqual(jasmine.objectContaining({id: 123}));
            });
        });
    });

});
