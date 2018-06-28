import { BaseEntity } from './../../shared';

export class JobSkill implements BaseEntity {
    constructor(
        public id?: number,
        public level?: number,
        public optimal?: boolean,
        public weight?: number,
        public commentJobSkill?: string,
        public skill?: BaseEntity,
        public job?: BaseEntity,
    ) {
        this.optimal = false;
    }
}
