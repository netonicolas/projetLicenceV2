/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs/Observable';

import { VerifyMyCandidateTestModule } from '../../../test.module';
import { CandidateTrueSkillDetailComponent } from '../../../../../../main/webapp/app/entities/candidate-true-skill/candidate-true-skill-detail.component';
import { CandidateTrueSkillService } from '../../../../../../main/webapp/app/entities/candidate-true-skill/candidate-true-skill.service';
import { CandidateTrueSkill } from '../../../../../../main/webapp/app/entities/candidate-true-skill/candidate-true-skill.model';

describe('Component Tests', () => {

    describe('CandidateTrueSkill Management Detail Component', () => {
        let comp: CandidateTrueSkillDetailComponent;
        let fixture: ComponentFixture<CandidateTrueSkillDetailComponent>;
        let service: CandidateTrueSkillService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [VerifyMyCandidateTestModule],
                declarations: [CandidateTrueSkillDetailComponent],
                providers: [
                    CandidateTrueSkillService
                ]
            })
            .overrideTemplate(CandidateTrueSkillDetailComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(CandidateTrueSkillDetailComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(CandidateTrueSkillService);
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN

                spyOn(service, 'find').and.returnValue(Observable.of(new HttpResponse({
                    body: new CandidateTrueSkill(123)
                })));

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(service.find).toHaveBeenCalledWith(123);
                expect(comp.candidateTrueSkill).toEqual(jasmine.objectContaining({id: 123}));
            });
        });
    });

});
