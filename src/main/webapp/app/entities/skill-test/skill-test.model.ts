import { BaseEntity } from './../../shared';

export class SkillTest implements BaseEntity {
    constructor(
        public id?: number,
        public date?: any,
        public skillTestskillTestResponses?: BaseEntity[],
        public skillTestSkillQuestions?: BaseEntity[],
        public jobOffer?: BaseEntity,
    ) {
    }
}
