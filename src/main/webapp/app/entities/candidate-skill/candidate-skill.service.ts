import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs/Observable';
import { SERVER_API_URL } from '../../app.constants';

import { CandidateSkill } from './candidate-skill.model';
import { createRequestOption } from '../../shared';

export type EntityResponseType = HttpResponse<CandidateSkill>;

@Injectable()
export class CandidateSkillService {

    private resourceUrl =  SERVER_API_URL + 'api/candidate-skills';
    private resourceSearchUrl = SERVER_API_URL + 'api/_search/candidate-skills';

    constructor(private http: HttpClient) { }

    create(candidateSkill: CandidateSkill): Observable<EntityResponseType> {
        const copy = this.convert(candidateSkill);
        return this.http.post<CandidateSkill>(this.resourceUrl, copy, { observe: 'response' })
            .map((res: EntityResponseType) => this.convertResponse(res));
    }

    update(candidateSkill: CandidateSkill): Observable<EntityResponseType> {
        const copy = this.convert(candidateSkill);
        return this.http.put<CandidateSkill>(this.resourceUrl, copy, { observe: 'response' })
            .map((res: EntityResponseType) => this.convertResponse(res));
    }

    find(id: number): Observable<EntityResponseType> {
        return this.http.get<CandidateSkill>(`${this.resourceUrl}/${id}`, { observe: 'response'})
            .map((res: EntityResponseType) => this.convertResponse(res));
    }

    query(req?: any): Observable<HttpResponse<CandidateSkill[]>> {
        const options = createRequestOption(req);
        return this.http.get<CandidateSkill[]>(this.resourceUrl, { params: options, observe: 'response' })
            .map((res: HttpResponse<CandidateSkill[]>) => this.convertArrayResponse(res));
    }

    delete(id: number): Observable<HttpResponse<any>> {
        return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response'});
    }

    search(req?: any): Observable<HttpResponse<CandidateSkill[]>> {
        const options = createRequestOption(req);
        return this.http.get<CandidateSkill[]>(this.resourceSearchUrl, { params: options, observe: 'response' })
            .map((res: HttpResponse<CandidateSkill[]>) => this.convertArrayResponse(res));
    }

    private convertResponse(res: EntityResponseType): EntityResponseType {
        const body: CandidateSkill = this.convertItemFromServer(res.body);
        return res.clone({body});
    }

    private convertArrayResponse(res: HttpResponse<CandidateSkill[]>): HttpResponse<CandidateSkill[]> {
        const jsonResponse: CandidateSkill[] = res.body;
        const body: CandidateSkill[] = [];
        for (let i = 0; i < jsonResponse.length; i++) {
            body.push(this.convertItemFromServer(jsonResponse[i]));
        }
        return res.clone({body});
    }

    /**
     * Convert a returned JSON object to CandidateSkill.
     */
    private convertItemFromServer(candidateSkill: CandidateSkill): CandidateSkill {
        const copy: CandidateSkill = Object.assign({}, candidateSkill);
        return copy;
    }

    /**
     * Convert a CandidateSkill to a JSON which can be sent to the server.
     */
    private convert(candidateSkill: CandidateSkill): CandidateSkill {
        const copy: CandidateSkill = Object.assign({}, candidateSkill);
        return copy;
    }
}
