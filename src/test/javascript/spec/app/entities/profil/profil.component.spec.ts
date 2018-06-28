/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async } from '@angular/core/testing';
import { Observable } from 'rxjs/Observable';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { VerifyMyCandidateTestModule } from '../../../test.module';
import { ProfilComponent } from '../../../../../../main/webapp/app/entities/profil/profil.component';
import { ProfilService } from '../../../../../../main/webapp/app/entities/profil/profil.service';
import { Profil } from '../../../../../../main/webapp/app/entities/profil/profil.model';

describe('Component Tests', () => {

    describe('Profil Management Component', () => {
        let comp: ProfilComponent;
        let fixture: ComponentFixture<ProfilComponent>;
        let service: ProfilService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [VerifyMyCandidateTestModule],
                declarations: [ProfilComponent],
                providers: [
                    ProfilService
                ]
            })
            .overrideTemplate(ProfilComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(ProfilComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(ProfilService);
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN
                const headers = new HttpHeaders().append('link', 'link;link');
                spyOn(service, 'query').and.returnValue(Observable.of(new HttpResponse({
                    body: [new Profil(123)],
                    headers
                })));

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(service.query).toHaveBeenCalled();
                expect(comp.profils[0]).toEqual(jasmine.objectContaining({id: 123}));
            });
        });
    });

});
