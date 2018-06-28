import { BaseEntity } from './../../shared';

export class ExperienceProfil implements BaseEntity {
    constructor(
        public id?: number,
        public anneExperienceDebut?: any,
        public anneExperienceFin?: any,
        public comment?: string,
        public idProfil?: BaseEntity,
        public idExperience?: BaseEntity,
    ) {
    }
}
