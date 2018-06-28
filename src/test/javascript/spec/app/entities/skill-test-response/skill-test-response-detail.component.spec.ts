/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs/Observable';

import { VerifyMyCandidateTestModule } from '../../../test.module';
import { SkillTestResponseDetailComponent } from '../../../../../../main/webapp/app/entities/skill-test-response/skill-test-response-detail.component';
import { SkillTestResponseService } from '../../../../../../main/webapp/app/entities/skill-test-response/skill-test-response.service';
import { SkillTestResponse } from '../../../../../../main/webapp/app/entities/skill-test-response/skill-test-response.model';

describe('Component Tests', () => {

    describe('SkillTestResponse Management Detail Component', () => {
        let comp: SkillTestResponseDetailComponent;
        let fixture: ComponentFixture<SkillTestResponseDetailComponent>;
        let service: SkillTestResponseService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [VerifyMyCandidateTestModule],
                declarations: [SkillTestResponseDetailComponent],
                providers: [
                    SkillTestResponseService
                ]
            })
            .overrideTemplate(SkillTestResponseDetailComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(SkillTestResponseDetailComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(SkillTestResponseService);
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN

                spyOn(service, 'find').and.returnValue(Observable.of(new HttpResponse({
                    body: new SkillTestResponse(123)
                })));

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(service.find).toHaveBeenCalledWith(123);
                expect(comp.skillTestResponse).toEqual(jasmine.objectContaining({id: 123}));
            });
        });
    });

});
