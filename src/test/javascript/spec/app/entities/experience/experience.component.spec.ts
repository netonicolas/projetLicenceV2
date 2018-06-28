/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async } from '@angular/core/testing';
import { Observable } from 'rxjs/Observable';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { VerifyMyCandidateTestModule } from '../../../test.module';
import { ExperienceComponent } from '../../../../../../main/webapp/app/entities/experience/experience.component';
import { ExperienceService } from '../../../../../../main/webapp/app/entities/experience/experience.service';
import { Experience } from '../../../../../../main/webapp/app/entities/experience/experience.model';

describe('Component Tests', () => {

    describe('Experience Management Component', () => {
        let comp: ExperienceComponent;
        let fixture: ComponentFixture<ExperienceComponent>;
        let service: ExperienceService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [VerifyMyCandidateTestModule],
                declarations: [ExperienceComponent],
                providers: [
                    ExperienceService
                ]
            })
            .overrideTemplate(ExperienceComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(ExperienceComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(ExperienceService);
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN
                const headers = new HttpHeaders().append('link', 'link;link');
                spyOn(service, 'query').and.returnValue(Observable.of(new HttpResponse({
                    body: [new Experience(123)],
                    headers
                })));

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(service.query).toHaveBeenCalled();
                expect(comp.experiences[0]).toEqual(jasmine.objectContaining({id: 123}));
            });
        });
    });

});
