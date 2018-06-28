import { BaseEntity } from './../../shared';

export class CandidateSkill implements BaseEntity {
    constructor(
        public id?: number,
        public level?: number,
        public comment?: string,
        public idProfil?: BaseEntity,
        public idSkill?: BaseEntity,
    ) {
    }
}
