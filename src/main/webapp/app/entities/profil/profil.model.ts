import { BaseEntity } from './../../shared';

export class Profil implements BaseEntity {
    constructor(
        public id?: number,
        public userId?: number,
        public profilcandidateSkills?: BaseEntity[],
        public profilcandidateTrueSkills?: BaseEntity[],
        public candidatskillTestResponses?: BaseEntity[],
        public candidatjobResponses?: BaseEntity[],
        public profiletudeProfils?: BaseEntity[],
        public profilexperienceProfils?: BaseEntity[],
    ) {
    }
}
