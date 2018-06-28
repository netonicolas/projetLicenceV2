/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs/Observable';

import { VerifyMyCandidateTestModule } from '../../../test.module';
import { ProfilDetailComponent } from '../../../../../../main/webapp/app/entities/profil/profil-detail.component';
import { ProfilService } from '../../../../../../main/webapp/app/entities/profil/profil.service';
import { Profil } from '../../../../../../main/webapp/app/entities/profil/profil.model';

describe('Component Tests', () => {

    describe('Profil Management Detail Component', () => {
        let comp: ProfilDetailComponent;
        let fixture: ComponentFixture<ProfilDetailComponent>;
        let service: ProfilService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [VerifyMyCandidateTestModule],
                declarations: [ProfilDetailComponent],
                providers: [
                    ProfilService
                ]
            })
            .overrideTemplate(ProfilDetailComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(ProfilDetailComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(ProfilService);
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN

                spyOn(service, 'find').and.returnValue(Observable.of(new HttpResponse({
                    body: new Profil(123)
                })));

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(service.find).toHaveBeenCalledWith(123);
                expect(comp.profil).toEqual(jasmine.objectContaining({id: 123}));
            });
        });
    });

});
