import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs/Observable';
import { SERVER_API_URL } from '../../app.constants';

import { JhiDateUtils } from 'ng-jhipster';

import { JobOffer } from './job-offer.model';
import { createRequestOption } from '../../shared';

export type EntityResponseType = HttpResponse<JobOffer>;

@Injectable()
export class JobOfferService {

    private resourceUrl =  SERVER_API_URL + 'api/job-offers';
    private resourceSearchUrl = SERVER_API_URL + 'api/_search/job-offers';

    constructor(private http: HttpClient, private dateUtils: JhiDateUtils) { }

    create(jobOffer: JobOffer): Observable<EntityResponseType> {
        const copy = this.convert(jobOffer);
        return this.http.post<JobOffer>(this.resourceUrl, copy, { observe: 'response' })
            .map((res: EntityResponseType) => this.convertResponse(res));
    }

    update(jobOffer: JobOffer): Observable<EntityResponseType> {
        const copy = this.convert(jobOffer);
        return this.http.put<JobOffer>(this.resourceUrl, copy, { observe: 'response' })
            .map((res: EntityResponseType) => this.convertResponse(res));
    }

    find(id: number): Observable<EntityResponseType> {
        return this.http.get<JobOffer>(`${this.resourceUrl}/${id}`, { observe: 'response'})
            .map((res: EntityResponseType) => this.convertResponse(res));
    }

    query(req?: any): Observable<HttpResponse<JobOffer[]>> {
        const options = createRequestOption(req);
        return this.http.get<JobOffer[]>(this.resourceUrl, { params: options, observe: 'response' })
            .map((res: HttpResponse<JobOffer[]>) => this.convertArrayResponse(res));
    }

    delete(id: number): Observable<HttpResponse<any>> {
        return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response'});
    }

    search(req?: any): Observable<HttpResponse<JobOffer[]>> {
        const options = createRequestOption(req);
        return this.http.get<JobOffer[]>(this.resourceSearchUrl, { params: options, observe: 'response' })
            .map((res: HttpResponse<JobOffer[]>) => this.convertArrayResponse(res));
    }

    private convertResponse(res: EntityResponseType): EntityResponseType {
        const body: JobOffer = this.convertItemFromServer(res.body);
        return res.clone({body});
    }

    private convertArrayResponse(res: HttpResponse<JobOffer[]>): HttpResponse<JobOffer[]> {
        const jsonResponse: JobOffer[] = res.body;
        const body: JobOffer[] = [];
        for (let i = 0; i < jsonResponse.length; i++) {
            body.push(this.convertItemFromServer(jsonResponse[i]));
        }
        return res.clone({body});
    }

    /**
     * Convert a returned JSON object to JobOffer.
     */
    private convertItemFromServer(jobOffer: JobOffer): JobOffer {
        const copy: JobOffer = Object.assign({}, jobOffer);
        copy.dateOffer = this.dateUtils
            .convertLocalDateFromServer(jobOffer.dateOffer);
        return copy;
    }

    /**
     * Convert a JobOffer to a JSON which can be sent to the server.
     */
    private convert(jobOffer: JobOffer): JobOffer {
        const copy: JobOffer = Object.assign({}, jobOffer);
        copy.dateOffer = this.dateUtils
            .convertLocalDateToServer(jobOffer.dateOffer);
        return copy;
    }

    queryCompany(idCompany : number):Observable<HttpResponse<JobOffer[]>>{
        return this.http.get<JobOffer[]>(SERVER_API_URL + 'api/job-offers-company/'+idCompany, {  observe: 'response' })
            .map((res: HttpResponse<JobOffer[]>) => this.convertArrayResponse(res));
    }

    queryLimite(page : number,limite: number): Observable<HttpResponse<JobOffer[]>> {
        return this.http.get<JobOffer[]>(this.resourceUrl+"/"+page+"/"+limite, {  observe: 'response' })
            .map((res: HttpResponse<JobOffer[]>) => this.convertArrayResponse(res));
    }

    count(req?:any):Observable<HttpResponse<number>>{
        return this.http.get<number>(this.resourceUrl+"/count/", {  observe: 'response' })
            .map((res: HttpResponse<number>) => res);
    }


}
