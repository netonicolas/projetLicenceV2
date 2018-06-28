/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async } from '@angular/core/testing';
import { Observable } from 'rxjs/Observable';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { VerifyMyCandidateTestModule } from '../../../test.module';
import { EtudeComponent } from '../../../../../../main/webapp/app/entities/etude/etude.component';
import { EtudeService } from '../../../../../../main/webapp/app/entities/etude/etude.service';
import { Etude } from '../../../../../../main/webapp/app/entities/etude/etude.model';

describe('Component Tests', () => {

    describe('Etude Management Component', () => {
        let comp: EtudeComponent;
        let fixture: ComponentFixture<EtudeComponent>;
        let service: EtudeService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [VerifyMyCandidateTestModule],
                declarations: [EtudeComponent],
                providers: [
                    EtudeService
                ]
            })
            .overrideTemplate(EtudeComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(EtudeComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(EtudeService);
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN
                const headers = new HttpHeaders().append('link', 'link;link');
                spyOn(service, 'query').and.returnValue(Observable.of(new HttpResponse({
                    body: [new Etude(123)],
                    headers
                })));

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(service.query).toHaveBeenCalled();
                expect(comp.etudes[0]).toEqual(jasmine.objectContaining({id: 123}));
            });
        });
    });

});
