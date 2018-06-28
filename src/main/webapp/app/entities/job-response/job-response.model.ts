import { BaseEntity } from './../../shared';

export class JobResponse implements BaseEntity {
    constructor(
        public id?: number,
        public comment?: string,
        public dateResponse?: string,
        public candidat?: BaseEntity,
        public jobOffer?: BaseEntity,
    ) {
    }
}
