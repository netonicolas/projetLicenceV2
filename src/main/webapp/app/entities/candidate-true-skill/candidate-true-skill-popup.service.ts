import { Injectable, Component } from '@angular/core';
import { Router } from '@angular/router';
import { NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { HttpResponse } from '@angular/common/http';
import { CandidateTrueSkill } from './candidate-true-skill.model';
import { CandidateTrueSkillService } from './candidate-true-skill.service';

@Injectable()
export class CandidateTrueSkillPopupService {
    private ngbModalRef: NgbModalRef;

    constructor(
        private modalService: NgbModal,
        private router: Router,
        private candidateTrueSkillService: CandidateTrueSkillService

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
                this.candidateTrueSkillService.find(id)
                    .subscribe((candidateTrueSkillResponse: HttpResponse<CandidateTrueSkill>) => {
                        const candidateTrueSkill: CandidateTrueSkill = candidateTrueSkillResponse.body;
                        this.ngbModalRef = this.candidateTrueSkillModalRef(component, candidateTrueSkill);
                        resolve(this.ngbModalRef);
                    });
            } else {
                // setTimeout used as a workaround for getting ExpressionChangedAfterItHasBeenCheckedError
                setTimeout(() => {
                    this.ngbModalRef = this.candidateTrueSkillModalRef(component, new CandidateTrueSkill());
                    resolve(this.ngbModalRef);
                }, 0);
            }
        });
    }

    candidateTrueSkillModalRef(component: Component, candidateTrueSkill: CandidateTrueSkill): NgbModalRef {
        const modalRef = this.modalService.open(component, { size: 'lg', backdrop: 'static'});
        modalRef.componentInstance.candidateTrueSkill = candidateTrueSkill;
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
