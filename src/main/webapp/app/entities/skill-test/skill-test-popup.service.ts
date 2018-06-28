import { Injectable, Component } from '@angular/core';
import { Router } from '@angular/router';
import { NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { HttpResponse } from '@angular/common/http';
import { SkillTest } from './skill-test.model';
import { SkillTestService } from './skill-test.service';

@Injectable()
export class SkillTestPopupService {
    private ngbModalRef: NgbModalRef;

    constructor(
        private modalService: NgbModal,
        private router: Router,
        private skillTestService: SkillTestService

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
                this.skillTestService.find(id)
                    .subscribe((skillTestResponse: HttpResponse<SkillTest>) => {
                        const skillTest: SkillTest = skillTestResponse.body;
                        if (skillTest.date) {
                            skillTest.date = {
                                year: skillTest.date.getFullYear(),
                                month: skillTest.date.getMonth() + 1,
                                day: skillTest.date.getDate()
                            };
                        }
                        this.ngbModalRef = this.skillTestModalRef(component, skillTest);
                        resolve(this.ngbModalRef);
                    });
            } else {
                // setTimeout used as a workaround for getting ExpressionChangedAfterItHasBeenCheckedError
                setTimeout(() => {
                    this.ngbModalRef = this.skillTestModalRef(component, new SkillTest());
                    resolve(this.ngbModalRef);
                }, 0);
            }
        });
    }

    skillTestModalRef(component: Component, skillTest: SkillTest): NgbModalRef {
        const modalRef = this.modalService.open(component, { size: 'lg', backdrop: 'static'});
        modalRef.componentInstance.skillTest = skillTest;
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
