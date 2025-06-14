import { HttpInterceptorFn } from '@angular/common/http'
import { inject } from '@angular/core'
import { AuthService } from '../../Services/authService/auth.service'

export const httpInterceptor: HttpInterceptorFn = (req, next) => {
    let authService = inject(AuthService)
    let reqClone = req.clone({
        setHeaders: {
            Authorization: authService.getToken(),
        },
    })
    return next(reqClone)
}
