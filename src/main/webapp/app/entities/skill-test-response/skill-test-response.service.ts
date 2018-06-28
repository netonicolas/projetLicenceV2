import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs/Observable';
import { SERVER_API_URL } from '../../app.constants';

import { JhiDateUtils } from 'ng-jhipster';

import { SkillTestResponse } from './skill-test-response.model';
import { createRequestOption } from '../../shared';

export type EntityResponseType = HttpResponse<SkillTestResponse>;

@Injectable()
export class SkillTestResponseService {

    private resourceUrl =  SERVER_API_URL + 'api/skill-test-responses';
    private resourceSearchUrl = SERVER_API_URL + 'api/_search/skill-test-responses';

    constructor(private http: HttpClient, private dateUtils: JhiDateUtils) { }

    create(skillTestResponse: SkillTestResponse): Observable<EntityResponseType> {
        const copy = this.convert(skillTestResponse);
        return this.http.post<SkillTestResponse>(this.resourceUrl, copy, { observe: 'response' })
            .map((res: EntityResponseType) => this.convertResponse(res));
    }

    update(skillTestResponse: SkillTestResponse): Observable<EntityResponseType> {
        const copy = this.convert(skillTestResponse);
        return this.http.put<SkillTestResponse>(this.resourceUrl, copy, { observe: 'response' })
            .map((res: EntityResponseType) => this.convertResponse(res));
    }

    find(id: number): Observable<EntityResponseType> {
        return this.http.get<SkillTestResponse>(`${this.resourceUrl}/${id}`, { observe: 'response'})
            .map((res: EntityResponseType) => this.convertResponse(res));
    }

    query(req?: any): Observable<HttpResponse<SkillTestResponse[]>> {
        const options = createRequestOption(req);
        return this.http.get<SkillTestResponse[]>(this.resourceUrl, { params: options, observe: 'response' })
            .map((res: HttpResponse<SkillTestResponse[]>) => this.convertArrayResponse(res));
    }

    delete(id: number): Observable<HttpResponse<any>> {
        return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response'});
    }

    search(req?: any): Observable<HttpResponse<SkillTestResponse[]>> {
        const options = createRequestOption(req);
        return this.http.get<SkillTestResponse[]>(this.resourceSearchUrl, { params: options, observe: 'response' })
            .map((res: HttpResponse<SkillTestResponse[]>) => this.convertArrayResponse(res));
    }

    private convertResponse(res: EntityResponseType): EntityResponseType {
        const body: SkillTestResponse = this.convertItemFromServer(res.body);
        return res.clone({body});
    }

    private convertArrayResponse(res: HttpResponse<SkillTestResponse[]>): HttpResponse<SkillTestResponse[]> {
        const jsonResponse: SkillTestResponse[] = res.body;
        const body: SkillTestResponse[] = [];
        for (let i = 0; i < jsonResponse.length; i++) {
            body.push(this.convertItemFromServer(jsonResponse[i]));
        }
        return res.clone({body});
    }

    /**
     * Convert a returned JSON object to SkillTestResponse.
     */
    private convertItemFromServer(skillTestResponse: SkillTestResponse): SkillTestResponse {
        const copy: SkillTestResponse = Object.assign({}, skillTestResponse);
        copy.date = this.dateUtils
            .convertLocalDateFromServer(skillTestResponse.date);
        return copy;
    }

    /**
     * Convert a SkillTestResponse to a JSON which can be sent to the server.
     */
    private convert(skillTestResponse: SkillTestResponse): SkillTestResponse {
        const copy: SkillTestResponse = Object.assign({}, skillTestResponse);
        copy.date = this.dateUtils
            .convertLocalDateToServer(skillTestResponse.date);
        return copy;
    }
}
