import { BaseEntity } from './../../shared';

export class Experience implements BaseEntity {
    constructor(
        public id?: number,
        public libelle?: string,
        public experienceexperienceProfils?: BaseEntity[],
    ) {
    }
}
