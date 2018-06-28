import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs/Observable';
import { SERVER_API_URL } from '../../app.constants';

import { JhiDateUtils } from 'ng-jhipster';

import { EtudeProfil } from './etude-profil.model';
import { createRequestOption } from '../../shared';

export type EntityResponseType = HttpResponse<EtudeProfil>;

@Injectable()
export class EtudeProfilService {

    private resourceUrl =  SERVER_API_URL + 'api/etude-profils';
    private resourceSearchUrl = SERVER_API_URL + 'api/_search/etude-profils';

    constructor(private http: HttpClient, private dateUtils: JhiDateUtils) { }

    create(etudeProfil: EtudeProfil): Observable<EntityResponseType> {
        const copy = this.convert(etudeProfil);
        return this.http.post<EtudeProfil>(this.resourceUrl, copy, { observe: 'response' })
            .map((res: EntityResponseType) => this.convertResponse(res));
    }

    update(etudeProfil: EtudeProfil): Observable<EntityResponseType> {
        const copy = this.convert(etudeProfil);
        return this.http.put<EtudeProfil>(this.resourceUrl, copy, { observe: 'response' })
            .map((res: EntityResponseType) => this.convertResponse(res));
    }

    find(id: number): Observable<EntityResponseType> {
        return this.http.get<EtudeProfil>(`${this.resourceUrl}/${id}`, { observe: 'response'})
            .map((res: EntityResponseType) => this.convertResponse(res));
    }

    query(req?: any): Observable<HttpResponse<EtudeProfil[]>> {
        const options = createRequestOption(req);
        return this.http.get<EtudeProfil[]>(this.resourceUrl, { params: options, observe: 'response' })
            .map((res: HttpResponse<EtudeProfil[]>) => this.convertArrayResponse(res));
    }

    delete(id: number): Observable<HttpResponse<any>> {
        return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response'});
    }

    search(req?: any): Observable<HttpResponse<EtudeProfil[]>> {
        const options = createRequestOption(req);
        return this.http.get<EtudeProfil[]>(this.resourceSearchUrl, { params: options, observe: 'response' })
            .map((res: HttpResponse<EtudeProfil[]>) => this.convertArrayResponse(res));
    }

    private convertResponse(res: EntityResponseType): EntityResponseType {
        const body: EtudeProfil = this.convertItemFromServer(res.body);
        return res.clone({body});
    }

    private convertArrayResponse(res: HttpResponse<EtudeProfil[]>): HttpResponse<EtudeProfil[]> {
        const jsonResponse: EtudeProfil[] = res.body;
        const body: EtudeProfil[] = [];
        for (let i = 0; i < jsonResponse.length; i++) {
            body.push(this.convertItemFromServer(jsonResponse[i]));
        }
        return res.clone({body});
    }

    /**
     * Convert a returned JSON object to EtudeProfil.
     */
    private convertItemFromServer(etudeProfil: EtudeProfil): EtudeProfil {
        const copy: EtudeProfil = Object.assign({}, etudeProfil);
        copy.anneeEtudeDebut = this.dateUtils
            .convertLocalDateFromServer(etudeProfil.anneeEtudeDebut);
        copy.anneEtudeFin = this.dateUtils
            .convertLocalDateFromServer(etudeProfil.anneEtudeFin);
        return copy;
    }

    /**
     * Convert a EtudeProfil to a JSON which can be sent to the server.
     */
    private convert(etudeProfil: EtudeProfil): EtudeProfil {
        const copy: EtudeProfil = Object.assign({}, etudeProfil);
        copy.anneeEtudeDebut = this.dateUtils
            .convertLocalDateToServer(etudeProfil.anneeEtudeDebut);
        copy.anneEtudeFin = this.dateUtils
            .convertLocalDateToServer(etudeProfil.anneEtudeFin);
        return copy;
    }
}
