import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { VerifyMyCandidateSharedModule } from '../../shared';

import { APPLY_ROUTE } from './';
import {ApplyComponent} from './apply.component';

@NgModule({
    imports: [
        VerifyMyCandidateSharedModule ,
        RouterModule.forChild(APPLY_ROUTE)
    ],
    declarations: [
        ApplyComponent
    ],
    entryComponents: [

    ],
    providers: [
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class ApplyModule {}
