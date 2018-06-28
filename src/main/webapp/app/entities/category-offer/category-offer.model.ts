import { BaseEntity } from './../../shared';

export class CategoryOffer implements BaseEntity {
    constructor(
        public id?: number,
        public nameCategory?: string,
        public categories?: BaseEntity[],
    ) {
    }
}
