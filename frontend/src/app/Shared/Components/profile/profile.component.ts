import { Component, inject, signal } from '@angular/core'
import { AuthService } from '../../../Core/Services/authService/auth.service'
import { CommonModule } from '@angular/common'

@Component({
    selector: 'app-profile',
    standalone: true,
    imports: [CommonModule],
    templateUrl: './profile.component.html',
    styleUrl: './profile.component.css',
})
export class ProfileComponent {
    authService = inject(AuthService)
    onProfileClick = signal(false)
}
