/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs/Observable';

import { VerifyMyCandidateTestModule } from '../../../test.module';
import { CandidateSkillDetailComponent } from '../../../../../../main/webapp/app/entities/candidate-skill/candidate-skill-detail.component';
import { CandidateSkillService } from '../../../../../../main/webapp/app/entities/candidate-skill/candidate-skill.service';
import { CandidateSkill } from '../../../../../../main/webapp/app/entities/candidate-skill/candidate-skill.model';

describe('Component Tests', () => {

    describe('CandidateSkill Management Detail Component', () => {
        let comp: CandidateSkillDetailComponent;
        let fixture: ComponentFixture<CandidateSkillDetailComponent>;
        let service: CandidateSkillService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [VerifyMyCandidateTestModule],
                declarations: [CandidateSkillDetailComponent],
                providers: [
                    CandidateSkillService
                ]
            })
            .overrideTemplate(CandidateSkillDetailComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(CandidateSkillDetailComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(CandidateSkillService);
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN

                spyOn(service, 'find').and.returnValue(Observable.of(new HttpResponse({
                    body: new CandidateSkill(123)
                })));

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(service.find).toHaveBeenCalledWith(123);
                expect(comp.candidateSkill).toEqual(jasmine.objectContaining({id: 123}));
            });
        });
    });

});
