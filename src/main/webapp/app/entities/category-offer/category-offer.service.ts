import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs/Observable';
import { SERVER_API_URL } from '../../app.constants';

import { CategoryOffer } from './category-offer.model';
import { createRequestOption } from '../../shared';

export type EntityResponseType = HttpResponse<CategoryOffer>;

@Injectable()
export class CategoryOfferService {

    private resourceUrl =  SERVER_API_URL + 'api/category-offers';
    private resourceSearchUrl = SERVER_API_URL + 'api/_search/category-offers';

    constructor(private http: HttpClient) { }

    create(categoryOffer: CategoryOffer): Observable<EntityResponseType> {
        const copy = this.convert(categoryOffer);
        return this.http.post<CategoryOffer>(this.resourceUrl, copy, { observe: 'response' })
            .map((res: EntityResponseType) => this.convertResponse(res));
    }

    update(categoryOffer: CategoryOffer): Observable<EntityResponseType> {
        const copy = this.convert(categoryOffer);
        return this.http.put<CategoryOffer>(this.resourceUrl, copy, { observe: 'response' })
            .map((res: EntityResponseType) => this.convertResponse(res));
    }

    find(id: number): Observable<EntityResponseType> {
        return this.http.get<CategoryOffer>(`${this.resourceUrl}/${id}`, { observe: 'response'})
            .map((res: EntityResponseType) => this.convertResponse(res));
    }

    query(req?: any): Observable<HttpResponse<CategoryOffer[]>> {
        const options = createRequestOption(req);
        return this.http.get<CategoryOffer[]>(this.resourceUrl, { params: options, observe: 'response' })
            .map((res: HttpResponse<CategoryOffer[]>) => this.convertArrayResponse(res));
    }

    delete(id: number): Observable<HttpResponse<any>> {
        return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response'});
    }

    search(req?: any): Observable<HttpResponse<CategoryOffer[]>> {
        const options = createRequestOption(req);
        return this.http.get<CategoryOffer[]>(this.resourceSearchUrl, { params: options, observe: 'response' })
            .map((res: HttpResponse<CategoryOffer[]>) => this.convertArrayResponse(res));
    }

    private convertResponse(res: EntityResponseType): EntityResponseType {
        const body: CategoryOffer = this.convertItemFromServer(res.body);
        return res.clone({body});
    }

    private convertArrayResponse(res: HttpResponse<CategoryOffer[]>): HttpResponse<CategoryOffer[]> {
        const jsonResponse: CategoryOffer[] = res.body;
        const body: CategoryOffer[] = [];
        for (let i = 0; i < jsonResponse.length; i++) {
            body.push(this.convertItemFromServer(jsonResponse[i]));
        }
        return res.clone({body});
    }

    /**
     * Convert a returned JSON object to CategoryOffer.
     */
    private convertItemFromServer(categoryOffer: CategoryOffer): CategoryOffer {
        const copy: CategoryOffer = Object.assign({}, categoryOffer);
        return copy;
    }

    /**
     * Convert a CategoryOffer to a JSON which can be sent to the server.
     */
    private convert(categoryOffer: CategoryOffer): CategoryOffer {
        const copy: CategoryOffer = Object.assign({}, categoryOffer);
        return copy;
    }
}
