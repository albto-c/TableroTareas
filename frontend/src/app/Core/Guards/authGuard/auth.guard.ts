import { inject } from '@angular/core'
import { CanActivateFn, Router } from '@angular/router'
import { AlertService } from '../../Services/alertService/alert.service'
import { AlertType } from '../../Enums/alertType.enum'
import { AuthService } from '../../Services/authService/auth.service'

export const authGuard: CanActivateFn = (route, state) => {
    const router = inject(Router)
    const alertService = inject(AlertService)
    const authService = inject(AuthService)

    if (authService.isLogged$.getValue()) {
        return true
    } else {
        router.navigate(['/'])

        alertService.setAlert({
            type: AlertType.error,
            text: '¡Inicia sesión!',
        })

        return false
    }
}
