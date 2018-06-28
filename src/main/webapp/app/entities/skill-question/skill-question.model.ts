import { BaseEntity } from './../../shared';

export class SkillQuestion implements BaseEntity {
    constructor(
        public id?: number,
        public questionSkillQuestion?: string,
        public responseSkillQuestion?: string,
        public question?: BaseEntity,
        public skill?: BaseEntity,
    ) {
    }
}
