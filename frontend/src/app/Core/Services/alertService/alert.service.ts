import { Injectable } from '@angular/core'
import { Subject } from 'rxjs'
import { AlertModel } from '../../models/alert.model'

@Injectable({
    providedIn: 'root',
})
export class AlertService {
    private alert$ = new Subject<AlertModel>()

    setAlert(alert: AlertModel) {
        this.alert$.next(alert)
    }

    getAlert() {
        return this.alert$.asObservable()
    }

    constructor() {}
}
