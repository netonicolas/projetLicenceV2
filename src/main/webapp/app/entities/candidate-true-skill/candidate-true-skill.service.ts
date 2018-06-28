import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs/Observable';
import { SERVER_API_URL } from '../../app.constants';

import { CandidateTrueSkill } from './candidate-true-skill.model';
import { createRequestOption } from '../../shared';

export type EntityResponseType = HttpResponse<CandidateTrueSkill>;

@Injectable()
export class CandidateTrueSkillService {

    private resourceUrl =  SERVER_API_URL + 'api/candidate-true-skills';
    private resourceSearchUrl = SERVER_API_URL + 'api/_search/candidate-true-skills';

    constructor(private http: HttpClient) { }

    create(candidateTrueSkill: CandidateTrueSkill): Observable<EntityResponseType> {
        const copy = this.convert(candidateTrueSkill);
        return this.http.post<CandidateTrueSkill>(this.resourceUrl, copy, { observe: 'response' })
            .map((res: EntityResponseType) => this.convertResponse(res));
    }

    update(candidateTrueSkill: CandidateTrueSkill): Observable<EntityResponseType> {
        const copy = this.convert(candidateTrueSkill);
        return this.http.put<CandidateTrueSkill>(this.resourceUrl, copy, { observe: 'response' })
            .map((res: EntityResponseType) => this.convertResponse(res));
    }

    find(id: number): Observable<EntityResponseType> {
        return this.http.get<CandidateTrueSkill>(`${this.resourceUrl}/${id}`, { observe: 'response'})
            .map((res: EntityResponseType) => this.convertResponse(res));
    }

    query(req?: any): Observable<HttpResponse<CandidateTrueSkill[]>> {
        const options = createRequestOption(req);
        return this.http.get<CandidateTrueSkill[]>(this.resourceUrl, { params: options, observe: 'response' })
            .map((res: HttpResponse<CandidateTrueSkill[]>) => this.convertArrayResponse(res));
    }

    delete(id: number): Observable<HttpResponse<any>> {
        return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response'});
    }

    search(req?: any): Observable<HttpResponse<CandidateTrueSkill[]>> {
        const options = createRequestOption(req);
        return this.http.get<CandidateTrueSkill[]>(this.resourceSearchUrl, { params: options, observe: 'response' })
            .map((res: HttpResponse<CandidateTrueSkill[]>) => this.convertArrayResponse(res));
    }

    private convertResponse(res: EntityResponseType): EntityResponseType {
        const body: CandidateTrueSkill = this.convertItemFromServer(res.body);
        return res.clone({body});
    }

    private convertArrayResponse(res: HttpResponse<CandidateTrueSkill[]>): HttpResponse<CandidateTrueSkill[]> {
        const jsonResponse: CandidateTrueSkill[] = res.body;
        const body: CandidateTrueSkill[] = [];
        for (let i = 0; i < jsonResponse.length; i++) {
            body.push(this.convertItemFromServer(jsonResponse[i]));
        }
        return res.clone({body});
    }

    /**
     * Convert a returned JSON object to CandidateTrueSkill.
     */
    private convertItemFromServer(candidateTrueSkill: CandidateTrueSkill): CandidateTrueSkill {
        const copy: CandidateTrueSkill = Object.assign({}, candidateTrueSkill);
        return copy;
    }

    /**
     * Convert a CandidateTrueSkill to a JSON which can be sent to the server.
     */
    private convert(candidateTrueSkill: CandidateTrueSkill): CandidateTrueSkill {
        const copy: CandidateTrueSkill = Object.assign({}, candidateTrueSkill);
        return copy;
    }
}
