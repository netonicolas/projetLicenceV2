import { Injectable, Component } from '@angular/core';
import { Router } from '@angular/router';
import { NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { HttpResponse } from '@angular/common/http';
import { EtudeProfil } from './etude-profil.model';
import { EtudeProfilService } from './etude-profil.service';

@Injectable()
export class EtudeProfilPopupService {
    private ngbModalRef: NgbModalRef;

    constructor(
        private modalService: NgbModal,
        private router: Router,
        private etudeProfilService: EtudeProfilService

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
                this.etudeProfilService.find(id)
                    .subscribe((etudeProfilResponse: HttpResponse<EtudeProfil>) => {
                        const etudeProfil: EtudeProfil = etudeProfilResponse.body;
                        if (etudeProfil.anneeEtudeDebut) {
                            etudeProfil.anneeEtudeDebut = {
                                year: etudeProfil.anneeEtudeDebut.getFullYear(),
                                month: etudeProfil.anneeEtudeDebut.getMonth() + 1,
                                day: etudeProfil.anneeEtudeDebut.getDate()
                            };
                        }
                        if (etudeProfil.anneEtudeFin) {
                            etudeProfil.anneEtudeFin = {
                                year: etudeProfil.anneEtudeFin.getFullYear(),
                                month: etudeProfil.anneEtudeFin.getMonth() + 1,
                                day: etudeProfil.anneEtudeFin.getDate()
                            };
                        }
                        this.ngbModalRef = this.etudeProfilModalRef(component, etudeProfil);
                        resolve(this.ngbModalRef);
                    });
            } else {
                // setTimeout used as a workaround for getting ExpressionChangedAfterItHasBeenCheckedError
                setTimeout(() => {
                    this.ngbModalRef = this.etudeProfilModalRef(component, new EtudeProfil());
                    resolve(this.ngbModalRef);
                }, 0);
            }
        });
    }

    etudeProfilModalRef(component: Component, etudeProfil: EtudeProfil): NgbModalRef {
        const modalRef = this.modalService.open(component, { size: 'lg', backdrop: 'static'});
        modalRef.componentInstance.etudeProfil = etudeProfil;
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
