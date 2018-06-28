import { BaseEntity } from './../../shared';

export class SkillTestResponse implements BaseEntity {
    constructor(
        public id?: number,
        public responseSkillTestResponse?: string,
        public date?: any,
        public skillTest?: BaseEntity,
        public candidat?: BaseEntity,
    ) {
    }
}
