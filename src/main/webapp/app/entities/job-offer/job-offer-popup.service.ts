import { Injectable, Component } from '@angular/core';
import { Router } from '@angular/router';
import { NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { HttpResponse } from '@angular/common/http';
import { JobOffer } from './job-offer.model';
import { JobOfferService } from './job-offer.service';

@Injectable()
export class JobOfferPopupService {
    private ngbModalRef: NgbModalRef;

    constructor(
        private modalService: NgbModal,
        private router: Router,
        private jobOfferService: JobOfferService

    ) {
        this.ngbModalRef = null;
    }

    open(component: Component, id?: number | any): Promise<NgbModalRef> {
        return new Promise<NgbModalRef>((resolve, reject) => {
            const isOpen = this.ngbModalRef !== null;
            if (isOpen) {
                resolve(this.ngbModalRef);
            }

            if (id) {
                this.jobOfferService.find(id)
                    .subscribe((jobOfferResponse: HttpResponse<JobOffer>) => {
                        const jobOffer: JobOffer = jobOfferResponse.body;
                        if (jobOffer.dateOffer) {
                            jobOffer.dateOffer = {
                                year: jobOffer.dateOffer.getFullYear(),
                                month: jobOffer.dateOffer.getMonth() + 1,
                                day: jobOffer.dateOffer.getDate()
                            };
                        }
                        this.ngbModalRef = this.jobOfferModalRef(component, jobOffer);
                        resolve(this.ngbModalRef);
                    });
            } else {
                // setTimeout used as a workaround for getting ExpressionChangedAfterItHasBeenCheckedError
                setTimeout(() => {
                    this.ngbModalRef = this.jobOfferModalRef(component, new JobOffer());
                    resolve(this.ngbModalRef);
                }, 0);
            }
        });
    }

    jobOfferModalRef(component: Component, jobOffer: JobOffer): NgbModalRef {
        const modalRef = this.modalService.open(component, { size: 'lg', backdrop: 'static'});
        modalRef.componentInstance.jobOffer = jobOffer;
        modalRef.result.then((result) => {
            this.router.navigate([{ outlets: { popup: null }}], { replaceUrl: true, queryParamsHandling: 'merge' });
            this.ngbModalRef = null;
        }, (reason) => {
            this.router.navigate([{ outlets: { popup: null }}], { replaceUrl: true, queryParamsHandling: 'merge' });
            this.ngbModalRef = null;
        });
        return modalRef;
    }
}
