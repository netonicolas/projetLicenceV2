import { BaseEntity } from './../../shared';

export const enum TypeOffre {
    'CDI',
    'CDD',
    'STAGE'
}

export class JobOffer implements BaseEntity {
    constructor(
        public id?: number,
        public titleOffer?: string,
        public descriptionOffer?: string,
        public salairyMin?: number,
        public salairyMax?: number,
        public typeOffer?: TypeOffre,
        public dateOffer?: any,
        public comment?: string,
        public place?: string,
        public contact?: string,
        public status?: string,
        public jobsjobSkills?: BaseEntity[],
        public idProfilSkillTests?: BaseEntity[],
        public jobjobResponses?: BaseEntity[],
        public company?: BaseEntity,
        public categoryOffer?: BaseEntity,
        public etude?: BaseEntity,
    ) {
    }
}
