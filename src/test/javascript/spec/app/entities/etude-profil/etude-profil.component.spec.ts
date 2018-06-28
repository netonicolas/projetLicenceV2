/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async } from '@angular/core/testing';
import { Observable } from 'rxjs/Observable';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { VerifyMyCandidateTestModule } from '../../../test.module';
import { EtudeProfilComponent } from '../../../../../../main/webapp/app/entities/etude-profil/etude-profil.component';
import { EtudeProfilService } from '../../../../../../main/webapp/app/entities/etude-profil/etude-profil.service';
import { EtudeProfil } from '../../../../../../main/webapp/app/entities/etude-profil/etude-profil.model';

describe('Component Tests', () => {

    describe('EtudeProfil Management Component', () => {
        let comp: EtudeProfilComponent;
        let fixture: ComponentFixture<EtudeProfilComponent>;
        let service: EtudeProfilService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [VerifyMyCandidateTestModule],
                declarations: [EtudeProfilComponent],
                providers: [
                    EtudeProfilService
                ]
            })
            .overrideTemplate(EtudeProfilComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(EtudeProfilComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(EtudeProfilService);
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN
                const headers = new HttpHeaders().append('link', 'link;link');
                spyOn(service, 'query').and.returnValue(Observable.of(new HttpResponse({
                    body: [new EtudeProfil(123)],
                    headers
                })));

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(service.query).toHaveBeenCalled();
                expect(comp.etudeProfils[0]).toEqual(jasmine.objectContaining({id: 123}));
            });
        });
    });

});
