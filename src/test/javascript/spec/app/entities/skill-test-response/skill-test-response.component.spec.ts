/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async } from '@angular/core/testing';
import { Observable } from 'rxjs/Observable';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { VerifyMyCandidateTestModule } from '../../../test.module';
import { SkillTestResponseComponent } from '../../../../../../main/webapp/app/entities/skill-test-response/skill-test-response.component';
import { SkillTestResponseService } from '../../../../../../main/webapp/app/entities/skill-test-response/skill-test-response.service';
import { SkillTestResponse } from '../../../../../../main/webapp/app/entities/skill-test-response/skill-test-response.model';

describe('Component Tests', () => {

    describe('SkillTestResponse Management Component', () => {
        let comp: SkillTestResponseComponent;
        let fixture: ComponentFixture<SkillTestResponseComponent>;
        let service: SkillTestResponseService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [VerifyMyCandidateTestModule],
                declarations: [SkillTestResponseComponent],
                providers: [
                    SkillTestResponseService
                ]
            })
            .overrideTemplate(SkillTestResponseComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(SkillTestResponseComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(SkillTestResponseService);
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN
                const headers = new HttpHeaders().append('link', 'link;link');
                spyOn(service, 'query').and.returnValue(Observable.of(new HttpResponse({
                    body: [new SkillTestResponse(123)],
                    headers
                })));

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(service.query).toHaveBeenCalled();
                expect(comp.skillTestResponses[0]).toEqual(jasmine.objectContaining({id: 123}));
            });
        });
    });

});
