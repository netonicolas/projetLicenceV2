import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs/Observable';
import { SERVER_API_URL } from '../../app.constants';

import { JobResponse } from './job-response.model';
import { createRequestOption } from '../../shared';

export type EntityResponseType = HttpResponse<JobResponse>;

@Injectable()
export class JobResponseService {

    private resourceUrl =  SERVER_API_URL + 'api/job-responses';
    private resourceSearchUrl = SERVER_API_URL + 'api/_search/job-responses';

    constructor(private http: HttpClient) { }

    create(jobResponse: JobResponse): Observable<EntityResponseType> {
        const copy = this.convert(jobResponse);
        return this.http.post<JobResponse>(this.resourceUrl, copy, { observe: 'response' })
            .map((res: EntityResponseType) => this.convertResponse(res));
    }

    update(jobResponse: JobResponse): Observable<EntityResponseType> {
        const copy = this.convert(jobResponse);
        return this.http.put<JobResponse>(this.resourceUrl, copy, { observe: 'response' })
            .map((res: EntityResponseType) => this.convertResponse(res));
    }

    find(id: number): Observable<EntityResponseType> {
        return this.http.get<JobResponse>(`${this.resourceUrl}/${id}`, { observe: 'response'})
            .map((res: EntityResponseType) => this.convertResponse(res));
    }

    query(req?: any): Observable<HttpResponse<JobResponse[]>> {
        const options = createRequestOption(req);
        return this.http.get<JobResponse[]>(this.resourceUrl, { params: options, observe: 'response' })
            .map((res: HttpResponse<JobResponse[]>) => this.convertArrayResponse(res));
    }

    delete(id: number): Observable<HttpResponse<any>> {
        return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response'});
    }

    search(req?: any): Observable<HttpResponse<JobResponse[]>> {
        const options = createRequestOption(req);
        return this.http.get<JobResponse[]>(this.resourceSearchUrl, { params: options, observe: 'response' })
            .map((res: HttpResponse<JobResponse[]>) => this.convertArrayResponse(res));
    }

    findByJob(id: number): Observable<HttpResponse<JobResponse[]>> {
        const options = createRequestOption();
        return this.http.get<JobResponse[]>( SERVER_API_URL + 'api/offer-job-responses/'+id, { params: options, observe: 'response' })
            .map((res: HttpResponse<JobResponse[]>) => this.convertArrayResponse(res));
    }

    findByUser(id: number): Observable<HttpResponse<JobResponse[]>> {
        const options = createRequestOption();
        return this.http.get<JobResponse[]>( SERVER_API_URL + 'api/user-offer-job-responses/'+id, { params: options, observe: 'response' })
            .map((res: HttpResponse<JobResponse[]>) => this.convertArrayResponse(res));
    }
    private convertResponse(res: EntityResponseType): EntityResponseType {
        const body: JobResponse = this.convertItemFromServer(res.body);
        return res.clone({body});
    }

    private convertArrayResponse(res: HttpResponse<JobResponse[]>): HttpResponse<JobResponse[]> {
        const jsonResponse: JobResponse[] = res.body;
        const body: JobResponse[] = [];
        for (let i = 0; i < jsonResponse.length; i++) {
            body.push(this.convertItemFromServer(jsonResponse[i]));
        }
        return res.clone({body});
    }

    /**
     * Convert a returned JSON object to JobResponse.
     */
    private convertItemFromServer(jobResponse: JobResponse): JobResponse {
        const copy: JobResponse = Object.assign({}, jobResponse);
        return copy;
    }

    /**
     * Convert a JobResponse to a JSON which can be sent to the server.
     */
    private convert(jobResponse: JobResponse): JobResponse {
        const copy: JobResponse = Object.assign({}, jobResponse);
        return copy;
    }
}
