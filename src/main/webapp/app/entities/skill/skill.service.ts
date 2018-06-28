import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs/Observable';
import { SERVER_API_URL } from '../../app.constants';

import { Skill } from './skill.model';
import { createRequestOption } from '../../shared';

export type EntityResponseType = HttpResponse<Skill>;

@Injectable()
export class SkillService {

    private resourceUrl =  SERVER_API_URL + 'api/skills';
    private resourceSearchUrl = SERVER_API_URL + 'api/_search/skills';

    constructor(private http: HttpClient) { }

    create(skill: Skill): Observable<EntityResponseType> {
        const copy = this.convert(skill);
        return this.http.post<Skill>(this.resourceUrl, copy, { observe: 'response' })
            .map((res: EntityResponseType) => this.convertResponse(res));
    }

    update(skill: Skill): Observable<EntityResponseType> {
        const copy = this.convert(skill);
        return this.http.put<Skill>(this.resourceUrl, copy, { observe: 'response' })
            .map((res: EntityResponseType) => this.convertResponse(res));
    }

    find(id: number): Observable<EntityResponseType> {
        return this.http.get<Skill>(`${this.resourceUrl}/${id}`, { observe: 'response'})
            .map((res: EntityResponseType) => this.convertResponse(res));
    }

    query(req?: any): Observable<HttpResponse<Skill[]>> {
        const options = createRequestOption(req);
        return this.http.get<Skill[]>(this.resourceUrl, { params: options, observe: 'response' })
            .map((res: HttpResponse<Skill[]>) => this.convertArrayResponse(res));
    }

    delete(id: number): Observable<HttpResponse<any>> {
        return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response'});
    }

    search(req?: any): Observable<HttpResponse<Skill[]>> {
        const options = createRequestOption(req);
        return this.http.get<Skill[]>(this.resourceSearchUrl, { params: options, observe: 'response' })
            .map((res: HttpResponse<Skill[]>) => this.convertArrayResponse(res));
    }

    private convertResponse(res: EntityResponseType): EntityResponseType {
        const body: Skill = this.convertItemFromServer(res.body);
        return res.clone({body});
    }

    private convertArrayResponse(res: HttpResponse<Skill[]>): HttpResponse<Skill[]> {
        const jsonResponse: Skill[] = res.body;
        const body: Skill[] = [];
        for (let i = 0; i < jsonResponse.length; i++) {
            body.push(this.convertItemFromServer(jsonResponse[i]));
        }
        return res.clone({body});
    }

    /**
     * Convert a returned JSON object to Skill.
     */
    private convertItemFromServer(skill: Skill): Skill {
        const copy: Skill = Object.assign({}, skill);
        return copy;
    }

    /**
     * Convert a Skill to a JSON which can be sent to the server.
     */
    private convert(skill: Skill): Skill {
        const copy: Skill = Object.assign({}, skill);
        return copy;
    }
}
