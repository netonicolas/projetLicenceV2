import { Injectable, Component } from '@angular/core';
import { Router } from '@angular/router';
import { NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { HttpResponse } from '@angular/common/http';
import { SkillTestResponse } from './skill-test-response.model';
import { SkillTestResponseService } from './skill-test-response.service';

@Injectable()
export class SkillTestResponsePopupService {
    private ngbModalRef: NgbModalRef;

    constructor(
        private modalService: NgbModal,
        private router: Router,
        private skillTestResponseService: SkillTestResponseService

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
                this.skillTestResponseService.find(id)
                    .subscribe((skillTestResponseResponse: HttpResponse<SkillTestResponse>) => {
                        const skillTestResponse: SkillTestResponse = skillTestResponseResponse.body;
                        if (skillTestResponse.date) {
                            skillTestResponse.date = {
                                year: skillTestResponse.date.getFullYear(),
                                month: skillTestResponse.date.getMonth() + 1,
                                day: skillTestResponse.date.getDate()
                            };
                        }
                        this.ngbModalRef = this.skillTestResponseModalRef(component, skillTestResponse);
                        resolve(this.ngbModalRef);
                    });
            } else {
                // setTimeout used as a workaround for getting ExpressionChangedAfterItHasBeenCheckedError
                setTimeout(() => {
                    this.ngbModalRef = this.skillTestResponseModalRef(component, new SkillTestResponse());
                    resolve(this.ngbModalRef);
                }, 0);
            }
        });
    }

    skillTestResponseModalRef(component: Component, skillTestResponse: SkillTestResponse): NgbModalRef {
        const modalRef = this.modalService.open(component, { size: 'lg', backdrop: 'static'});
        modalRef.componentInstance.skillTestResponse = skillTestResponse;
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
