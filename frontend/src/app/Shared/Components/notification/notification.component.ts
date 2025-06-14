import { Component, inject } from '@angular/core'
import { AlertModel } from '../../../Core/models/alert.model'
import { AlertService } from '../../../Core/Services/alertService/alert.service'
import { AlertType } from '../../../Core/Enums/alertType.enum'
import { Subject, takeUntil } from 'rxjs'

@Component({
    selector: 'app-notification',
    standalone: true,
    imports: [],
    templateUrl: './notification.component.html',
    styleUrl: './notification.component.css',
})
export class NotificationComponent {
    alert?: AlertModel
    alertType = AlertType
    private alertService = inject(AlertService)
    private destroy$ = new Subject<void>()

    ngOnInit() {
        this.alertService
            .getAlert()
            .pipe(takeUntil(this.destroy$))
            .subscribe(alert => {
                this.alert = alert
                this.deleteAlert()
            })
    }

    ngOnDestroy() {
        this.destroy$.next()
        this.destroy$.complete()
    }

    deleteAlert() {
        window.setTimeout(() => {
            this.alert = undefined
        }, 2000)
    }
}
