import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs/Observable';
import { SERVER_API_URL } from '../../app.constants';

import { Profil } from './profil.model';
import { createRequestOption } from '../../shared';

export type EntityResponseType = HttpResponse<Profil>;

@Injectable()
export class ProfilService {

    private resourceUrl =  SERVER_API_URL + 'api/profils';
    private resourceSearchUrl = SERVER_API_URL + 'api/_search/profils';

    constructor(private http: HttpClient) { }

    create(profil: Profil): Observable<EntityResponseType> {
        const copy = this.convert(profil);
        return this.http.post<Profil>(this.resourceUrl, copy, { observe: 'response' })
            .map((res: EntityResponseType) => this.convertResponse(res));
    }

    update(profil: Profil): Observable<EntityResponseType> {
        const copy = this.convert(profil);
        return this.http.put<Profil>(this.resourceUrl, copy, { observe: 'response' })
            .map((res: EntityResponseType) => this.convertResponse(res));
    }

    find(id: number): Observable<EntityResponseType> {
        return this.http.get<Profil>(`${this.resourceUrl}/${id}`, { observe: 'response'})
            .map((res: EntityResponseType) => this.convertResponse(res));
    }

    query(req?: any): Observable<HttpResponse<Profil[]>> {
        const options = createRequestOption(req);
        return this.http.get<Profil[]>(this.resourceUrl, { params: options, observe: 'response' })
            .map((res: HttpResponse<Profil[]>) => this.convertArrayResponse(res));
    }

    delete(id: number): Observable<HttpResponse<any>> {
        return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response'});
    }

    search(req?: any): Observable<HttpResponse<Profil[]>> {
        const options = createRequestOption(req);
        return this.http.get<Profil[]>(this.resourceSearchUrl, { params: options, observe: 'response' })
            .map((res: HttpResponse<Profil[]>) => this.convertArrayResponse(res));
    }

    findByUserId(id: number): Observable<EntityResponseType> {
        return this.http.get<Profil>(`${this.resourceUrl}/user/${id}`, { observe: 'response'})
            .map((res: EntityResponseType) => this.convertResponse(res));
    }


    private convertResponse(res: EntityResponseType): EntityResponseType {
        const body: Profil = this.convertItemFromServer(res.body);
        return res.clone({body});
    }

    private convertArrayResponse(res: HttpResponse<Profil[]>): HttpResponse<Profil[]> {
        const jsonResponse: Profil[] = res.body;
        const body: Profil[] = [];
        for (let i = 0; i < jsonResponse.length; i++) {
            body.push(this.convertItemFromServer(jsonResponse[i]));
        }
        return res.clone({body});
    }

    /**
     * Convert a returned JSON object to Profil.
     */
    private convertItemFromServer(profil: Profil): Profil {
        const copy: Profil = Object.assign({}, profil);
        return copy;
    }

    /**
     * Convert a Profil to a JSON which can be sent to the server.
     */
    private convert(profil: Profil): Profil {
        const copy: Profil = Object.assign({}, profil);
        return copy;
    }
}
