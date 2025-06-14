import {
    HttpClient,
    HttpErrorResponse,
    HttpStatusCode,
} from '@angular/common/http'
import { inject, Injectable } from '@angular/core'
import { LoginModel } from '../../models/login.model'
import { Router } from '@angular/router'
import { BehaviorSubject, catchError, EMPTY } from 'rxjs'
import { AlertService } from '../alertService/alert.service'
import { AlertType } from '../../Enums/alertType.enum'

@Injectable({
    providedIn: 'root',
})
export class AuthService {
    httpClient = inject(HttpClient)
    router = inject(Router)
    alertService = inject(AlertService)
    urlLogin = 'http://localhost:8080/auth/login'
    urlRegister = 'http://localhost:8080/auth/register'

    isLogged$ = new BehaviorSubject<boolean>(false)

    constructor() {
        this.onInit()
    }

    onInit() {
        if (this.existToken()) {
            this.isLogged$.next(true)
        }
    }

    login(loginModel: LoginModel) {
        this.httpClient
            .post<any>(this.urlLogin, loginModel)
            .pipe(
                catchError((error: HttpErrorResponse) => {
                    if (
                        error.status === HttpStatusCode.NotFound ||
                        error.status === HttpStatusCode.Unauthorized
                    ) {
                        this.alertService.setAlert({
                            type: AlertType.error,
                            text: 'Usuario o contraseña incorrectos',
                        })
                        return EMPTY
                    }

                    this.alertService.setAlert({
                        type: AlertType.error,
                        text: 'Error desconocido',
                    })
                    return EMPTY
                })
            )
            .subscribe(res => {
                if (res != null) {
                    this.setToken(res.token)
                    this.isLogged$.next(true)
                    this.router.navigate(['/'])
                }
            })
    }

    logout() {
        localStorage.clear()
        this.isLogged$.next(false)
        this.router.navigate(['/'])
    }

    register(loginModel: LoginModel) {
        this.httpClient
            .post<any>(this.urlRegister, loginModel)
            .pipe(
                catchError(() => {
                    this.alertService.setAlert({
                        type: AlertType.error,
                        text: '',
                    })
                    return EMPTY
                })
            )
            .subscribe(() => {
                this.alertService.setAlert({
                    type: AlertType.success,
                    text: 'Registro completado con éxito',
                })
            })
    }

    setToken(token: string) {
        localStorage.setItem('jwtToken', 'Bearer:' + token)
    }

    getToken() {
        let jwtToken = localStorage.getItem('jwtToken')?.toString()
        if (jwtToken != null) {
            return jwtToken
        } else {
            return ''
        }
    }

    existToken() {
        return this.getToken() != ''
    }

    getUserOfToken() {
        let token = this.getToken()
        if (token != null && token != '') {
            let tokenSplited = token.split('.')
            let tokenPayload = JSON.parse(atob(tokenSplited[1]))
            return tokenPayload.sub
        }
        return ''
    }
}
