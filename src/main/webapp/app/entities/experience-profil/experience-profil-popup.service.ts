import { Injectable, Component } from '@angular/core';
import { Router } from '@angular/router';
import { NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { HttpResponse } from '@angular/common/http';
import { ExperienceProfil } from './experience-profil.model';
import { ExperienceProfilService } from './experience-profil.service';

@Injectable()
export class ExperienceProfilPopupService {
    private ngbModalRef: NgbModalRef;

    constructor(
        private modalService: NgbModal,
        private router: Router,
        private experienceProfilService: ExperienceProfilService

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
                this.experienceProfilService.find(id)
                    .subscribe((experienceProfilResponse: HttpResponse<ExperienceProfil>) => {
                        const experienceProfil: ExperienceProfil = experienceProfilResponse.body;
                        if (experienceProfil.anneExperienceDebut) {
                            experienceProfil.anneExperienceDebut = {
                                year: experienceProfil.anneExperienceDebut.getFullYear(),
                                month: experienceProfil.anneExperienceDebut.getMonth() + 1,
                                day: experienceProfil.anneExperienceDebut.getDate()
                            };
                        }
                        if (experienceProfil.anneExperienceFin) {
                            experienceProfil.anneExperienceFin = {
                                year: experienceProfil.anneExperienceFin.getFullYear(),
                                month: experienceProfil.anneExperienceFin.getMonth() + 1,
                                day: experienceProfil.anneExperienceFin.getDate()
                            };
                        }
                        this.ngbModalRef = this.experienceProfilModalRef(component, experienceProfil);
                        resolve(this.ngbModalRef);
                    });
            } else {
                // setTimeout used as a workaround for getting ExpressionChangedAfterItHasBeenCheckedError
                setTimeout(() => {
                    this.ngbModalRef = this.experienceProfilModalRef(component, new ExperienceProfil());
                    resolve(this.ngbModalRef);
                }, 0);
            }
        });
    }

    experienceProfilModalRef(component: Component, experienceProfil: ExperienceProfil): NgbModalRef {
        const modalRef = this.modalService.open(component, { size: 'lg', backdrop: 'static'});
        modalRef.componentInstance.experienceProfil = experienceProfil;
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
