/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs/Observable';

import { VerifyMyCandidateTestModule } from '../../../test.module';
import { CategoryOfferDetailComponent } from '../../../../../../main/webapp/app/entities/category-offer/category-offer-detail.component';
import { CategoryOfferService } from '../../../../../../main/webapp/app/entities/category-offer/category-offer.service';
import { CategoryOffer } from '../../../../../../main/webapp/app/entities/category-offer/category-offer.model';

describe('Component Tests', () => {

    describe('CategoryOffer Management Detail Component', () => {
        let comp: CategoryOfferDetailComponent;
        let fixture: ComponentFixture<CategoryOfferDetailComponent>;
        let service: CategoryOfferService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [VerifyMyCandidateTestModule],
                declarations: [CategoryOfferDetailComponent],
                providers: [
                    CategoryOfferService
                ]
            })
            .overrideTemplate(CategoryOfferDetailComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(CategoryOfferDetailComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(CategoryOfferService);
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN

                spyOn(service, 'find').and.returnValue(Observable.of(new HttpResponse({
                    body: new CategoryOffer(123)
                })));

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(service.find).toHaveBeenCalledWith(123);
                expect(comp.categoryOffer).toEqual(jasmine.objectContaining({id: 123}));
            });
        });
    });

});
