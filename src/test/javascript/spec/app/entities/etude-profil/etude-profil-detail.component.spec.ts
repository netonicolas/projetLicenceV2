/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs/Observable';

import { VerifyMyCandidateTestModule } from '../../../test.module';
import { EtudeProfilDetailComponent } from '../../../../../../main/webapp/app/entities/etude-profil/etude-profil-detail.component';
import { EtudeProfilService } from '../../../../../../main/webapp/app/entities/etude-profil/etude-profil.service';
import { EtudeProfil } from '../../../../../../main/webapp/app/entities/etude-profil/etude-profil.model';

describe('Component Tests', () => {

    describe('EtudeProfil Management Detail Component', () => {
        let comp: EtudeProfilDetailComponent;
        let fixture: ComponentFixture<EtudeProfilDetailComponent>;
        let service: EtudeProfilService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [VerifyMyCandidateTestModule],
                declarations: [EtudeProfilDetailComponent],
                providers: [
                    EtudeProfilService
                ]
            })
            .overrideTemplate(EtudeProfilDetailComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(EtudeProfilDetailComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(EtudeProfilService);
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN

                spyOn(service, 'find').and.returnValue(Observable.of(new HttpResponse({
                    body: new EtudeProfil(123)
                })));

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(service.find).toHaveBeenCalledWith(123);
                expect(comp.etudeProfil).toEqual(jasmine.objectContaining({id: 123}));
            });
        });
    });

});
