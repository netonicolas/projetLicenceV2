import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs/Observable';
import { SERVER_API_URL } from '../../app.constants';

import { SkillQuestion } from './skill-question.model';
import { createRequestOption } from '../../shared';

export type EntityResponseType = HttpResponse<SkillQuestion>;

@Injectable()
export class SkillQuestionService {

    private resourceUrl =  SERVER_API_URL + 'api/skill-questions';
    private resourceSearchUrl = SERVER_API_URL + 'api/_search/skill-questions';

    constructor(private http: HttpClient) { }

    create(skillQuestion: SkillQuestion): Observable<EntityResponseType> {
        const copy = this.convert(skillQuestion);
        return this.http.post<SkillQuestion>(this.resourceUrl, copy, { observe: 'response' })
            .map((res: EntityResponseType) => this.convertResponse(res));
    }

    update(skillQuestion: SkillQuestion): Observable<EntityResponseType> {
        const copy = this.convert(skillQuestion);
        return this.http.put<SkillQuestion>(this.resourceUrl, copy, { observe: 'response' })
            .map((res: EntityResponseType) => this.convertResponse(res));
    }

    find(id: number): Observable<EntityResponseType> {
        return this.http.get<SkillQuestion>(`${this.resourceUrl}/${id}`, { observe: 'response'})
            .map((res: EntityResponseType) => this.convertResponse(res));
    }

    query(req?: any): Observable<HttpResponse<SkillQuestion[]>> {
        const options = createRequestOption(req);
        return this.http.get<SkillQuestion[]>(this.resourceUrl, { params: options, observe: 'response' })
            .map((res: HttpResponse<SkillQuestion[]>) => this.convertArrayResponse(res));
    }

    delete(id: number): Observable<HttpResponse<any>> {
        return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response'});
    }

    search(req?: any): Observable<HttpResponse<SkillQuestion[]>> {
        const options = createRequestOption(req);
        return this.http.get<SkillQuestion[]>(this.resourceSearchUrl, { params: options, observe: 'response' })
            .map((res: HttpResponse<SkillQuestion[]>) => this.convertArrayResponse(res));
    }

    private convertResponse(res: EntityResponseType): EntityResponseType {
        const body: SkillQuestion = this.convertItemFromServer(res.body);
        return res.clone({body});
    }

    private convertArrayResponse(res: HttpResponse<SkillQuestion[]>): HttpResponse<SkillQuestion[]> {
        const jsonResponse: SkillQuestion[] = res.body;
        const body: SkillQuestion[] = [];
        for (let i = 0; i < jsonResponse.length; i++) {
            body.push(this.convertItemFromServer(jsonResponse[i]));
        }
        return res.clone({body});
    }

    /**
     * Convert a returned JSON object to SkillQuestion.
     */
    private convertItemFromServer(skillQuestion: SkillQuestion): SkillQuestion {
        const copy: SkillQuestion = Object.assign({}, skillQuestion);
        return copy;
    }

    /**
     * Convert a SkillQuestion to a JSON which can be sent to the server.
     */
    private convert(skillQuestion: SkillQuestion): SkillQuestion {
        const copy: SkillQuestion = Object.assign({}, skillQuestion);
        return copy;
    }
}
