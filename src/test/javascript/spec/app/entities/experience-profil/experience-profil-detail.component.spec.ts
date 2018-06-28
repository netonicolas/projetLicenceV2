/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs/Observable';

import { VerifyMyCandidateTestModule } from '../../../test.module';
import { ExperienceProfilDetailComponent } from '../../../../../../main/webapp/app/entities/experience-profil/experience-profil-detail.component';
import { ExperienceProfilService } from '../../../../../../main/webapp/app/entities/experience-profil/experience-profil.service';
import { ExperienceProfil } from '../../../../../../main/webapp/app/entities/experience-profil/experience-profil.model';

describe('Component Tests', () => {

    describe('ExperienceProfil Management Detail Component', () => {
        let comp: ExperienceProfilDetailComponent;
        let fixture: ComponentFixture<ExperienceProfilDetailComponent>;
        let service: ExperienceProfilService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [VerifyMyCandidateTestModule],
                declarations: [ExperienceProfilDetailComponent],
                providers: [
                    ExperienceProfilService
                ]
            })
            .overrideTemplate(ExperienceProfilDetailComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(ExperienceProfilDetailComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(ExperienceProfilService);
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN

                spyOn(service, 'find').and.returnValue(Observable.of(new HttpResponse({
                    body: new ExperienceProfil(123)
                })));

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(service.find).toHaveBeenCalledWith(123);
                expect(comp.experienceProfil).toEqual(jasmine.objectContaining({id: 123}));
            });
        });
    });

});
