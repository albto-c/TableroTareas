import { Component, inject } from '@angular/core'
import { RouterOutlet } from '@angular/router'
import { AuthService } from '../../Core/Services/authService/auth.service'
import { ProfileComponent } from '../../Shared/Components/profile/profile.component'

@Component({
    selector: 'app-initial-layout',
    standalone: true,
    imports: [RouterOutlet, ProfileComponent],
    templateUrl: './initial-layout.component.html',
    styleUrl: './initial-layout.component.css',
})
export class InitialLayoutComponent {
    authService = inject(AuthService)
}
