import { BaseEntity } from './../../shared';

export const enum NiveauEtude {
    'BAC',
    'LICENCE',
    'MASTER',
    'INGENIEUR'
}

export class Etude implements BaseEntity {
    constructor(
        public id?: number,
        public libelle?: string,
        public niveauEtude?: NiveauEtude,
        public etudeJobOffers?: BaseEntity[],
        public etudeetudeProfils?: BaseEntity[],
    ) {
    }
}
