import { NgModule, LOCALE_ID } from '@angular/core';
import { Title } from '@angular/platform-browser';
import { registerLocaleData } from '@angular/common';
import locale from '@angular/common/locales/en';

import {
    VerifyMyCandidateSharedLibsModule,
    JhiAlertComponent,
    JhiAlertErrorComponent
} from './';

@NgModule({
    imports: [
        VerifyMyCandidateSharedLibsModule
    ],
    declarations: [
        JhiAlertComponent,
        JhiAlertErrorComponent
    ],
    providers: [
        Title,
        {
            provide: LOCALE_ID,
            useValue: 'en'
        },
    ],
    exports: [
        VerifyMyCandidateSharedLibsModule,
        JhiAlertComponent,
        JhiAlertErrorComponent
    ]
})
export class VerifyMyCandidateSharedCommonModule {
    constructor() {
        registerLocaleData(locale);
    }
}
