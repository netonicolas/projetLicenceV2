import { BaseEntity } from './../../shared';

export class CandidateTrueSkill implements BaseEntity {
    constructor(
        public id?: number,
        public candidateskillid?: number,
        public level?: number,
        public comment?: string,
        public idProfil?: BaseEntity,
        public idSkill?: BaseEntity,
    ) {
    }
}
