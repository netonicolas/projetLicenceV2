import { Injectable, Component } from '@angular/core';
import { Router } from '@angular/router';
import { NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { HttpResponse } from '@angular/common/http';
import { SkillQuestion } from './skill-question.model';
import { SkillQuestionService } from './skill-question.service';

@Injectable()
export class SkillQuestionPopupService {
    private ngbModalRef: NgbModalRef;

    constructor(
        private modalService: NgbModal,
        private router: Router,
        private skillQuestionService: SkillQuestionService

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
                this.skillQuestionService.find(id)
                    .subscribe((skillQuestionResponse: HttpResponse<SkillQuestion>) => {
                        const skillQuestion: SkillQuestion = skillQuestionResponse.body;
                        this.ngbModalRef = this.skillQuestionModalRef(component, skillQuestion);
                        resolve(this.ngbModalRef);
                    });
            } else {
                // setTimeout used as a workaround for getting ExpressionChangedAfterItHasBeenCheckedError
                setTimeout(() => {
                    this.ngbModalRef = this.skillQuestionModalRef(component, new SkillQuestion());
                    resolve(this.ngbModalRef);
                }, 0);
            }
        });
    }

    skillQuestionModalRef(component: Component, skillQuestion: SkillQuestion): NgbModalRef {
        const modalRef = this.modalService.open(component, { size: 'lg', backdrop: 'static'});
        modalRef.componentInstance.skillQuestion = skillQuestion;
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
