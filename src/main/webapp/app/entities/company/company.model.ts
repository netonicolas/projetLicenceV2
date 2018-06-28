import { BaseEntity } from './../../shared';

export class Company implements BaseEntity {
    constructor(
        public id?: number,
        public companyName?: string,
        public telephoneEntreprise?: string,
        public place?: string,
        public siren?: number,
        public logo?: string,
        public userId?: number,
        public companies?: BaseEntity[],
    ) {
    }
}
