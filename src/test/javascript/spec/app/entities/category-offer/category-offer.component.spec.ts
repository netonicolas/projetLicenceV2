/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async } from '@angular/core/testing';
import { Observable } from 'rxjs/Observable';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { VerifyMyCandidateTestModule } from '../../../test.module';
import { CategoryOfferComponent } from '../../../../../../main/webapp/app/entities/category-offer/category-offer.component';
import { CategoryOfferService } from '../../../../../../main/webapp/app/entities/category-offer/category-offer.service';
import { CategoryOffer } from '../../../../../../main/webapp/app/entities/category-offer/category-offer.model';

describe('Component Tests', () => {

    describe('CategoryOffer Management Component', () => {
        let comp: CategoryOfferComponent;
        let fixture: ComponentFixture<CategoryOfferComponent>;
        let service: CategoryOfferService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [VerifyMyCandidateTestModule],
                declarations: [CategoryOfferComponent],
                providers: [
                    CategoryOfferService
                ]
            })
            .overrideTemplate(CategoryOfferComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(CategoryOfferComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(CategoryOfferService);
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN
                const headers = new HttpHeaders().append('link', 'link;link');
                spyOn(service, 'query').and.returnValue(Observable.of(new HttpResponse({
                    body: [new CategoryOffer(123)],
                    headers
                })));

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(service.query).toHaveBeenCalled();
                expect(comp.categoryOffers[0]).toEqual(jasmine.objectContaining({id: 123}));
            });
        });
    });

});
