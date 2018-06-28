import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs/Observable';
import { SERVER_API_URL } from '../../app.constants';

import { JhiDateUtils } from 'ng-jhipster';

import { SkillTest } from './skill-test.model';
import { createRequestOption } from '../../shared';

export type EntityResponseType = HttpResponse<SkillTest>;

@Injectable()
export class SkillTestService {

    private resourceUrl =  SERVER_API_URL + 'api/skill-tests';
    private resourceSearchUrl = SERVER_API_URL + 'api/_search/skill-tests';

    constructor(private http: HttpClient, private dateUtils: JhiDateUtils) { }

    create(skillTest: SkillTest): Observable<EntityResponseType> {
        const copy = this.convert(skillTest);
        return this.http.post<SkillTest>(this.resourceUrl, copy, { observe: 'response' })
            .map((res: EntityResponseType) => this.convertResponse(res));
    }

    update(skillTest: SkillTest): Observable<EntityResponseType> {
        const copy = this.convert(skillTest);
        return this.http.put<SkillTest>(this.resourceUrl, copy, { observe: 'response' })
            .map((res: EntityResponseType) => this.convertResponse(res));
    }

    find(id: number): Observable<EntityResponseType> {
        return this.http.get<SkillTest>(`${this.resourceUrl}/${id}`, { observe: 'response'})
            .map((res: EntityResponseType) => this.convertResponse(res));
    }

    query(req?: any): Observable<HttpResponse<SkillTest[]>> {
        const options = createRequestOption(req);
        return this.http.get<SkillTest[]>(this.resourceUrl, { params: options, observe: 'response' })
            .map((res: HttpResponse<SkillTest[]>) => this.convertArrayResponse(res));
    }

    delete(id: number): Observable<HttpResponse<any>> {
        return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response'});
    }

    search(req?: any): Observable<HttpResponse<SkillTest[]>> {
        const options = createRequestOption(req);
        return this.http.get<SkillTest[]>(this.resourceSearchUrl, { params: options, observe: 'response' })
            .map((res: HttpResponse<SkillTest[]>) => this.convertArrayResponse(res));
    }

    private convertResponse(res: EntityResponseType): EntityResponseType {
        const body: SkillTest = this.convertItemFromServer(res.body);
        return res.clone({body});
    }

    private convertArrayResponse(res: HttpResponse<SkillTest[]>): HttpResponse<SkillTest[]> {
        const jsonResponse: SkillTest[] = res.body;
        const body: SkillTest[] = [];
        for (let i = 0; i < jsonResponse.length; i++) {
            body.push(this.convertItemFromServer(jsonResponse[i]));
        }
        return res.clone({body});
    }

    /**
     * Convert a returned JSON object to SkillTest.
     */
    private convertItemFromServer(skillTest: SkillTest): SkillTest {
        const copy: SkillTest = Object.assign({}, skillTest);
        copy.date = this.dateUtils
            .convertLocalDateFromServer(skillTest.date);
        return copy;
    }

    /**
     * Convert a SkillTest to a JSON which can be sent to the server.
     */
    private convert(skillTest: SkillTest): SkillTest {
        const copy: SkillTest = Object.assign({}, skillTest);
        copy.date = this.dateUtils
            .convertLocalDateToServer(skillTest.date);
        return copy;
    }
}
