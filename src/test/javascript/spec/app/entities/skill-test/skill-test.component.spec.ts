/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async } from '@angular/core/testing';
import { Observable } from 'rxjs/Observable';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { VerifyMyCandidateTestModule } from '../../../test.module';
import { SkillTestComponent } from '../../../../../../main/webapp/app/entities/skill-test/skill-test.component';
import { SkillTestService } from '../../../../../../main/webapp/app/entities/skill-test/skill-test.service';
import { SkillTest } from '../../../../../../main/webapp/app/entities/skill-test/skill-test.model';

describe('Component Tests', () => {

    describe('SkillTest Management Component', () => {
        let comp: SkillTestComponent;
        let fixture: ComponentFixture<SkillTestComponent>;
        let service: SkillTestService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [VerifyMyCandidateTestModule],
                declarations: [SkillTestComponent],
                providers: [
                    SkillTestService
                ]
            })
            .overrideTemplate(SkillTestComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(SkillTestComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(SkillTestService);
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN
                const headers = new HttpHeaders().append('link', 'link;link');
                spyOn(service, 'query').and.returnValue(Observable.of(new HttpResponse({
                    body: [new SkillTest(123)],
                    headers
                })));

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(service.query).toHaveBeenCalled();
                expect(comp.skillTests[0]).toEqual(jasmine.objectContaining({id: 123}));
            });
        });
    });

});
