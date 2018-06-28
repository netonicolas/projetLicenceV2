/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs/Observable';

import { VerifyMyCandidateTestModule } from '../../../test.module';
import { EtudeDetailComponent } from '../../../../../../main/webapp/app/entities/etude/etude-detail.component';
import { EtudeService } from '../../../../../../main/webapp/app/entities/etude/etude.service';
import { Etude } from '../../../../../../main/webapp/app/entities/etude/etude.model';

describe('Component Tests', () => {

    describe('Etude Management Detail Component', () => {
        let comp: EtudeDetailComponent;
        let fixture: ComponentFixture<EtudeDetailComponent>;
        let service: EtudeService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [VerifyMyCandidateTestModule],
                declarations: [EtudeDetailComponent],
                providers: [
                    EtudeService
                ]
            })
            .overrideTemplate(EtudeDetailComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(EtudeDetailComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(EtudeService);
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN

                spyOn(service, 'find').and.returnValue(Observable.of(new HttpResponse({
                    body: new Etude(123)
                })));

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(service.find).toHaveBeenCalledWith(123);
                expect(comp.etude).toEqual(jasmine.objectContaining({id: 123}));
            });
        });
    });

});
