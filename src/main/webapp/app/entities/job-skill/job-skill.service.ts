import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs/Observable';
import { SERVER_API_URL } from '../../app.constants';

import { JobSkill } from './job-skill.model';
import { createRequestOption } from '../../shared';

export type EntityResponseType = HttpResponse<JobSkill>;

@Injectable()
export class JobSkillService {

    private resourceUrl =  SERVER_API_URL + 'api/job-skills';
    private resourceSearchUrl = SERVER_API_URL + 'api/_search/job-skills';
    private jobSkillRessourceUrl= SERVER_API_URL + "api/job-skills/jobId";

    constructor(private http: HttpClient) { }

    create(jobSkill: JobSkill): Observable<EntityResponseType> {
        const copy = this.convert(jobSkill);
        return this.http.post<JobSkill>(this.resourceUrl, copy, { observe: 'response' })
            .map((res: EntityResponseType) => this.convertResponse(res));
    }

    update(jobSkill: JobSkill): Observable<EntityResponseType> {
        const copy = this.convert(jobSkill);
        return this.http.put<JobSkill>(this.resourceUrl, copy, { observe: 'response' })
            .map((res: EntityResponseType) => this.convertResponse(res));
    }

    find(id: number): Observable<EntityResponseType> {
        return this.http.get<JobSkill>(`${this.resourceUrl}/${id}`, { observe: 'response'})
            .map((res: EntityResponseType) => this.convertResponse(res));
    }

    query(req?: any): Observable<HttpResponse<JobSkill[]>> {
        const options = createRequestOption(req);
        return this.http.get<JobSkill[]>(this.resourceUrl, { params: options, observe: 'response' })
            .map((res: HttpResponse<JobSkill[]>) => this.convertArrayResponse(res));
    }

    delete(id: number): Observable<HttpResponse<any>> {
        return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response'});
    }

    search(req?: any): Observable<HttpResponse<JobSkill[]>> {
        const options = createRequestOption(req);
        return this.http.get<JobSkill[]>(this.resourceSearchUrl, { params: options, observe: 'response' })
            .map((res: HttpResponse<JobSkill[]>) => this.convertArrayResponse(res));
    }

    findJobSkillByJobOffer(id: number): Observable<HttpResponse<JobSkill[]>> {
        return this.http.get<JobSkill[]>(`${this.jobSkillRessourceUrl}/${id}`, { observe: 'response'})
            .map((res: HttpResponse<JobSkill[]>) => this.convertArrayResponse(res));

    }

    private convertResponse(res: EntityResponseType): EntityResponseType {
        const body: JobSkill = this.convertItemFromServer(res.body);
        return res.clone({body});
    }

    private convertArrayResponse(res: HttpResponse<JobSkill[]>): HttpResponse<JobSkill[]> {
        const jsonResponse: JobSkill[] = res.body;
        const body: JobSkill[] = [];
        for (let i = 0; i < jsonResponse.length; i++) {
            body.push(this.convertItemFromServer(jsonResponse[i]));
        }
        return res.clone({body});
    }

    /**
     * Convert a returned JSON object to JobSkill.
     */
    private convertItemFromServer(jobSkill: JobSkill): JobSkill {
        const copy: JobSkill = Object.assign({}, jobSkill);
        return copy;
    }

    /**
     * Convert a JobSkill to a JSON which can be sent to the server.
     */
    private convert(jobSkill: JobSkill): JobSkill {
        const copy: JobSkill = Object.assign({}, jobSkill);
        return copy;
    }
}
