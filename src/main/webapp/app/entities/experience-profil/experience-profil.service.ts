import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs/Observable';
import { SERVER_API_URL } from '../../app.constants';

import { JhiDateUtils } from 'ng-jhipster';

import { ExperienceProfil } from './experience-profil.model';
import { createRequestOption } from '../../shared';

export type EntityResponseType = HttpResponse<ExperienceProfil>;

@Injectable()
export class ExperienceProfilService {

    private resourceUrl =  SERVER_API_URL + 'api/experience-profils';
    private resourceSearchUrl = SERVER_API_URL + 'api/_search/experience-profils';

    constructor(private http: HttpClient, private dateUtils: JhiDateUtils) { }

    create(experienceProfil: ExperienceProfil): Observable<EntityResponseType> {
        const copy = this.convert(experienceProfil);
        return this.http.post<ExperienceProfil>(this.resourceUrl, copy, { observe: 'response' })
            .map((res: EntityResponseType) => this.convertResponse(res));
    }

    update(experienceProfil: ExperienceProfil): Observable<EntityResponseType> {
        const copy = this.convert(experienceProfil);
        return this.http.put<ExperienceProfil>(this.resourceUrl, copy, { observe: 'response' })
            .map((res: EntityResponseType) => this.convertResponse(res));
    }

    find(id: number): Observable<EntityResponseType> {
        return this.http.get<ExperienceProfil>(`${this.resourceUrl}/${id}`, { observe: 'response'})
            .map((res: EntityResponseType) => this.convertResponse(res));
    }

    query(req?: any): Observable<HttpResponse<ExperienceProfil[]>> {
        const options = createRequestOption(req);
        return this.http.get<ExperienceProfil[]>(this.resourceUrl, { params: options, observe: 'response' })
            .map((res: HttpResponse<ExperienceProfil[]>) => this.convertArrayResponse(res));
    }

    delete(id: number): Observable<HttpResponse<any>> {
        return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response'});
    }

    search(req?: any): Observable<HttpResponse<ExperienceProfil[]>> {
        const options = createRequestOption(req);
        return this.http.get<ExperienceProfil[]>(this.resourceSearchUrl, { params: options, observe: 'response' })
            .map((res: HttpResponse<ExperienceProfil[]>) => this.convertArrayResponse(res));
    }

    private convertResponse(res: EntityResponseType): EntityResponseType {
        const body: ExperienceProfil = this.convertItemFromServer(res.body);
        return res.clone({body});
    }

    private convertArrayResponse(res: HttpResponse<ExperienceProfil[]>): HttpResponse<ExperienceProfil[]> {
        const jsonResponse: ExperienceProfil[] = res.body;
        const body: ExperienceProfil[] = [];
        for (let i = 0; i < jsonResponse.length; i++) {
            body.push(this.convertItemFromServer(jsonResponse[i]));
        }
        return res.clone({body});
    }

    /**
     * Convert a returned JSON object to ExperienceProfil.
     */
    private convertItemFromServer(experienceProfil: ExperienceProfil): ExperienceProfil {
        const copy: ExperienceProfil = Object.assign({}, experienceProfil);
        copy.anneExperienceDebut = this.dateUtils
            .convertLocalDateFromServer(experienceProfil.anneExperienceDebut);
        copy.anneExperienceFin = this.dateUtils
            .convertLocalDateFromServer(experienceProfil.anneExperienceFin);
        return copy;
    }

    /**
     * Convert a ExperienceProfil to a JSON which can be sent to the server.
     */
    private convert(experienceProfil: ExperienceProfil): ExperienceProfil {
        const copy: ExperienceProfil = Object.assign({}, experienceProfil);
        copy.anneExperienceDebut = this.dateUtils
            .convertLocalDateToServer(experienceProfil.anneExperienceDebut);
        copy.anneExperienceFin = this.dateUtils
            .convertLocalDateToServer(experienceProfil.anneExperienceFin);
        return copy;
    }
}
