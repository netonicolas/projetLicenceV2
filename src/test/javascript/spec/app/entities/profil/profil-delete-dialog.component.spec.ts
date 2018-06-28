/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async, inject, fakeAsync, tick } from '@angular/core/testing';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { Observable } from 'rxjs/Observable';
import { JhiEventManager } from 'ng-jhipster';

import { VerifyMyCandidateTestModule } from '../../../test.module';
import { ProfilDeleteDialogComponent } from '../../../../../../main/webapp/app/entities/profil/profil-delete-dialog.component';
import { ProfilService } from '../../../../../../main/webapp/app/entities/profil/profil.service';

describe('Component Tests', () => {

    describe('Profil Management Delete Component', () => {
        let comp: ProfilDeleteDialogComponent;
        let fixture: ComponentFixture<ProfilDeleteDialogComponent>;
        let service: ProfilService;
        let mockEventManager: any;
        let mockActiveModal: any;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [VerifyMyCandidateTestModule],
                declarations: [ProfilDeleteDialogComponent],
                providers: [
                    ProfilService
                ]
            })
            .overrideTemplate(ProfilDeleteDialogComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(ProfilDeleteDialogComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(ProfilService);
            mockEventManager = fixture.debugElement.injector.get(JhiEventManager);
            mockActiveModal = fixture.debugElement.injector.get(NgbActiveModal);
        });

        describe('confirmDelete', () => {
            it('Should call delete service on confirmDelete',
                inject([],
                    fakeAsync(() => {
                        // GIVEN
                        spyOn(service, 'delete').and.returnValue(Observable.of({}));

                        // WHEN
                        comp.confirmDelete(123);
                        tick();

                        // THEN
                        expect(service.delete).toHaveBeenCalledWith(123);
                        expect(mockActiveModal.dismissSpy).toHaveBeenCalled();
                        expect(mockEventManager.broadcastSpy).toHaveBeenCalled();
                    })
                )
            );
        });
    });

});
