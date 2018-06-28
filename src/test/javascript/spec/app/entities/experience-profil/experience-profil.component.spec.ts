/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async } from '@angular/core/testing';
import { Observable } from 'rxjs/Observable';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { VerifyMyCandidateTestModule } from '../../../test.module';
import { ExperienceProfilComponent } from '../../../../../../main/webapp/app/entities/experience-profil/experience-profil.component';
import { ExperienceProfilService } from '../../../../../../main/webapp/app/entities/experience-profil/experience-profil.service';
import { ExperienceProfil } from '../../../../../../main/webapp/app/entities/experience-profil/experience-profil.model';

describe('Component Tests', () => {

    describe('ExperienceProfil Management Component', () => {
        let comp: ExperienceProfilComponent;
        let fixture: ComponentFixture<ExperienceProfilComponent>;
        let service: ExperienceProfilService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [VerifyMyCandidateTestModule],
                declarations: [ExperienceProfilComponent],
                providers: [
                    ExperienceProfilService
                ]
            })
            .overrideTemplate(ExperienceProfilComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(ExperienceProfilComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(ExperienceProfilService);
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN
                const headers = new HttpHeaders().append('link', 'link;link');
                spyOn(service, 'query').and.returnValue(Observable.of(new HttpResponse({
                    body: [new ExperienceProfil(123)],
                    headers
                })));

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(service.query).toHaveBeenCalled();
                expect(comp.experienceProfils[0]).toEqual(jasmine.objectContaining({id: 123}));
            });
        });
    });

});
