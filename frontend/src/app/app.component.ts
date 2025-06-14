import { Component } from '@angular/core'
import { RouterOutlet } from '@angular/router'
import { NotificationComponent } from './Shared/Components/notification/notification.component'
import 'bootstrap-icons/font/bootstrap-icons.css'

@Component({
    selector: 'app-root',
    standalone: true,
    imports: [RouterOutlet, NotificationComponent],
    templateUrl: './app.component.html',
    styleUrl: './app.component.css',
})
export class AppComponent {}
