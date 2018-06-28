/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs/Observable';

import { VerifyMyCandidateTestModule } from '../../../test.module';
import { ExperienceDetailComponent } from '../../../../../../main/webapp/app/entities/experience/experience-detail.component';
import { ExperienceService } from '../../../../../../main/webapp/app/entities/experience/experience.service';
import { Experience } from '../../../../../../main/webapp/app/entities/experience/experience.model';

describe('Component Tests', () => {

    describe('Experience Management Detail Component', () => {
        let comp: ExperienceDetailComponent;
        let fixture: ComponentFixture<ExperienceDetailComponent>;
        let service: ExperienceService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [VerifyMyCandidateTestModule],
                declarations: [ExperienceDetailComponent],
                providers: [
                    ExperienceService
                ]
            })
            .overrideTemplate(ExperienceDetailComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(ExperienceDetailComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(ExperienceService);
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN

                spyOn(service, 'find').and.returnValue(Observable.of(new HttpResponse({
                    body: new Experience(123)
                })));

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(service.find).toHaveBeenCalledWith(123);
                expect(comp.experience).toEqual(jasmine.objectContaining({id: 123}));
            });
        });
    });

});
