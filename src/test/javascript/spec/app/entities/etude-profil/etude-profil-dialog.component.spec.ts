/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async, inject, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { Observable } from 'rxjs/Observable';
import { JhiEventManager } from 'ng-jhipster';

import { VerifyMyCandidateTestModule } from '../../../test.module';
import { EtudeProfilDialogComponent } from '../../../../../../main/webapp/app/entities/etude-profil/etude-profil-dialog.component';
import { EtudeProfilService } from '../../../../../../main/webapp/app/entities/etude-profil/etude-profil.service';
import { EtudeProfil } from '../../../../../../main/webapp/app/entities/etude-profil/etude-profil.model';
import { ProfilService } from '../../../../../../main/webapp/app/entities/profil';
import { EtudeService } from '../../../../../../main/webapp/app/entities/etude';

describe('Component Tests', () => {

    describe('EtudeProfil Management Dialog Component', () => {
        let comp: EtudeProfilDialogComponent;
        let fixture: ComponentFixture<EtudeProfilDialogComponent>;
        let service: EtudeProfilService;
        let mockEventManager: any;
        let mockActiveModal: any;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [VerifyMyCandidateTestModule],
                declarations: [EtudeProfilDialogComponent],
                providers: [
                    ProfilService,
                    EtudeService,
                    EtudeProfilService
                ]
            })
            .overrideTemplate(EtudeProfilDialogComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(EtudeProfilDialogComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(EtudeProfilService);
            mockEventManager = fixture.debugElement.injector.get(JhiEventManager);
            mockActiveModal = fixture.debugElement.injector.get(NgbActiveModal);
        });

        describe('save', () => {
            it('Should call update service on save for existing entity',
                inject([],
                    fakeAsync(() => {
                        // GIVEN
                        const entity = new EtudeProfil(123);
                        spyOn(service, 'update').and.returnValue(Observable.of(new HttpResponse({body: entity})));
                        comp.etudeProfil = entity;
                        // WHEN
                        comp.save();
                        tick(); // simulate async

                        // THEN
                        expect(service.update).toHaveBeenCalledWith(entity);
                        expect(comp.isSaving).toEqual(false);
                        expect(mockEventManager.broadcastSpy).toHaveBeenCalledWith({ name: 'etudeProfilListModification', content: 'OK'});
                        expect(mockActiveModal.dismissSpy).toHaveBeenCalled();
                    })
                )
            );

            it('Should call create service on save for new entity',
                inject([],
                    fakeAsync(() => {
                        // GIVEN
                        const entity = new EtudeProfil();
                        spyOn(service, 'create').and.returnValue(Observable.of(new HttpResponse({body: entity})));
                        comp.etudeProfil = entity;
                        // WHEN
                        comp.save();
                        tick(); // simulate async

                        // THEN
                        expect(service.create).toHaveBeenCalledWith(entity);
                        expect(comp.isSaving).toEqual(false);
                        expect(mockEventManager.broadcastSpy).toHaveBeenCalledWith({ name: 'etudeProfilListModification', content: 'OK'});
                        expect(mockActiveModal.dismissSpy).toHaveBeenCalled();
                    })
                )
            );
        });
    });

});
