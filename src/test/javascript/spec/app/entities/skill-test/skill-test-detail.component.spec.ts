/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs/Observable';

import { VerifyMyCandidateTestModule } from '../../../test.module';
import { SkillTestDetailComponent } from '../../../../../../main/webapp/app/entities/skill-test/skill-test-detail.component';
import { SkillTestService } from '../../../../../../main/webapp/app/entities/skill-test/skill-test.service';
import { SkillTest } from '../../../../../../main/webapp/app/entities/skill-test/skill-test.model';

describe('Component Tests', () => {

    describe('SkillTest Management Detail Component', () => {
        let comp: SkillTestDetailComponent;
        let fixture: ComponentFixture<SkillTestDetailComponent>;
        let service: SkillTestService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [VerifyMyCandidateTestModule],
                declarations: [SkillTestDetailComponent],
                providers: [
                    SkillTestService
                ]
            })
            .overrideTemplate(SkillTestDetailComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(SkillTestDetailComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(SkillTestService);
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN

                spyOn(service, 'find').and.returnValue(Observable.of(new HttpResponse({
                    body: new SkillTest(123)
                })));

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(service.find).toHaveBeenCalledWith(123);
                expect(comp.skillTest).toEqual(jasmine.objectContaining({id: 123}));
            });
        });
    });

});
