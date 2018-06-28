import { BaseEntity } from './../../shared';

export class EtudeProfil implements BaseEntity {
    constructor(
        public id?: number,
        public anneeEtudeDebut?: any,
        public anneEtudeFin?: any,
        public comment?: string,
        public idProfil?: BaseEntity,
        public idEtude?: BaseEntity,
    ) {
    }
}
