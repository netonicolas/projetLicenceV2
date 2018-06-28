import { BaseEntity } from './../../shared';

export class Skill implements BaseEntity {
    constructor(
        public id?: number,
        public nameSkill?: string,
        public skilljobSkills?: BaseEntity[],
        public skillcandidateSkills?: BaseEntity[],
        public skillcandidateTrueSkills?: BaseEntity[],
        public skillskillQuestions?: BaseEntity[],
    ) {
    }
}
